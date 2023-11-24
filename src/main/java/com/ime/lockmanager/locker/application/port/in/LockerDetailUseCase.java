package com.ime.lockmanager.locker.application.port.in;

import com.ime.lockmanager.locker.domain.LockerDetail;
import com.ime.lockmanager.locker.domain.dto.LockerDetailCreateDto;
import org.springframework.stereotype.Service;

public interface LockerDetailUseCase {
    LockerDetail saveLockerDetail(LockerDetailCreateDto lockerDetailCreateDto);
}
