package com.ime.lockmanager.reservation.adapter.out;

import com.ime.lockmanager.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<Reservation,Long> {

    Optional<Reservation> findByUserStudentNum(String studentNum);

    Reservation findByLockerId(Long lockerId);

    @Query("select R.locker.id from RESERVATION_TABLE as R")
    List<Long> findAllIds();

    void deleteByUserStudentNum(String studentNum);

    Optional<Reservation> findByLockerDetailId(Long lockerDetailId);
}
