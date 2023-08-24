package com.ime.lockmanager.reservation.application.port.out;

import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.reservation.adapter.out.dto.DeleteReservationByStudentNumDto;
import com.ime.lockmanager.reservation.application.port.out.dto.FindReservationByLockerNumDto;
import com.ime.lockmanager.reservation.application.port.out.dto.FindReservationByStudentNumDto;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.domain.User;

import java.util.List;

public interface ReservationQueryPort {
    boolean isReservationByStudentNum(FindReservationByStudentNumDto findReservationByStudentNumDto);
    boolean isReservationByLockerId(FindReservationByLockerNumDto findReservationByLockerNumDto);

    void registerLocker(User userJpaEntity, Locker lockerJpaEntity);

    Reservation findReservationByStudentNum(String studentNum);
    void deleteAll();

    List<Long> findReservedLockers();

    void deleteByStudentNum(DeleteReservationByStudentNumDto deleteReservationByStudentNumDto);
}
