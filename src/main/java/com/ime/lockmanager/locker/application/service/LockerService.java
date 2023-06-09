package com.ime.lockmanager.locker.application.service;

import com.ime.lockmanager.common.exception.locker.AlreadyReservedLockerException;
import com.ime.lockmanager.common.exception.locker.IsNotReserveTimeException;
import com.ime.lockmanager.common.exception.locker.NotFoundLockerException;
import com.ime.lockmanager.common.exception.locker.ReserveTimeNullException;
import com.ime.lockmanager.common.exception.user.AlreadyReservedUserException;
import com.ime.lockmanager.common.exception.user.NotFoundUserException;
import com.ime.lockmanager.locker.adapter.in.res.LockerReserveResponse;
import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerReserveResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class LockerService implements LockerUseCase  {
    private final UserQueryPort userQueryPort;
    private final LockerQueryPort lockerQueryPort;

    @Transactional
    @Override
    public LockerRegisterResponseDto register(LockerRegisterRequestDto dto) throws Exception {
        log.info("예약 시작 : [학번 {}, 사물함 번호 {}]",dto.getStudentNum(),dto.getLockerNum());
        User byStudentNum = userQueryPort.findByStudentNum(dto.getStudentNum())
                .orElseThrow(NotFoundUserException::new);
        Locker byLockerId = lockerQueryPort.findByLockerId(dto.getLockerNum())
                .orElseThrow(NotFoundLockerException::new);
        if(byLockerId.getReservedTime()!=null){
            if(byLockerId.getReservedTime().isBefore(LocalDateTime.now())){
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
            throw new IsNotReserveTimeException();
        }
        throw new ReserveTimeNullException();
    }

    @Override
    public LockerReserveResponseDto findReserveLocker() {
        List<Long> reservedLockerId = lockerQueryPort.findReservedLocker();
        return LockerReserveResponseDto.builder()
                .lockerId(reservedLockerId)
                .build();
    }
}
