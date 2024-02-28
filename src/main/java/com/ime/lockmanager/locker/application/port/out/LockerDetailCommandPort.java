package com.ime.lockmanager.locker.application.port.out;

import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;

import java.util.List;

public interface LockerDetailCommandPort {
    void deleteAll();

    LockerDetail save(LockerDetail lockerDetail);

    List<LockerDetail> saveAll(List<LockerDetail> saveLockerDetails);
}
