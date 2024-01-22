package com.ime.lockmanager.reservation.application.port.in;

import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.ReservationOfLockerResponseDto;
import com.ime.lockmanager.user.application.port.in.req.UserCancelLockerRequestDto;

import java.security.Principal;

public interface ReservationUseCase {
    LockerRegisterResponseDto registerForUser(LockerRegisterRequestDto lockerRegisterRequestDto) throws Exception;
    LockerRegisterResponseDto registerForAdmin(LockerRegisterRequestDto lockerRegisterRequestDto) throws Exception;


    void resetReservation(Long lockerId);

    void cancelLockerByStudentNum(UserCancelLockerRequestDto build);

}
