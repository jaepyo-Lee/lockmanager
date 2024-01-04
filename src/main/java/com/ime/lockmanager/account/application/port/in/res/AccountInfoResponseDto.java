package com.ime.lockmanager.account.application.port.in.res;

import com.ime.lockmanager.account.adapter.in.res.AccountInfoResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountInfoResponseDto {
    private String bank;
    private String ownerName;
    private String accountNum;
    public AccountInfoResponse toResponse(){
        return AccountInfoResponse.builder()
                .accountNum(accountNum)
                .ownerName(ownerName)
                .bank(bank)
                .build();
    }
}
