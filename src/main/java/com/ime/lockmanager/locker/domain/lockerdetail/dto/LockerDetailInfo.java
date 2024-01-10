package com.ime.lockmanager.locker.domain.lockerdetail.dto;

import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetailStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
@Schema(description = "사물함 각 칸의 정보")
@Getter
@Builder
public class LockerDetailInfo {
    @Schema(description = "해당 칸의 pk값")
    private Long id;
    @Schema(description = "해당 칸의 행번호")
    private String row_num;
    @Schema(description = "해당 칸의 열번호")
    private String column_num;
    @Schema(description = "해당 칸의 번호")
    private String locker_num;
    @Schema(description = "해당 칸의 현재 상태",allowableValues = {"RESERVED","NON_RESERVED","BROKEN"})
    private LockerDetailStatus status;
}
