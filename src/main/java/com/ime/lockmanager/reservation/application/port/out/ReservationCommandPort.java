package com.ime.lockmanager.reservation.application.port.out;

import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import com.ime.lockmanager.user.domain.User;

import java.util.List;

public interface ReservationCommandPort {
    void deleteById(Long id);
    void registerLocker(User userJpaEntity, LockerDetail lockerDetail);

    void deleteAll();
    void deleteAllByIds(List<Long> reservationId);

}
