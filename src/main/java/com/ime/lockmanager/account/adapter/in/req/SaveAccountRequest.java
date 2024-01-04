package com.ime.lockmanager.account.adapter.in.req;

import com.ime.lockmanager.account.application.port.in.req.SaveAccountRequestDto;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class SaveAccountRequest {
    @NotBlank
    private String bank;
    @NotBlank
    private String ownerName;
    @NotBlank
    private String accountNum;

    public SaveAccountRequestDto toRequestDto(){
        return SaveAccountRequestDto.builder()
                .accountNum(accountNum)
                .ownerName(ownerName)
                .bank(bank)
                .build();
    }
}
