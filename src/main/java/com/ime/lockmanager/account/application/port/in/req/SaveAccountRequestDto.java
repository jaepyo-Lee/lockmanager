package com.ime.lockmanager.account.application.port.in.req;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class SaveAccountRequestDto {
    private String bank;
    private String ownerName;
    private String accountNum;
}
