package com.ime.lockmanager.reservation.application.service;

import com.ime.lockmanager.common.format.exception.locker.*;
import com.ime.lockmanager.common.format.exception.reservation.NotFoundReservationException;
import com.ime.lockmanager.common.format.exception.reservation.NotMatchUserTierAndLockerException;
import com.ime.lockmanager.common.format.exception.user.AlreadyReservedUserException;
import com.ime.lockmanager.common.format.exception.user.InvalidReservedStatusException;
import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.common.sse.SseEmitterService;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.ReservationOfLockerResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerDetailQueryPort;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.locker.Locker;
import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;
import java.util.stream.Collectors;

import static com.ime.lockmanager.common.sse.SseEmitterService.sendEvent;
import static com.ime.lockmanager.reservation.domain.ReservationStatus.RESERVED;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReservationService implements ReservationUseCase {
    private final UserQueryPort userQueryPort;
    private final ReservationQueryPort reservationQueryPort;
    private final LockerDetailQueryPort lockerDetailQueryPort;

    @Override
    public void resetReservation(Long lockerId) {
        List<LockerDetail> lockerDetailsByLocker = lockerDetailQueryPort.findLockerDetailByLocker(lockerId);
        List<Reservation> reservationsByLockerDetails = reservationQueryPort.findAllByLockerDetails(lockerDetailsByLocker);

    }


    @Override
    public LockerRegisterResponseDto registerForAdmin(LockerRegisterRequestDto requestDto) throws Exception {
        User user = userQueryPort.findById(requestDto.getUserId()).orElseThrow(NotFoundUserException::new);
//                .findByStudentNum(requestDto.getStudentNum())
        LockerDetail lockerDetail = lockerDetailQueryPort.findByIdWithLocker(requestDto.getLockerDetailId())
                .orElseThrow(() -> new NullPointerException("없는 사물함입니다."));
        log.info("(사용자)예약 시작 : [학번 {}, 사물함 번호 {}]", user.getStudentNum(), lockerDetail.getLockerNum());
        Locker locker = lockerDetail.getLocker();
        commonConditionForReserve(user, lockerDetail, locker);
        reservationQueryPort.registerLocker(user, lockerDetail);
//                    log.info("예약 수정 완료 : [학번 {}, 사물함 번호 {}]", requestDto.getStudentNum(), requestDto.getLockerDetailId());
        return LockerRegisterResponseDto.of((lockerDetail.getLockerNum()),
                user.getStudentNum(),
                lockerDetail.getLocker().getName());

    }


    @Override
    public LockerRegisterResponseDto registerForUser(LockerRegisterRequestDto dto) throws Exception {
        User user = userQueryPort.findById(dto.getUserId()).orElseThrow(NotFoundUserException::new);
        LockerDetail lockerDetail = lockerDetailQueryPort.findByIdWithLocker(dto.getLockerDetailId())
                .orElseThrow(InvalidLockerDetailException::new);
        log.info("(사용자)예약 시작 : [학번 {}, 사물함 번호 {}]", user.getStudentNum(), lockerDetail.getLockerNum());
        Locker locker = lockerDetail.getLocker();
        distinctConditionForReserveToUser(locker);
        commonConditionForReserve(user, lockerDetail, locker);
        Long registerLockerId = reservationQueryPort.registerLocker(UserJpaEntity.of(user), lockerDetail);
        Set<SseEmitter> sseEmitters = SseEmitterService.getEmitterMap()
                .getOrDefault("LOCKER_" + dto.getMajorId().toString(), new HashSet<>());
        SseEmitter.SseEventBuilder reservedLockerEventBuilder = SseEmitter.event()
                .name("reservedLockerDetailId")
                .data(registerLockerId)
                .reconnectTime(3000L);
        sseEmitters.stream().forEach(sseEmitter -> sendEvent(sseEmitter, reservedLockerEventBuilder));

        log.info("예약 완료 : [학번 {}, 사물함 번호 {}]", user.getStudentNum(), lockerDetail.getLockerNum());
        return LockerRegisterResponseDto
                .of(lockerDetail.getLockerNum(), user.getStudentNum(), locker.getName());
    }

    private static void distinctConditionForReserveToUser(Locker locker) {
        if (!locker.isDeadlineValid()) {
            throw new IsNotReserveTimeException();
        }
    }

    private void commonConditionForReserve(User user, LockerDetail lockerDetail, Locker locker) {
        if (!locker.getPermitUserState().contains(user.getUserState())) {
            throw new InvalidReservedStatusException();
        }
        if (!locker.getPermitUserTier().contains(user.getUserTier())) {
            throw new NotMatchUserTierAndLockerException();
        }
        if (isReservationNegativeByLockerDetailId(lockerDetail.getId())) {
            throw new AlreadyReservedLockerException();
        }
        if (isReservationNegativeById(user.getId())) {
            throw new AlreadyReservedUserException();
        }
    }
    private boolean isReservationNegativeByLockerDetailId(Long lockerDetailId) {
        Optional<Reservation> reservation = reservationQueryPort.findByLockerDetailId(lockerDetailId);
        if (reservation.isPresent()) {
            return true;
        }
        return false;
    }

    private boolean isReservationNegativeById(Long userId) {
        Optional<Reservation> reservation = reservationQueryPort.findByUserId(userId);
        if (reservation.isPresent()) {
            return true;
        }
        return false;
    }


    public void cancelLockerByStudentNum(UserCancelLockerRequestDto cancelLockerDto) {
        log.info("{} : 사물함 취소", cancelLockerDto.getUserId());
        User user = userQueryPort.findById(cancelLockerDto.getUserId()).orElseThrow(NotFoundUserException::new);
        Reservation reservation = reservationQueryPort
                .findAllByUserIdAndLockerDetailId(user.getId(),
                        cancelLockerDto.getLockerDetailId()).orElseThrow(NotFoundReservationException::new);
        reservationQueryPort.deleteById(reservation.getId());
    }

    public boolean isReservationExistByStudentNum(String studentNum) {//무언가 있다면
        return !reservationQueryPort.findReservationByStudentNum(studentNum)
                .isEmpty();
    }
}
