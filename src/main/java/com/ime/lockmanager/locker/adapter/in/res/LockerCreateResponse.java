package com.ime.lockmanager.locker.adapter.in.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "사물함 생성시 응답DTO")
@Getter
@Builder
public class LockerCreateResponse {
    @Schema(description = "생성된 사물함 이름")
    private String createdLockerName;
}
