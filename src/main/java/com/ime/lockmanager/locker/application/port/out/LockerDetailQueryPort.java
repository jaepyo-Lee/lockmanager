package com.ime.lockmanager.locker.application.port.out;

import com.ime.lockmanager.locker.domain.LockerDetail;

import java.util.Optional;

public interface LockerDetailQueryPort {
    LockerDetail save(LockerDetail lockerDetail);

    Optional<LockerDetail> findByIdWithLocker(Long lockerDetailId);
}
