package com.ime.lockmanager.reservation.adapter.out;

import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import com.ime.lockmanager.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findAllByUserIdAndLockerDetailId(Long studentNum, Long LockerDetailId);

    Optional<Reservation> findByUserStudentNumAndLockerDetailId(String studentNum, Long LockerDetailId);

    Optional<Reservation> findByUserStudentNum(String studentNum);

    Optional<Reservation> findByUserId(Long userId);

    void deleteByUserStudentNum(String studentNum);

    Optional<Reservation> findByLockerDetailId(Long lockerDetailId);

    @Query("select R from RESERVATION_TABLE as R where R.lockerDetail in (:lockerDetailsByLocker)")
    List<Reservation> findAllByLockerDetails(List<LockerDetail> lockerDetailsByLocker);
}
