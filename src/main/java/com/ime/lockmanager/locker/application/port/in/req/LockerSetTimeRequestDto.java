package com.ime.lockmanager.locker.application.port.in.req;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class LockerSetTimeRequestDto {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
