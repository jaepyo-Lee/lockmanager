package com.ime.lockmanager.locker.adapter.in.res;

import com.ime.lockmanager.locker.domain.lockerdetail.dto.LockerDetailInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "학과내의 모든 사물함 조회DTO")
@Getter
@Builder
public class AllLockersInMajorResponse {
    @Schema(description = "사물함 이름")
    private String lockerName;
    @Schema(description = "예약 시작시간")
    private LocalDateTime startReservationTime;
    @Schema(description = "예약 마감시간")
    private LocalDateTime endReservationTime;
    @Schema(description = "사물함의 각 칸의 정보리스트")
    private List<LockerDetailInfo> lockerDetailInfoList;
}
