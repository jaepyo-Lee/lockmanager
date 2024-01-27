package com.ime.lockmanager.reservation.application.port.in;

import com.ime.lockmanager.locker.application.port.out.LockerDetailQueryPort;
import com.ime.lockmanager.reservation.application.port.out.ReservationQueryPort;
import com.ime.lockmanager.reservation.application.service.ReservationService;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class ReservationUseCaseTest {
    private final ReservationQueryPort reservationQueryPort = Mockito.mock(ReservationQueryPort.class);
    private final LockerDetailQueryPort lockerDetailQueryPort = Mockito.mock(LockerDetailQueryPort.class);
    private final UserQueryPort userQueryPort = Mockito.mock(UserQueryPort.class);
    private final ReservationUseCase reservationUseCase =
            new ReservationService(userQueryPort, reservationQueryPort, lockerDetailQueryPort);
    @Test
    void changeReservation() {

        // 해당 사물함 락걸기
        // 해당 사물함 예약 가능한지 확인
        // 다른 사물함 취소
        // 해당 사물함 예약
    }
}