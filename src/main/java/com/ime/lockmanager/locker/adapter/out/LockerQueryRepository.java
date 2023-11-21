package com.ime.lockmanager.locker.adapter.out;

import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.major.domain.Major;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class LockerQueryRepository implements LockerQueryPort {

    private final LockerJpaRepository lockerJpaRepository;

    @Override
    public Locker save(Locker locker) {
        return lockerJpaRepository.save(locker);
    }

    @Override
    public void deleteAll() {
        lockerJpaRepository.deleteAll();
    }

    @Override
    public Optional<Locker> findByLockerId(Long lockerId) {
        return lockerJpaRepository.findById(lockerId);
    }

    @Override
    public List<Locker> findAll() {
        return lockerJpaRepository.findAll();
    }

    @Override
    public List<Long> findNotReservedLockerId() {
        List<Long> notReservationLockerId = lockerJpaRepository.findNotReservationLocker();
        return notReservationLockerId;
    }

    @Override
    public List<Locker> findLockerByUserMajor(Major major) {
        return lockerJpaRepository.findLockerByUserMajor(major);
    }
}
