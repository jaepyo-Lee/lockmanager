package com.ime.lockmanager.reservation.adapter.in.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChangeReservationResponse {
    private Long changeLockerId;
}
