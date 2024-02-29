package com.ime.lockmanager.reservation.application.port.in.req;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChangeReservationRequestDto {
    private Long newLockerDetailId;
    private Long originLockerDetailId;
    private Long userId;
    private Long majorId;

    public static ChangeReservationRequestDto of(Long newLockerDetailId, Long originLockerDetailId, Long userId, Long majorId) {
        return ChangeReservationRequestDto.builder()
                .majorId(majorId)
                .newLockerDetailId(newLockerDetailId)
                .originLockerDetailId(originLockerDetailId)
                .userId(userId)
                .build();
    }

}
