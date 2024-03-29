package com.ime.lockmanager.locker.application.port.in;

import com.ime.lockmanager.locker.application.port.in.dto.CreateLockerDetailDto;
import com.ime.lockmanager.locker.domain.locker.Locker;
import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import com.ime.lockmanager.locker.domain.lockerdetail.dto.LockerDetailCreateDto;

import java.util.List;

public interface LockerDetailUseCase {

    void createLockerDetails(CreateLockerDetailDto createLockerDetailDto, Locker saveLocker);
}
