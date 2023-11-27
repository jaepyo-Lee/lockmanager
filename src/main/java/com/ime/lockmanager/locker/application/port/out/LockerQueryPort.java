package com.ime.lockmanager.locker.application.port.out;

import com.ime.lockmanager.locker.domain.locker.Locker;
import com.ime.lockmanager.major.domain.Major;

import java.util.List;
import java.util.Optional;

public interface LockerQueryPort {
    Optional<Locker> findByLockerId(Long lockerId);

    List<Locker> findAll();

    List<Long> findNotReservedLockerId();

    void deleteAll();

    Locker save(Locker locker);

    List<Locker> findLockerByUserMajor(Major major);
}
