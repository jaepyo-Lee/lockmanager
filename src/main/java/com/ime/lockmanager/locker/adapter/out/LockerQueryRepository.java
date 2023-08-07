package com.ime.lockmanager.locker.adapter.out;

import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.Locker;
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
    public Optional<Locker> findByLockerId(Long lockerId) {
        return lockerJpaRepository.findById(lockerId);
    }

    @Override
    public List<Long> findReservedLockerId() {
        return lockerJpaRepository.findReservedLockerId();
    }

    @Override
    public List<Locker> findAll() {
        return lockerJpaRepository.findAll();
    }

    @Override
    public List<Long> findNotReservedLockerId() {
        List<Long> notReservedLockerId = new ArrayList<>();
        List<Locker> all = lockerJpaRepository.findAll();
        for (Locker locker : all) {
            if(locker.getUser()==null){
                notReservedLockerId.add(locker.getId());
            }
        }
        return notReservedLockerId;
    }
}
