package com.ime.lockmanager.locker.adapter.out;

import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.Locker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class LockerQueryRepository implements LockerQueryPort {

    private final LockerJpaRepository lockerJpaRepository;
    @Override
    public Optional<Locker> findByLockerId(Long lockerId) {
        return lockerJpaRepository.findById(lockerId);
    }

    @Override
    public List<Long> findReservedLocker() {
        return lockerJpaRepository.findReservedLocker();
    }
}
