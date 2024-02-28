package com.ime.lockmanager.locker.adapter.out;

import com.ime.lockmanager.locker.application.port.out.LockerDetailQueryPort;
import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LockerDetailQueryRepository implements LockerDetailQueryPort {
    private final LockerDetailJpaRepository lockerDetailJpaRepository;
    @Override
    public Optional<LockerDetail> findByIdWithLocker(Long lockerDetailId) {
        return lockerDetailJpaRepository.findByIdWithLocker(lockerDetailId);
    }

    @Override
    public List<LockerDetail> findLockerDetailByLocker(Long lockerId) {
        return lockerDetailJpaRepository.findByLockerId(lockerId);
    }

    @Override
    public Optional<LockerDetail> findById(Long lockerDetailId) {
        return lockerDetailJpaRepository.findById(lockerDetailId);
    }
}
