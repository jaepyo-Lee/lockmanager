package com.ime.lockmanager.account.adapter.in.req;

import com.ime.lockmanager.account.application.port.in.req.SaveOrModifyAccountRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Schema
@Getter
public class ModifyAccountRequest {
    @Schema(description = "은행명")
    @NotBlank
    private String bank;

    @Schema(description = "입급주명")
    @NotBlank
    private String ownerName;

    @Schema(description = "계좌번호")
    @NotBlank
    private String accountNum;
    public SaveOrModifyAccountRequestDto toRequestDto(){
        return SaveOrModifyAccountRequestDto.builder()
                .accountNum(accountNum)
                .ownerName(ownerName)
                .bank(bank)
                .build();
    }
}
