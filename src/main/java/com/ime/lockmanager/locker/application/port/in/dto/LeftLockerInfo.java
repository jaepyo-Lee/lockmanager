package com.ime.lockmanager.locker.application.port.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@Schema(description = "남은 사물함 정보 응답DTO")
public class LeftLockerInfo {
    @Schema(description = "남은 사물함 번호 리스트")
    private List<String> leftLockerNum;
    @Schema(description = "남은 사물함명")
    private String leftLockerName;
}
