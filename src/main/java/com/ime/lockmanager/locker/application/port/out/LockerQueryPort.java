package com.ime.lockmanager.locker.application.port.out;

import com.ime.lockmanager.locker.domain.Locker;

import java.util.List;
import java.util.Optional;

public interface LockerQueryPort {
    Optional<Locker> findByLockerId(Long lockerId);

    List<Long> findReservedLockerId();

    List<Locker> findAll();

}
