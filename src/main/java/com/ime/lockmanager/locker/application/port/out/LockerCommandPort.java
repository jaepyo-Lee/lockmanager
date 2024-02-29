package com.ime.lockmanager.locker.application.port.out;

import com.ime.lockmanager.locker.domain.locker.Locker;

public interface LockerCommandPort {
    void deleteAll();

    Locker save(Locker locker);
}
