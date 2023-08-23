package com.ime.lockmanager.reservation.application.service;

import com.ime.lockmanager.common.format.exception.locker.*;
import com.ime.lockmanager.common.format.exception.user.AlreadyReservedUserException;
import com.ime.lockmanager.common.format.exception.user.InvalidReservedStatusException;
import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.ReservationOfLockerResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.locker.domain.LockerJpaEntity;
import com.ime.lockmanager.reservation.adapter.out.dto.DeleteReservationByStudentNumDto;
import com.ime.lockmanager.reservation.application.port.in.ReservationUseCase;
import com.ime.lockmanager.reservation.application.port.out.ReservationQueryPort;
import com.ime.lockmanager.reservation.application.port.out.dto.FindReservationByLockerNumDto;
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

import static java.time.LocalDateTime.now;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReservationService implements ReservationUseCase {

    private final UserQueryPort userQueryPort;
    private final LockerQueryPort lockerQueryPort;
    private final ReservationQueryPort reservationQueryPort;
    private final static List<String> notInvalidStatus = new ArrayList<>(List.of("휴햑", "재학"));

    @Override
    public void resetReservation(Principal principal) {
        log.info("{} : 모든 사물함 초기화",principal.getName());
        reservationQueryPort.deleteAll();
    }

    @Transactional(readOnly = true)
    @Override
    public ReservationOfLockerResponseDto findReservedLockers() {
        List<Long> reservedLockersIdList = reservationQueryPort.findReservedLockers();
        return ReservationOfLockerResponseDto.builder()
                .lockerIdList(reservedLockersIdList)
                .build();
    }



    @Override
    public LockerRegisterResponseDto register(LockerRegisterRequestDto dto) throws Exception {
        log.info("예약 시작 : [학번 {}, 사물함 번호 {}]",dto.getStudentNum(),dto.getLockerNum());
        User byStudentNum = userQueryPort.findByStudentNum(dto.getStudentNum())
                .orElseThrow(NotFoundUserException::new);
        Locker byLockerId = lockerQueryPort.findByLockerId(dto.getLockerNum())
                .orElseThrow(NotFoundLockerException::new);
        if(byLockerId.getPeriod()!=null){
            if(
                    byLockerId.getPeriod().getEndDateTime().isAfter(now()) &&
                            byLockerId.getPeriod().getStartDateTime().isBefore(now())
            ){
                if(notInvalidStatus.contains(byStudentNum.getStatus())){
                    if (!reservationQueryPort.isReservationByStudentNum(FindReservationByStudentNumDto.builder()
                            .studentNum(byStudentNum.getStudentNum())
                            .build())) {
                        if (!reservationQueryPort.isReservationByLockerId(FindReservationByLockerNumDto.builder()
                                .lockerNum(byLockerId.getId())
                                .build())) {
                            reservationQueryPort.registerLocker(UserJpaEntity.of(byStudentNum), LockerJpaEntity.of(byLockerId));
                            log.info("예약 완료 : [학번 {}, 사물함 번호 {}]", dto.getStudentNum(), dto.getLockerNum());
                            return LockerRegisterResponseDto.builder()
                                    .lockerNum(byLockerId.getId())
                                    .studentNum(byStudentNum.getStudentNum())
                                    .build();
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

    public void cancelLockerByStudentNum(UserCancelLockerRequestDto cancelLockerDto) {
        Reservation reservationByStudentNum = reservationQueryPort.findReservationByStudentNum(cancelLockerDto.getStudentNum());
        if(reservationByStudentNum==null){
            throw new InvalidCancelLockerException();
        }
        log.info("{} : 사물함 취소",cancelLockerDto.getStudentNum());
        reservationQueryPort.deleteByStudentNum(DeleteReservationByStudentNumDto.builder().studentNum(cancelLockerDto.getStudentNum()).build());
    }
}
