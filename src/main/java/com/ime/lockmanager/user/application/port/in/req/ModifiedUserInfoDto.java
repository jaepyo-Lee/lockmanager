package com.ime.lockmanager.user.application.port.in.req;

import com.ime.lockmanager.user.domain.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ModifiedUserInfoDto {
    private String studentNum;
    private Boolean admin;
    private Boolean membership;
    private Long lockerDetailId;
}
