package com.ime.lockmanager.reservation.application.service;

import com.ime.lockmanager.common.format.exception.locker.*;
import com.ime.lockmanager.common.format.exception.user.AlreadyReservedUserException;
import com.ime.lockmanager.common.format.exception.user.InvalidReservedStatusException;
import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.ReservationOfLockerResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerDetailQueryPort;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.locker.Locker;
import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetailStatus;
import com.ime.lockmanager.reservation.adapter.out.dto.DeleteReservationByStudentNumDto;
import com.ime.lockmanager.reservation.application.port.in.ReservationUseCase;
import com.ime.lockmanager.reservation.application.port.out.ReservationQueryPort;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.reservation.domain.ReservationStatus;
import com.ime.lockmanager.user.application.port.in.req.UserCancelLockerRequestDto;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.User;
import com.ime.lockmanager.user.domain.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ime.lockmanager.reservation.domain.ReservationStatus.RESERVED;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReservationService implements ReservationUseCase {
    private final LockerQueryPort lockerQueryPort;
    private final UserQueryPort userQueryPort;
    private final ReservationQueryPort reservationQueryPort;
    private final LockerDetailQueryPort lockerDetailQueryPort;
    private final static List<String> notInvalidStatus = new ArrayList<>(List.of("휴햑", "재학"));

    @Override
    public void resetReservation(Long lockerId, Principal principal) {
        log.info("{} : 모든 사물함 초기화", principal.getName());
        List<LockerDetail> lockerDetailsByLocker = lockerDetailQueryPort.findLockerDetailByLocker(lockerId);
        List<Reservation> reservationsByLockerDetails = reservationQueryPort.findAllByLockerDetails(lockerDetailsByLocker);
        reservationsByLockerDetails.stream()
                .forEach(reservation -> reservation.changeReservationStatus(ReservationStatus.RESET));
    }

    @Transactional(readOnly = true)
    @Override
    public ReservationOfLockerResponseDto findReservedLockers() {
        List<Long> reservedLockersIdList = reservationQueryPort.findReservedLockers();
        ReservationOfLockerResponseDto build = ReservationOfLockerResponseDto.builder()
                .lockerIdList(reservedLockersIdList)
                .build();
        return build;
    }

    @Override
    public LockerRegisterResponseDto registerForAdmin(LockerRegisterRequestDto lockerRegisterRequestDto) throws Exception {
        log.info("(관리자)예약 시작 : [학번 {}, 사물함 번호 {}]", lockerRegisterRequestDto.getStudentNum(), lockerRegisterRequestDto.getLockerDetailId());
        User user = userQueryPort.findByStudentNum(lockerRegisterRequestDto.getStudentNum())
                .orElseThrow(NotFoundUserException::new);

        LockerDetail lockerDetail = lockerDetailQueryPort.findByIdWithLocker(lockerRegisterRequestDto.getLockerDetailId())
                .orElseThrow(NotFoundLockerException::new);
        if (notInvalidStatus.contains(user.getStatus())) {
            if (isReservationExistByUserStudentNum(user.getStudentNum())) {
                if (isReservationExistByLockerDetail(lockerDetail.getId())) {
                    reservationQueryPort.registerLocker(user, lockerDetail);
                    log.info("예약 수정 완료 : [학번 {}, 사물함 번호 {}]", lockerRegisterRequestDto.getStudentNum(), lockerRegisterRequestDto.getLockerDetailId());
                    return LockerRegisterResponseDto.of((lockerDetail.getLockerNum()),
                            user.getStudentNum(),
                            lockerDetail.getLocker().getName());
                }
                throw new AlreadyReservedLockerException();
            }
            throw new AlreadyReservedUserException();
        }
        throw new InvalidReservedStatusException();
    }


    @Override
    public LockerRegisterResponseDto registerForUser(LockerRegisterRequestDto dto) throws Exception {
        log.info("(사용자)예약 시작 : [학번 {}, 사물함 번호 {}]", dto.getStudentNum(), dto.getLockerDetailId());
        User user = userQueryPort.findByStudentNum(dto.getStudentNum())
                .orElseThrow(NotFoundUserException::new);
        LockerDetail lockerDetail = lockerDetailQueryPort.findByIdWithLocker(dto.getLockerDetailId())
                .orElseThrow(() -> new NullPointerException("없는 사물함입니다."));
        Locker locker = lockerDetail.getLocker();
        if (locker.getPeriod() != null) {
            if (locker.isDeadlineValid()) {
                if (notInvalidStatus.contains(user.getStatus())) {
                    if (isReservationPossibleByLockerDetailId(lockerDetail.getId())) {
                        if (!lockerDetail.getLockerDetailStatus().equals(LockerDetailStatus.BROKEN)) {
                            if (isReservationPossibleByStudentNum(user.getStudentNum())) {
                                reservationQueryPort.registerLocker(UserJpaEntity.of(user), lockerDetail);
                                log.info("예약 완료 : [학번 {}, 사물함 번호 {}]", dto.getStudentNum(), dto.getLockerDetailId());
                                return LockerRegisterResponseDto
                                        .of(lockerDetail.getLockerNum(), user.getStudentNum(), locker.getName());
                            }
                            throw new IllegalStateException("고장난 사물함입니다");
                        }
                        throw new AlreadyReservedLockerException();
                    }
                    throw new AlreadyReservedUserException();
                }
                throw new InvalidReservedStatusException();
            }
            throw new IsNotReserveTimeException();
        }
        throw new ReserveTimeNullException();
    }

    private boolean isReservationExistByUserStudentNum(String userStudentNum) {
        return reservationQueryPort.findReservationByStudentNum(userStudentNum).isEmpty();
    }

    private boolean isReservationExistByLockerDetail(Long lockerDetailId) {
        return reservationQueryPort.findByLockerDetailId(lockerDetailId).isEmpty();
    }

    private boolean isReservationPossibleByLockerDetailId(Long lockerDetailId) {
        List<Reservation> allReservationByLockerDetailId = reservationQueryPort.findAllByLockerDetailId(lockerDetailId);
        List<Reservation> reservations = allReservationByLockerDetailId.stream()
                .filter(reservation -> reservation.getReservationStatus().equals(RESERVED))
                .collect(Collectors.toList());
        if (reservations.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean isReservationPossibleByStudentNum(String studentNum) {
        List<Reservation> allReservationByLockerDetailId = reservationQueryPort.findAllByStudentNum(studentNum);
        List<Reservation> reservations = allReservationByLockerDetailId.stream()
                .filter(reservation -> reservation.getReservationStatus().equals(RESERVED))
                .collect(Collectors.toList());
        if (reservations.isEmpty()) {
            return true;
        }
        return false;
    }


    public void cancelLockerByStudentNum(UserCancelLockerRequestDto cancelLockerDto) {
        if (!isReservationExistByStudentNum(cancelLockerDto.getStudentNum())) {
            throw new InvalidCancelLockerException();
        }
        log.info("{} : 사물함 취소", cancelLockerDto.getStudentNum());
        reservationQueryPort.deleteByStudentNum(DeleteReservationByStudentNumDto.builder()
                .studentNum(cancelLockerDto.getStudentNum())
                .build());
    }

    public boolean isReservationExistByStudentNum(String studentNum) {
        return !reservationQueryPort.findReservationByStudentNum(studentNum)
                .isEmpty();
    }
}
