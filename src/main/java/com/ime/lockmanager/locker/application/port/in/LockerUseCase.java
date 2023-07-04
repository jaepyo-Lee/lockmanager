package com.ime.lockmanager.locker.application.port.in;

import com.ime.lockmanager.locker.adapter.in.req.LockerRegisterRequest;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;

public interface LockerUseCase {
    LockerRegisterResponseDto register(LockerRegisterRequestDto lockerRegisterRequestDto) throws IllegalAccessException, Exception;
}
