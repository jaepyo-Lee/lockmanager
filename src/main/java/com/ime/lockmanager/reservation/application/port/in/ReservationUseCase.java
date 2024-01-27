package com.ime.lockmanager.reservation.application.port.in;

import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import com.ime.lockmanager.reservation.application.port.in.req.ChangeReservationRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserCancelLockerRequestDto;

public interface ReservationUseCase {
    Long changeReservation(ChangeReservationRequestDto requestDto);

    LockerRegisterResponseDto registerForUser(LockerRegisterRequestDto lockerRegisterRequestDto) throws Exception;
    LockerRegisterResponseDto registerForAdmin(LockerRegisterRequestDto lockerRegisterRequestDto) throws Exception;


    void resetReservation(Long lockerId);

    Long cancelLockerByStudentNum(UserCancelLockerRequestDto build);

}
