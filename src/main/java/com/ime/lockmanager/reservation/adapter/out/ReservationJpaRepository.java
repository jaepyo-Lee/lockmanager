package com.ime.lockmanager.reservation.adapter.out;

import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import com.ime.lockmanager.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    Optional<Reservation> findByUserId(Long userId);

    Optional<Reservation> findByLockerDetailId(Long lockerDetailId);

    @Query("select R from RESERVATION_TABLE as R where R.lockerDetail in (:lockerDetailsByLocker)")
    List<Reservation> findAllByLockerDetails(@Param(value = "lockerDetailsByLocker") List<LockerDetail> lockerDetailsByLocker);

    @Modifying
    @Query("delete from RESERVATION_TABLE as R where R.id in (:reservationIds)")
    void deleteAllByIds(@Param(value = "reservationIds") List<Long> reservationId);
}
