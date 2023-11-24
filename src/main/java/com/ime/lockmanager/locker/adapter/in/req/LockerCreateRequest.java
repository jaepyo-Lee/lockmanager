package com.ime.lockmanager.locker.adapter.in.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class LockerCreateRequest {
    private String lockerName;
    private String totalRow;
    private String totalColumn;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startReservationTime;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endReservationTime;
    private List<LockerDetailCreateRequest> lockerDetailCreateRequests;
}
