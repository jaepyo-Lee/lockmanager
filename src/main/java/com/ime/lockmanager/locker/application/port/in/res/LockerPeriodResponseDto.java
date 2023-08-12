package com.ime.lockmanager.locker.application.port.in.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import reactor.util.annotation.Nullable;

import java.time.LocalDateTime;

@Getter
public class LockerPeriodResponseDto {
    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Builder
    public LockerPeriodResponseDto(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
