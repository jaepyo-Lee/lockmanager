package com.ime.lockmanager.locker.application.port.in;

import com.ime.lockmanager.locker.application.port.in.req.FindAllLockerInMajorRequestDto;
import com.ime.lockmanager.locker.application.port.in.req.LockerCreateRequestDto;
import com.ime.lockmanager.locker.application.port.in.req.LockerSetTimeRequestDto;
import com.ime.lockmanager.locker.application.port.in.req.ModifyLockerInfoReqeustDto;
import com.ime.lockmanager.locker.application.port.in.res.AllLockersInMajorResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.LeftLockerResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerCreateResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerPeriodResponseDto;
import com.ime.lockmanager.locker.domain.locker.Locker;
import com.ime.lockmanager.major.domain.Major;

import java.util.List;

public interface LockerUseCase {

    LeftLockerResponseDto getLeftLocker(String studentNum);

    void setLockerPeriod(LockerSetTimeRequestDto requestDto);

    LockerPeriodResponseDto getLockerPeriod();

    List<Locker> findLockerByUserMajor(Major major);

    LockerCreateResponseDto createLocker(LockerCreateRequestDto lockerCreateRequestDto, String studentNum);

    List<AllLockersInMajorResponseDto> findAllLockerInMajor(FindAllLockerInMajorRequestDto build);

    void modifyLockerInfo(ModifyLockerInfoReqeustDto toReqeustDto);
}
