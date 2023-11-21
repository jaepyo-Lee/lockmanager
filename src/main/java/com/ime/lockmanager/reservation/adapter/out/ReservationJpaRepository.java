package com.ime.lockmanager.reservation.adapter.out;

import com.ime.lockmanager.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationJpaRepository extends JpaRepository<Reservation,Long> {

    Reservation findByUserStudentNum(String studentNum);

    Reservation findByLockerId(Long lockerId);

    @Query("select R.locker.id from RESERVATION_TABLE as R")
    List<Long> findAllIds();

    void deleteByUserStudentNum(String studentNum);
}
