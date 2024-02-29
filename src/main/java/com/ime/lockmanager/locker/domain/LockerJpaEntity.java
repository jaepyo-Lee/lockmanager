package com.ime.lockmanager.locker.domain;

import com.ime.lockmanager.locker.domain.locker.Locker;

public class LockerJpaEntity {
    public static Locker of(Locker locker){
        return Locker.builder()
                .period(locker.getPeriod())
                .id(locker.getId())
                .build();
    }
}
