package com.ime.lockmanager.locker.adapter.out;

import com.ime.lockmanager.locker.application.port.out.LockerDetailCommandPort;
import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LockerDetailCommandRepository implements LockerDetailCommandPort {
    private final LockerDetailJpaRepository lockerDetailJpaRepository;
    @Override
    public LockerDetail save(LockerDetail lockerDetail) {
        return lockerDetailJpaRepository.save(lockerDetail);
    }

    @Override
    public void deleteAll(){
        lockerDetailJpaRepository.deleteAll();
    }
}
