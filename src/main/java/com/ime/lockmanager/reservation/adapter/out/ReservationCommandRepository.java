package com.ime.lockmanager.reservation.adapter.out;

import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import com.ime.lockmanager.reservation.application.port.out.ReservationCommandPort;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ReservationCommandRepository implements ReservationCommandPort {
    private final ReservationJpaRepository reservationJpaRepository;
    @Override
    public void deleteAllByIds(List<Long> reservationId) {
        reservationJpaRepository.deleteAllByIds(reservationId);
    }
    @Override
    public void registerLocker(User userJpaEntity, LockerDetail lockerDetail) {
        reservationJpaRepository.save(
                Reservation.builder()
                        .lockerDetail(lockerDetail)
                        .user(userJpaEntity)
                        .build()
        );
    }

    @Override
    public void deleteAll() {
        reservationJpaRepository.deleteAll();
    }
    @Override
    public void deleteById(Long id) {
        reservationJpaRepository.deleteById(id);
    }
}
