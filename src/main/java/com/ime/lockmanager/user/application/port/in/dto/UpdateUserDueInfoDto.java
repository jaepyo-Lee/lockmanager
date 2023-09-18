package com.ime.lockmanager.user.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateUserDueInfoDto {
    private String studentNum;
    private boolean isDue;
    private String name;
}
