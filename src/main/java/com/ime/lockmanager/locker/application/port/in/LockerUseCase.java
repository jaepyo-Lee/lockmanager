package com.ime.lockmanager.locker.application.port.in;

import com.ime.lockmanager.locker.application.port.in.req.LockerSetTimeRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerPeriodResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.ReservationOfLockerResponseDto;

public interface LockerUseCase {


    void setLockerPeriod(LockerSetTimeRequestDto requestDto);

    LockerPeriodResponseDto getLockerPeriod();

}
