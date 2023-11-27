package com.ime.lockmanager.locker.application.port.in;

import com.ime.lockmanager.locker.domain.locker.Locker;
import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import com.ime.lockmanager.locker.domain.lockerdetail.dto.LockerDetailCreateDto;
import com.ime.lockmanager.major.domain.Major;

import java.util.List;

public interface LockerDetailUseCase {
    LockerDetail saveLockerDetail(LockerDetailCreateDto lockerDetailCreateDto);

    List<LockerDetail> findLockerDetailByLocker(Locker locker);
}
