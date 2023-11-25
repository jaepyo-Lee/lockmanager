package com.ime.lockmanager.reservation.adapter.out;

import com.ime.lockmanager.locker.domain.LockerDetail;
import com.ime.lockmanager.reservation.adapter.out.dto.DeleteReservationByStudentNumDto;
import com.ime.lockmanager.reservation.application.port.out.ReservationQueryPort;
import com.ime.lockmanager.reservation.application.port.out.dto.FindReservationByLockerDetailIdDto;
import com.ime.lockmanager.reservation.application.port.out.dto.FindReservationByLockerNumDto;
import com.ime.lockmanager.reservation.application.port.out.dto.FindReservationByStudentNumDto;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ReservationQueryRepository implements ReservationQueryPort {
    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public void deleteByStudentNum(DeleteReservationByStudentNumDto deleteReservationByStudentNumDto) {
        reservationJpaRepository.deleteByUserStudentNum(deleteReservationByStudentNumDto.getStudentNum());
    }

    @Override
    public Optional<Reservation> findReservationByStudentNum(String studentNum) {
        return reservationJpaRepository.findByUserStudentNum(studentNum);
    }

    @Override
    public List<Long> findReservedLockers() {
        return reservationJpaRepository.findAllIds();
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
    public Optional<Reservation> findByLockerDetailId(FindReservationByLockerDetailIdDto findReservationByLockerDetailIdDto) {
        return reservationJpaRepository.findByLockerDetailId(findReservationByLockerDetailIdDto.getLockerDetailId());
    }
}
