package com.ime.lockmanager.locker.adapter.out;

import com.ime.lockmanager.locker.domain.Locker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LockerJpaRepository extends JpaRepository<Locker,Long> {
    @Override
    Optional<Locker> findById(Long aLong);

    @Query("select L.id FROM LOCKER_TABLE L join L.user U")
    List<Long> findReservedLocker();
}
