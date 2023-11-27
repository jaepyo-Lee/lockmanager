package com.ime.lockmanager.locker.adapter.in.res;

import com.ime.lockmanager.locker.domain.lockerdetail.dto.LockerDetailInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class AllLockersInMajorResponse {
    private String lockerName;
    private LocalDateTime startReservationTime;
    private LocalDateTime endReservationTime;
    private List<LockerDetailInfo> lockerDetailInfoList;
}
