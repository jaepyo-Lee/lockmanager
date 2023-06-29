package com.ime.lockmanager.locker.adapter.out;

import com.ime.lockmanager.locker.domain.Locker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LockerJpaRepository extends JpaRepository<Locker,Long> {
    @Override
    Optional<Locker> findById(Long aLong);
}
