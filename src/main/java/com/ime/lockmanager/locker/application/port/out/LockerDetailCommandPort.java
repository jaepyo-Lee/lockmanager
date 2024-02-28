package com.ime.lockmanager.locker.application.port.out;

import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;

public interface LockerDetailCommandPort {
    void deleteAll();

    LockerDetail save(LockerDetail lockerDetail);
}
