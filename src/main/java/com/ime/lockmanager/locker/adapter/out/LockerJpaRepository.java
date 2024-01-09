package com.ime.lockmanager.locker.adapter.out;

import com.ime.lockmanager.locker.domain.locker.Locker;
import com.ime.lockmanager.major.domain.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LockerJpaRepository extends JpaRepository<Locker,Long> {
    @Override
    Optional<Locker> findById(Long aLong);

    @Override
    List<Locker> findAll();

    @Query("select L.id From LOCKER_TABLE L left join L.reservation as R where R.id=null")
    List<Long> findNotReservationLocker();


    @Query("select L from LOCKER_TABLE as L join fetch L.major as M where M=:major")
    List<Locker> findLockerByUserMajor(@Param("major")Major major);

    Locker findByName(String name);

    @Query("select L from LOCKER_TABLE as L join fetch L.major as M where M.id=:majorId")
    List<Locker> findByMajorId(@Param("majorId") Long majorId);
}
