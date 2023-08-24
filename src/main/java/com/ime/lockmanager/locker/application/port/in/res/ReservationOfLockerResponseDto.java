package com.ime.lockmanager.locker.application.port.in.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReservationOfLockerResponseDto {
    private List<Long> lockerIdList;
}
