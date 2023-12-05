package com.ime.lockmanager.reservation.adapter.out;

import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import com.ime.lockmanager.reservation.adapter.out.dto.DeleteReservationByStudentNumDto;
import com.ime.lockmanager.reservation.application.port.out.ReservationQueryPort;
import com.ime.lockmanager.reservation.application.port.out.dto.FindReservationByLockerDetailIdDto;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationQueryRepository implements ReservationQueryPort {
    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public List<Reservation> findAllByStudentNum(String studentNum) {
        return reservationJpaRepository.findAllByUserStudentNum(studentNum);
    }

    @Override
    public List<Reservation> findAllByLockerDetailId(Long lockerDetailId) {
        return reservationJpaRepository.findAllByLockerDetailId(lockerDetailId);
    }

    @Override
    public List<Reservation> findAllByLockerDetails(List<LockerDetail> lockerDetailsByLocker) {
        return reservationJpaRepository.findAllByLockerDetails(lockerDetailsByLocker);
    }

    @Override
    public Optional<Reservation> findByStudentNumAndLockerDetailId(String studentNum, Long lockerDetailId) {
        return reservationJpaRepository.findByUserStudentNumAndLockerDetailId(studentNum, lockerDetailId);
    }

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
    public Optional<Reservation> findByLockerDetailId(Long lockerDetailId) {
        return reservationJpaRepository.findByLockerDetailId(lockerDetailId);
    }
}
