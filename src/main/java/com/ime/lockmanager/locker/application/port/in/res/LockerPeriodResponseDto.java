package com.ime.lockmanager.locker.application.port.in.res;

import lombok.Builder;
import lombok.Getter;
import reactor.util.annotation.Nullable;

import java.time.LocalDateTime;

@Getter
public class LockerPeriodResponseDto {
    private LocalDateTime startDateTime=null;

    private LocalDateTime endDateTime=null;

    @Builder
    public LockerPeriodResponseDto(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
