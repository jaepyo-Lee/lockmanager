package com.ime.lockmanager.locker.adapter.out;

import com.ime.lockmanager.locker.domain.Locker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LockerJpaRepository extends JpaRepository<Locker,Long> {
}
