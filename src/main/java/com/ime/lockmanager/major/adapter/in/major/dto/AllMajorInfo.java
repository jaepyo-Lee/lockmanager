package com.ime.lockmanager.major.adapter.in.major.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema
@Builder
public class AllMajorInfo {
    @Schema(description = "학과명")
    private String majorName;
    @Schema(description = "학과id(=pk)")
    private Long majorId;
}
