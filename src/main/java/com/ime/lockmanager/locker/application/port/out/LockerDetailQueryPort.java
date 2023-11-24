package com.ime.lockmanager.locker.application.port.out;

import com.ime.lockmanager.locker.domain.LockerDetail;

public interface LockerDetailQueryPort {
    LockerDetail save(LockerDetail lockerDetail);
}
