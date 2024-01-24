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
    @Query("select R from RESERVATION_TABLE AS R join fetch R.lockerDetail " +
            "where R.user.id=:userId and R.lockerDetail.id=:lockerDetailId")
    Optional<Reservation> findAllByUserIdAndLockerDetailId(@Param(value = "userId") Long userId,
                                                           @Param(value = "lockerDetailId") Long lockerDetailId);

    Optional<Reservation> findByUserStudentNumAndLockerDetailId(String studentNum, Long LockerDetailId);

    Optional<Reservation> findByUserStudentNum(String studentNum);

    Optional<Reservation> findByUserId(Long userId);

    void deleteByUserStudentNum(String studentNum);

    Optional<Reservation> findByLockerDetailId(Long lockerDetailId);

    @Query("select R from RESERVATION_TABLE as R where R.lockerDetail in (:lockerDetailsByLocker)")
    List<Reservation> findAllByLockerDetails(List<LockerDetail> lockerDetailsByLocker);
}
