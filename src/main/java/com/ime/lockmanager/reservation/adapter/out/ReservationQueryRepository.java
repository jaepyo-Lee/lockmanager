package com.ime.lockmanager.reservation.adapter.out;

import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.reservation.adapter.out.dto.DeleteReservationByStudentNumDto;
import com.ime.lockmanager.reservation.application.port.out.ReservationQueryPort;
import com.ime.lockmanager.reservation.application.port.out.dto.FindReservationByLockerNumDto;
import com.ime.lockmanager.reservation.application.port.out.dto.FindReservationByStudentNumDto;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ReservationQueryRepository implements ReservationQueryPort {
    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public void deleteByStudentNum(DeleteReservationByStudentNumDto deleteReservationByStudentNumDto) {
        reservationJpaRepository.deleteByUserStudentNum(deleteReservationByStudentNumDto.getStudentNum());
    }

    @Override
    public Reservation findReservationByStudentNum(String studentNum) {
        return reservationJpaRepository.findByUserStudentNum(studentNum);
    }

    @Override
    public List<Long> findReservedLockers() {
        return reservationJpaRepository.findAllIds();
    }

    @Override
    public boolean isReservationByStudentNum(FindReservationByStudentNumDto findReservationByStudentNumDto) {
        Reservation reservationByStudentNum = reservationJpaRepository.findByUserStudentNum(findReservationByStudentNumDto.getStudentNum());
        if(reservationByStudentNum==null){ //예약이 안되어있음
            return false;
        }
        return true;
    }

    @Override
    public boolean isReservationByLockerId(FindReservationByLockerNumDto findReservationByLockerNumDto) {
        Reservation reservationByLockerId = reservationJpaRepository.findByLockerId(findReservationByLockerNumDto.getLockerNum());
        if(reservationByLockerId==null){ //예약이 안되어있음
            return false;
        }
        return true;
    }

    @Override
    public void registerLocker(User userJpaEntity, Locker lockerJpaEntity) {
        reservationJpaRepository.save(
                Reservation.builder()
                        .locker(lockerJpaEntity)
                        .user(userJpaEntity)
                        .build()
        );
    }

    @Override
    public void deleteAll() {
        reservationJpaRepository.deleteAll();
    }
}
