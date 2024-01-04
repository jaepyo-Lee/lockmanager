package com.ime.lockmanager.account.application.port.in.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "저장 및 수정 응답 DTO")
@Getter
@Builder
public class SaveOrModifyAccountResponseDto {
    @Schema(description = "은행명")
    private String bank;

    @Schema(description = "입급주명")
    private String ownerName;

    @Schema(description = "계좌번호")
    private String accountNum;
}
