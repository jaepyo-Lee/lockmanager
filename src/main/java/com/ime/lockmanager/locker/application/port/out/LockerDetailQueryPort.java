package com.ime.lockmanager.locker.application.port.out;

import com.ime.lockmanager.locker.domain.locker.Locker;
import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import com.ime.lockmanager.major.domain.Major;

import java.util.List;
import java.util.Optional;

public interface LockerDetailQueryPort {
    LockerDetail save(LockerDetail lockerDetail);

    Optional<LockerDetail> findByIdWithLocker(Long lockerDetailId);

    List<LockerDetail> findLockerDetailByLocker(Long lockerId);

    Optional<LockerDetail> findById(Long lockerDetailId);

    void deleteAll();
}
