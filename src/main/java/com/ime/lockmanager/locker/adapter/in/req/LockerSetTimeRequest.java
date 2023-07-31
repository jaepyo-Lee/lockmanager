package com.ime.lockmanager.locker.adapter.in.req;

import com.ime.lockmanager.locker.application.port.in.req.LockerSetTimeRequestDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LockerSetTimeRequest {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public LockerSetTimeRequestDto toRequestDto(){
        return LockerSetTimeRequestDto.builder()
                .endDateTime(endDateTime)
                .startDateTime(startDateTime)
                .build();
    }
}
