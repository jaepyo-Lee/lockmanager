package com.ime.lockmanager.locker.adapter.in.res;

import com.ime.lockmanager.locker.application.port.in.res.ReservationOfLockerResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LockerReserveResponse {
    List<Long> reservedLockerId;
    public static LockerReserveResponse fromResponse(ReservationOfLockerResponseDto reserveLocker) {
        return LockerReserveResponse.builder()
                .reservedLockerId(reserveLocker.getLockerIdList())
                .build();
    }
}
