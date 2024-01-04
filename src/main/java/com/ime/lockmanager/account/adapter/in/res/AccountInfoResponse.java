package com.ime.lockmanager.account.adapter.in.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "계좌 조회 응답 DTO")
@Builder
@Getter
public class AccountInfoResponse {
    @Schema(description = "은행명")
    private String bank;

    @Schema(description = "입급주명")
    private String ownerName;

    @Schema(description = "계좌번호")
    private String accountNum;
}
