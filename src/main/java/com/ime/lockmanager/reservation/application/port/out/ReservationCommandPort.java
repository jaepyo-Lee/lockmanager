package com.ime.lockmanager.reservation.application.port.out;

import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import com.ime.lockmanager.user.domain.User;

public interface ReservationCommandPort {
    void deleteById(Long id);
    Long registerLocker(User userJpaEntity, LockerDetail lockerDetail);

    void deleteAll();
}
