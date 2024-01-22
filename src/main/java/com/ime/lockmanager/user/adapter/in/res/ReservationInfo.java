package com.ime.lockmanager.user.adapter.in.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "예약된 사물함 정보")
@Getter
@Builder
public class ReservationInfo {
    @Schema(description = "학생이 예약한 사물함 번호")
    private String lockerNum;
    @Schema(description = "사물함 이름")
    private String lockerName;
}
