package com.ime.lockmanager.locker.adapter.out;

import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.locker.Locker;
import com.ime.lockmanager.major.domain.Major;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class LockerQueryRepository implements LockerQueryPort {

    private final LockerJpaRepository lockerJpaRepository;

    @Override
    public List<Locker> findByMajorId(Long majorId) {
        return lockerJpaRepository.findByMajorId(majorId);
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
    public List<Locker> findLockerByUserMajor(Major major) {
        return lockerJpaRepository.findLockerByUserMajor(major);
    }

}
