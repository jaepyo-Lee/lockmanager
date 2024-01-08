package com.ime.lockmanager.locker.application.port.in;

import com.ime.lockmanager.locker.adapter.in.res.LockersInfoInMajorResponse;
import com.ime.lockmanager.locker.application.port.in.req.FindAllLockerInMajorRequestDto;
import com.ime.lockmanager.locker.application.port.in.req.LockerCreateRequestDto;
import com.ime.lockmanager.locker.application.port.in.req.LockerSetTimeRequestDto;
import com.ime.lockmanager.locker.application.port.in.req.ModifyLockerInfoReqeustDto;
import com.ime.lockmanager.locker.application.port.in.res.LeftLockerResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerCreateResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerPeriodResponseDto;
import com.ime.lockmanager.locker.domain.locker.Locker;
import com.ime.lockmanager.major.domain.Major;

import java.io.IOException;
import java.util.List;

public interface LockerUseCase {

    LeftLockerResponseDto getLeftLocker(Long majorId);

    LockerCreateResponseDto createLocker(LockerCreateRequestDto lockerCreateRequestDto, Long majorId) throws IOException;

    LockersInfoInMajorResponse findAllLockerInMajor(FindAllLockerInMajorRequestDto build);

    void modifyLockerInfo(ModifyLockerInfoReqeustDto toReqeustDto) throws IOException;
}
