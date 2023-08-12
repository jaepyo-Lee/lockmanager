package com.ime.lockmanager.locker.application.port.in;

import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.req.LockerSetTimeRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerPeriodResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerReserveResponseDto;

public interface LockerUseCase {
    LockerRegisterResponseDto register(LockerRegisterRequestDto lockerRegisterRequestDto) throws Exception;

    LockerReserveResponseDto findReserveLocker();

    void setLockerPeriod(LockerSetTimeRequestDto requestDto);

    LockerPeriodResponseDto getLockerPeriod();

    void initLockerInfo();
}
