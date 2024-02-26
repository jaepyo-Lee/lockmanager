package com.ime.lockmanager.reservation.application.service;

import com.ime.lockmanager.common.aop.meta.DistributeLock;
import com.ime.lockmanager.common.format.exception.locker.*;
import com.ime.lockmanager.common.format.exception.reservation.NotFoundReservationException;
import com.ime.lockmanager.common.format.exception.reservation.NotMatchUserTierAndLockerException;
import com.ime.lockmanager.common.format.exception.user.AlreadyReservedUserException;
import com.ime.lockmanager.common.format.exception.user.InvalidReservedStatusException;
import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.common.sse.SseEmitterService;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerDetailQueryPort;
import com.ime.lockmanager.locker.domain.locker.Locker;
import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import com.ime.lockmanager.reservation.application.port.in.ReservationUseCase;
import com.ime.lockmanager.reservation.application.port.in.req.ChangeReservationRequestDto;
import com.ime.lockmanager.reservation.application.port.out.ReservationQueryPort;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.application.port.in.req.UserCancelLockerRequestDto;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;

import static com.ime.lockmanager.common.sse.SseEmitterService.sendEvent;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReservationService implements ReservationUseCase {
    private final UserQueryPort userQueryPort;
    private final ReservationQueryPort reservationQueryPort;
    private final LockerDetailQueryPort lockerDetailQueryPort;

    private static final String LOCKER_KEY = "LOCKER_";

    @Override
    public void resetReservation(Long lockerId) {
        List<LockerDetail> lockerDetailsByLocker = lockerDetailQueryPort.findLockerDetailByLocker(lockerId);
        List<Reservation> reservationsByLockerDetails = reservationQueryPort.findAllByLockerDetails(lockerDetailsByLocker);

    }


    @Override
    @DistributeLock(identifier = LOCKER_KEY, key = "#dto.lockerDetailId")
    public LockerRegisterResponseDto registerForAdmin(LockerRegisterRequestDto dto) throws Exception {
        User user = userQueryPort.findById(dto.getUserId()).orElseThrow(NotFoundUserException::new);
        LockerDetail lockerDetail = lockerDetailQueryPort.findByIdWithLocker(dto.getLockerDetailId())
                .orElseThrow(InvalidLockerDetailException::new);
        log.info("(사용자)예약 시작 : [학번 {}, 사물함 번호 {}]", user.getStudentNum(), lockerDetail.getLockerNum());
        Locker locker = lockerDetail.getLocker();
        commonConditionForReserve(user, lockerDetail, locker);
        reservationQueryPort.registerLocker(user, lockerDetail);
        return LockerRegisterResponseDto.of((lockerDetail.getLockerNum()),
                user.getStudentNum(),
                lockerDetail.getLocker().getName());

    }


    @Override
    @DistributeLock(identifier = LOCKER_KEY, key = "#dto.lockerDetailId")
    public LockerRegisterResponseDto registerForUser(LockerRegisterRequestDto dto) throws Exception {
        User user = userQueryPort.findById(dto.getUserId()).orElseThrow(NotFoundUserException::new);
        LockerDetail lockerDetail = lockerDetailQueryPort.findByIdWithLocker(dto.getLockerDetailId())
                .orElseThrow(InvalidLockerDetailException::new);
        log.info("(사용자)예약 시작 : [학번 {}, 사물함 번호 {}]", user.getStudentNum(), lockerDetail.getLockerNum());
        Locker locker = lockerDetail.getLocker();
        distinctConditionForReserveToUser(locker);
        Long registerLockerId = reservation(user, lockerDetail, locker);
//        sendSSEMsg(dto.getMajorId(), registerLockerId, "reservedLockerDetailId");

        log.info("예약 완료 : [학번 {}, 사물함 번호 {}]", user.getStudentNum(), lockerDetail.getLockerNum());
        return LockerRegisterResponseDto
                .of(lockerDetail.getLockerNum(), user.getStudentNum(), locker.getName());
    }

    private Long reservation(User user, LockerDetail lockerDetail, Locker locker) {
        commonConditionForReserve(user, lockerDetail, locker);
        Long registerLockerId = register(user, lockerDetail);
        return registerLockerId;
    }

    private Long register(User user, LockerDetail lockerDetail) {
        Long registerLockerId = reservationQueryPort.registerLocker(user, lockerDetail);
        return registerLockerId;
    }

    private static void distinctConditionForReserveToUser(Locker locker) {
        if (!locker.isDeadlineValid()) {
            throw new IsNotReserveTimeException();
        }
    }

    private void commonConditionForReserve(User user, LockerDetail lockerDetail, Locker locker) {
        lockerConditions(user, lockerDetail, locker);
        userConditions(user);
    }

    private void userConditions(User user) {
        if (isReservationNegativeById(user.getId())) {
            throw new AlreadyReservedUserException();
        }
    }

    private void lockerConditions(User user, LockerDetail lockerDetail, Locker locker) {
        if (!locker.getPermitUserState().contains(user.getUserState())) {
            throw new InvalidReservedStatusException();
        }
        if (!locker.getPermitUserTier().contains(user.getUserTier())) {
            throw new NotMatchUserTierAndLockerException();
        }
        if (isReservationNegativeByLockerDetailId(lockerDetail.getId())) {
            throw new AlreadyReservedLockerException();
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


    public Long cancelLockerByStudentNum(UserCancelLockerRequestDto cancelLockerDto) {
        log.info("{} : 사물함 취소", cancelLockerDto.getUserId());
        User user = userQueryPort.findById(cancelLockerDto.getUserId()).orElseThrow(NotFoundUserException::new);
        Reservation reservation = reservationQueryPort
                .findAllByUserIdAndLockerDetailId(user.getId(),
                        cancelLockerDto.getLockerDetailId()).orElseThrow(NotFoundReservationException::new);
        Long cancelLockerDetailId = reservation.getLockerDetail().cancel();

        reservationQueryPort.deleteById(reservation.getId());
        return cancelLockerDetailId;
    }

    @Override
    @DistributeLock(identifier = LOCKER_KEY, key = "#dto.newLockerDetailId")
    public Long changeReservation(ChangeReservationRequestDto dto) {
        User user = userQueryPort.findById(dto.getUserId())
                .orElseThrow(NotFoundUserException::new);
        LockerDetail newLockerDetail = lockerDetailQueryPort.findByIdWithLocker(dto.getNewLockerDetailId())
                .orElseThrow(InvalidLockerDetailException::new);
        LockerDetail originLockerDetail = lockerDetailQueryPort.findByIdWithLocker(dto.getOriginLockerDetailId())
                .orElseThrow(InvalidLockerDetailException::new);
        Locker locker = newLockerDetail.getLocker();

        // 1. 변경하고자하는 사물함이 예약이 안되었는지
        lockerConditions(user, newLockerDetail, locker);

        // 2. 기존 사물함 취소
        Long cancelLockerDetailId = cancelLockerByStudentNum(UserCancelLockerRequestDto
                .of(user.getId(), originLockerDetail.getId()));

        // 3. 취소했다는 sse 통신
//        sendSSEMsg(dto.getMajorId(), cancelLockerDetailId, "cancelLockerDetailId");

        // 4. 변경하고자하는 사물함 예약
        Long registerNewLockerId = register(user, newLockerDetail);

        // 5. 예약했다는 sse 통신
//        sendSSEMsg(dto.getMajorId(), registerNewLockerId, "reservedLockerDetailId");

        return registerNewLockerId;
    }

    private static void sendSSEMsg(Long majorId, Long lockerDetailId, String sseEmitterName) {
        Set<SseEmitter> sseEmitters = SseEmitterService.getEmitterMap()
                .getOrDefault("LOCKER_" + majorId.toString(), new HashSet<>());
        SseEmitter.SseEventBuilder lockerEventBuilder = SseEmitter.event()
                .name(sseEmitterName)
                .data(lockerDetailId)
                .reconnectTime(3000L);
        sseEmitters.stream().forEach(sseEmitter -> sendEvent(sseEmitter, lockerEventBuilder));
    }
}
