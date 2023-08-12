package com.ime.lockmanager.locker.adapter.in.res;

import com.ime.lockmanager.locker.application.port.in.res.LockerReserveResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LockerReserveResponse {
    List<Long> reservedLockerId;
    public static LockerReserveResponse fromResponse(LockerReserveResponseDto reserveLocker) {
        return LockerReserveResponse.builder()
                .reservedLockerId(reserveLocker.getLockerId())
                .build();
    }
}
