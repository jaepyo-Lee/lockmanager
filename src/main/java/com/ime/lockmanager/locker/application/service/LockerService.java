package com.ime.lockmanager.locker.application.service;

import com.ime.lockmanager.locker.adapter.in.req.LockerRegisterRequest;
import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
@Slf4j
@RequiredArgsConstructor
@Service
public class LockerService implements LockerUseCase {
    private final UserQueryPort userQueryPort;
    private final LockerQueryPort lockerQueryPort;

    @Transactional
    @Override
    public LockerRegisterResponseDto register(LockerRegisterRequestDto dto) throws IllegalAccessException {
        User byStudentNum = userQueryPort.findByStudentNum(dto.getStudentNum()).orElseThrow(
                () -> new NullPointerException("사용자 유효하지 않습니다.")
        );
        Locker byLockerId = lockerQueryPort.findByLockerId(dto.getLockerNum()).orElseThrow(
                () -> new NullPointerException("사물함이 유효하지 않습니다.")
        );
        if(byStudentNum.getLocker()==null){
            if(byLockerId.isUsable()){
                byStudentNum.registerLocker(byLockerId);
                return LockerRegisterResponseDto.builder()
                        .lockerNum(byLockerId.getId())
                        .studentName(byStudentNum.getName())
                        .build();
            }
            throw new IllegalAccessException("이미 예약된 사물함입니다");
        }
        throw new IllegalAccessException("이미 사물함을 예약한 사용자입니다.");

    }
}
