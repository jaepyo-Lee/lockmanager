package com.ime.lockmanager.major.application.port.in.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "소속학과 정보DTO")
@Builder
@Getter
public class MajorDetailInMajorResponseDto {
    @Schema(description = "소속학과명")
    private String majorDetailName;
    @Schema(description = "소속학과Id(=pk)")
    private Long majorDetailId;
}
