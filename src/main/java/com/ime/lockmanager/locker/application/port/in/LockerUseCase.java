package com.ime.lockmanager.locker.application.port.in;

import com.ime.lockmanager.locker.application.port.in.req.LockerSetTimeRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerPeriodResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.ReservationOfLockerResponseDto;
import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.major.domain.Major;

import java.util.List;

public interface LockerUseCase {


    void setLockerPeriod(LockerSetTimeRequestDto requestDto);

    LockerPeriodResponseDto getLockerPeriod();

    List<Locker> findLockerByUserMajor(Major major);
}
