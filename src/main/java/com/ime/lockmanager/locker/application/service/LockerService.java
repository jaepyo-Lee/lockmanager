package com.ime.lockmanager.locker.application.service;

import com.ime.lockmanager.common.exception.locker.AlreadyReservedLockerException;
import com.ime.lockmanager.common.exception.locker.NotFoundLockerException;
import com.ime.lockmanager.common.exception.user.AlreadyReservedUserException;
import com.ime.lockmanager.common.exception.user.NotFoundUserException;
import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.awt.font.TextHitInfo;
import java.rmi.AlreadyBoundException;

import static com.ime.lockmanager.locker.exception.LockerExceptionStatus.ALREADY_RESERVED_LOCKER;
import static com.ime.lockmanager.locker.exception.LockerExceptionStatus.NOT_EXIST_LOCKER;

@Slf4j
@RequiredArgsConstructor
@Service
public class LockerService implements LockerUseCase  {
    private final UserQueryPort userQueryPort;
    private final LockerQueryPort lockerQueryPort;

    @Transactional
    @Override
    public LockerRegisterResponseDto register(LockerRegisterRequestDto dto) throws Exception {
        User byStudentNum = userQueryPort.findByStudentNum(dto.getStudentNum())
                .orElseThrow(NotFoundUserException::new);
        Locker byLockerId = lockerQueryPort.findByLockerId(dto.getLockerNum())
                .orElseThrow(NotFoundLockerException::new);
        if(byStudentNum.getLocker()==null){
            if(byLockerId.isUsable()){
                byStudentNum.registerLocker(byLockerId);
                return LockerRegisterResponseDto.builder()
                        .lockerNum(byLockerId.getId())
                        .studentName(byStudentNum.getName())
                        .build();
            }
            throw new AlreadyReservedLockerException();
        }
        throw new AlreadyReservedUserException();
    }
}
