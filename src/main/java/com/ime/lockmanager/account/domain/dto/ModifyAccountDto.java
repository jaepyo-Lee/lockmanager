package com.ime.lockmanager.account.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ModifyAccountDto {
    private String bank;
    private String ownerName;
    private String accountNum;
}
