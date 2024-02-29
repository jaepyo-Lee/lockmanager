package com.ime.lockmanager.reservation.application.port.out;

import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import com.ime.lockmanager.reservation.adapter.out.dto.DeleteReservationByStudentNumDto;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface ReservationQueryPort {

    Optional<Reservation> findByLockerDetailId(Long lockerDetailId);

    Optional<Reservation> findAllByUserIdAndLockerDetailId(Long studentNum, Long lockerDetailId);

    List<Reservation> findAllByLockerDetails(List<LockerDetail> lockerDetailsByLocker);

    Optional<Reservation> findByUserId(Long userId);

}
