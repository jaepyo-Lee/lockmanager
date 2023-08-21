package com.ime.lockmanager.locker.application.service;

import com.ime.lockmanager.common.format.exception.locker.AlreadyReservedLockerException;
import com.ime.lockmanager.common.format.exception.locker.IsNotReserveTimeException;
import com.ime.lockmanager.common.format.exception.locker.NotFoundLockerException;
import com.ime.lockmanager.common.format.exception.locker.ReserveTimeNullException;
import com.ime.lockmanager.common.format.exception.user.AlreadyReservedUserException;
import com.ime.lockmanager.common.format.exception.user.InvalidReservedStatusException;
import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.req.LockerSetTimeRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerPeriodResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerReserveResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.locker.domain.Period;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
class LockerService implements LockerUseCase  {
    private final UserQueryPort userQueryPort;
    private final LockerQueryPort lockerQueryPort;
    private final static List<String> notInvalidStatus = new ArrayList<>(List.of("휴햑", "재학"));
    @Override
    public void initLockerInfo() {
        log.info("사물함 초기화 진행");
        List<User> allUsers = userQueryPort.findAll();
        allUsers.stream().parallel().forEach((user)->user.cancelLocker());
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
                    if(byStudentNum.getLocker()==null){
                        if(byLockerId.isUsable()){
                            byStudentNum.registerLocker(byLockerId);
                            log.info("예약 완료 : [학번 {}, 사물함 번호 {}]",dto.getStudentNum(),dto.getLockerNum());
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

    @Transactional(readOnly = true)
    @Override
    public LockerReserveResponseDto findReserveLocker() {
        List<Long> reservedLockerId = lockerQueryPort.findReservedLockerId();
        return LockerReserveResponseDto.builder()
                .lockerIdList(reservedLockerId)
                .build();
    }

    @Transactional
    @Override
    public void setLockerPeriod(LockerSetTimeRequestDto requestDto) {
        List<Locker> lockers = lockerQueryPort.findAll();
        log.info("시간설정 시작");
        lockers.stream().filter(locker -> locker.getPeriod() == null).parallel()
                .forEach(locker -> locker.setPeriod(new Period(requestDto.getStartDateTime(), requestDto.getEndDateTime()))
                );

        /*for (Locker locker : lockers) {
            if(locker.getPeriod()==null){
                locker.setPeriod(new Period(requestDto.getStartDateTime(),requestDto.getEndDateTime()));
            }
            else{
                locker.getPeriod().modifiedDateTime(requestDto);
            }
        }*/
        log.info("시간설정 완료");
    }

    @Transactional(readOnly = true)
    @Override
    public LockerPeriodResponseDto getLockerPeriod() {
        List<Locker> lockers = lockerQueryPort.findAll();
        Locker locker = lockers.get(0);
        if(locker.getPeriod() == null){
            return LockerPeriodResponseDto.builder()
                    .endDateTime(null)
                    .startDateTime(null)
                    .build();
        } else{
            return LockerPeriodResponseDto.builder()
                    .startDateTime(locker.getPeriod().getStartDateTime())
                    .endDateTime(locker.getPeriod().getEndDateTime())
                    .build();
        }
    }
}
