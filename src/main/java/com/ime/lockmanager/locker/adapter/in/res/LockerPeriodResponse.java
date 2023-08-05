package com.ime.lockmanager.locker.adapter.in.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ime.lockmanager.locker.application.port.in.res.LockerPeriodResponseDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Builder
public class LockerPeriodResponse {
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDateTime;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDateTime;

    public static LockerPeriodResponse fromResponse(LockerPeriodResponseDto responseDto){
        return LockerPeriodResponse.builder()
                .startDateTime(responseDto.getStartDateTime())
                .endDateTime(responseDto.getEndDateTime())
                .build();
    }
}
