package com.ime.lockmanager.locker.adapter.out;

import com.ime.lockmanager.locker.application.port.out.LockerCommandPort;
import com.ime.lockmanager.locker.domain.locker.Locker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LockerCommandRepository implements LockerCommandPort {
    private final LockerJpaRepository lockerJpaRepository;
    @Override
    public Locker save(Locker locker) {
        return lockerJpaRepository.save(locker);
    }

    @Override
    public void deleteAll() {
        lockerJpaRepository.deleteAll();
    }
}

