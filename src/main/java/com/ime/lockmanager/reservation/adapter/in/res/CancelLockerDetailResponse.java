package com.ime.lockmanager.reservation.adapter.in.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "사물함 예약 취소시 응답 DTO")
@Getter
@Builder
public class CancelLockerDetailResponse {
    @Schema(description = "취소된 사물함 번호")
    private Long canceledLockerDetailNum;
    @Schema(description = "사물함 취소한 학생의 학번")
    private String studentNum;
}
