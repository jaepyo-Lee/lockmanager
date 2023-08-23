package com.ime.lockmanager.reservation.application.port.in;

import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.ReservationOfLockerResponseDto;
import com.ime.lockmanager.user.application.port.in.req.UserCancelLockerRequestDto;

import java.security.Principal;

public interface ReservationUseCase {
    LockerRegisterResponseDto register(LockerRegisterRequestDto lockerRegisterRequestDto) throws Exception;

    ReservationOfLockerResponseDto findReservedLockers();

    void resetReservation(Principal principal);

    void cancelLockerByStudentNum(UserCancelLockerRequestDto build);
}
