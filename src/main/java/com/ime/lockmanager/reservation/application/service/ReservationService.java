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
import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.locker.domain.LockerDetail;
import com.ime.lockmanager.reservation.adapter.out.dto.DeleteReservationByStudentNumDto;
import com.ime.lockmanager.reservation.application.port.in.ReservationUseCase;
import com.ime.lockmanager.reservation.application.port.out.ReservationQueryPort;
import com.ime.lockmanager.reservation.application.port.out.dto.FindReservationByLockerDetailIdDto;
import com.ime.lockmanager.reservation.application.port.out.dto.FindReservationByStudentNumDto;
import com.ime.lockmanager.reservation.domain.Reservation;
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
import java.util.Optional;

import static java.time.LocalDateTime.now;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReservationService implements ReservationUseCase {

    private final UserQueryPort userQueryPort;
    private final LockerQueryPort lockerQueryPort;
    private final ReservationQueryPort reservationQueryPort;
    private final LockerDetailQueryPort lockerDetailQueryPort;
    private final static List<String> notInvalidStatus = new ArrayList<>(List.of("휴햑", "재학"));

    @Override
    public void resetReservation(Principal principal) {
        log.info("{} : 모든 사물함 초기화", principal.getName());
        reservationQueryPort.deleteAll();
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
        /*log.info("(관리자)예약 시작 : [학번 {}, 사물함 번호 {}]", lockerRegisterRequestDto.getStudentNum(), lockerRegisterRequestDto.getLockerDetailId());
        User byStudentNum = userQueryPort.findByStudentNum(lockerRegisterRequestDto.getStudentNum())
                .orElseThrow(NotFoundUserException::new);

        Locker byLockerId = lockerQueryPort.findByLockerId(lockerRegisterRequestDto.getLockerDetailId())
                .orElseThrow(NotFoundLockerException::new);
        if (notInvalidStatus.contains(byStudentNum.getStatus())) {
            if (reservationQueryPort.isReservationByStudentNum(FindReservationByStudentNumDto.builder()
                    .studentNum(byStudentNum.getStudentNum())
                    .build())) {
                if (reservationQueryPort.findByLockerDetailId(FindReservationByLockerDetailIdDto
                        .builder()
                        .lockerDetailId(lockerRegisterRequestDto.getLockerDetailId())
                        .build()) != null) {
//                    reservationQueryPort.registerLocker(UserJpaEntity.of(byStudentNum), LockerJpaEntity.of(byLockerId));
                    log.info("예약 완료 : [학번 {}, 사물함 번호 {}]", lockerRegisterRequestDto.getStudentNum(), lockerRegisterRequestDto.getLockerDetailId());
                    return LockerRegisterResponseDto.builder()
                            .lockerDetailNum(byLockerId.getId())
                            .studentNum(byStudentNum.getStudentNum())
                            .build();
                }
                throw new AlreadyReservedLockerException();
            }
            throw new AlreadyReservedUserException();
        }
        throw new InvalidReservedStatusException();*/
        return null;
    }


    @Override
    public LockerRegisterResponseDto registerForUser(LockerRegisterRequestDto dto) throws Exception {
        log.info("(사용자)예약 시작 : [학번 {}, 사물함 번호 {}]", dto.getStudentNum(), dto.getLockerDetailId());
        User user = userQueryPort.findByStudentNum(dto.getStudentNum())
                .orElseThrow(NotFoundUserException::new);
        LockerDetail lockerDetail = lockerDetailQueryPort.findByIdWithLocker(dto.getLockerDetailId())
                .orElseThrow(() -> new NullPointerException("없는 사물함입니다."));
        Locker locker = lockerDetail.getLocker();
        if (!Optional.of(locker.getPeriod()).isEmpty()) {
            if (locker.isDeadlineValid()) {
                if (notInvalidStatus.contains(user.getStatus())) {
                    if (isReservationExistByLockerDetail(lockerDetail)) {
                        if (isReservationExistByUserId(user)) {
                            reservationQueryPort.registerLocker(UserJpaEntity.of(user), lockerDetail);
                            log.info("예약 완료 : [학번 {}, 사물함 번호 {}]", dto.getStudentNum(), dto.getLockerDetailId());
                            return LockerRegisterResponseDto
                                    .of(lockerDetail.getLocker_num(), user.getStudentNum(), locker.getName());
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

    private boolean isReservationExistByUserId(User user) {
        return reservationQueryPort.findReservationByStudentNum(user.getStudentNum()).isEmpty();
    }

    private boolean isReservationExistByLockerDetail(LockerDetail lockerDetail) {
        return reservationQueryPort.findByLockerDetailId(FindReservationByLockerDetailIdDto.builder()
                .lockerDetailId(lockerDetail.getId())
                .build()).isEmpty();
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
