package com.ime.lockmanager.locker.domain;

public class LockerJpaEntity {
    public static Locker of(Locker locker){
        return Locker.builder()
                .period(locker.getPeriod())
                .id(locker.getId())
                .build();
    }
}
