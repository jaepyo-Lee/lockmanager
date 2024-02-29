package com.ime.lockmanager.locker.application.port.out;

import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;

import java.util.List;
import java.util.Optional;

public interface LockerDetailQueryPort {

    Optional<LockerDetail> findByIdWithLocker(Long lockerDetailId);

    List<LockerDetail> findByLockerId(Long lockerId);

    Optional<LockerDetail> findById(Long lockerDetailId);


}
