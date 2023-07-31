package com.ime.lockmanager.locker.adapter.in.res;

import com.ime.lockmanager.locker.application.port.in.res.LockerPeriodResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LockerPeriodResponse {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public static LockerPeriodResponse fromResponse(LockerPeriodResponseDto responseDto){
        return LockerPeriodResponse.builder()
                .startDateTime(responseDto.getStartDateTime())
                .endDateTime(responseDto.getEndDateTime())
                .build();
    }
}
