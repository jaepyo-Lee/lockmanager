package com.ime.lockmanager.locker.adapter.out;

import com.ime.lockmanager.locker.application.port.out.LockerDetailQueryPort;
import com.ime.lockmanager.locker.domain.LockerDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LockerDetailQueryRepository implements LockerDetailQueryPort {
    private final LockerDetailRepository lockerDetailRepository;
    @Override
    public LockerDetail save(LockerDetail lockerDetail) {
        return lockerDetailRepository.save(lockerDetail);
    }

    @Override
    public Optional<LockerDetail> findByIdWithLocker(Long lockerDetailId) {
        return lockerDetailRepository.findByIdWithLocker(lockerDetailId);
    }
}
