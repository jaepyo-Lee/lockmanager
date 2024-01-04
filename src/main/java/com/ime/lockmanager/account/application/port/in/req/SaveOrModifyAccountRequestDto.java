package com.ime.lockmanager.account.application.port.in.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveOrModifyAccountRequestDto {
    private String bank;
    private String ownerName;
    private String accountNum;
}
