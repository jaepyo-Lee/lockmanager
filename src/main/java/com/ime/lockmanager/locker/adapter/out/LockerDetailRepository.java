package com.ime.lockmanager.locker.adapter.out;

import com.ime.lockmanager.locker.domain.LockerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LockerDetailRepository extends JpaRepository<LockerDetail,Long> {

    @Query("SELECT LD FROM LOCKER_DETAIL_TABLE LD JOIN FETCH LD.locker L WHERE LD.id = :lockerDetailId")
    Optional<LockerDetail> findByIdWithLocker(@Param("lockerDetailId")Long lockerDetailId);
}
