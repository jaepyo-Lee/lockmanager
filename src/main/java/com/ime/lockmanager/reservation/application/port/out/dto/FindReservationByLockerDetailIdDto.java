package com.ime.lockmanager.reservation.application.port.out.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindReservationByLockerDetailIdDto {
    private Long lockerDetailId;
}
