package com.ime.lockmanager.locker.domain.locker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ime.lockmanager.major.domain.Major;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class LockerCreateDto {
    private Major major;
    private String lockerName;
    private String totalRow;
    private String totalColumn;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startReservationTime;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endReservationTime;
}
