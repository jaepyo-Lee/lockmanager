package com.ime.lockmanager.locker.application.port.in.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ime.lockmanager.locker.domain.lockerdetail.dto.LockerDetailInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class AllLockersInMajorResponseDto {
    private String lockerName;

    @JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
    private LocalDateTime startReservationTime;
    @JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
    private LocalDateTime endReservationTime;
    private List<LockerDetailInfo> lockerDetailInfoList;
}
