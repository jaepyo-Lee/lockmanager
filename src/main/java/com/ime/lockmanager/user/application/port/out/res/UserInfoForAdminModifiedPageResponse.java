package com.ime.lockmanager.user.application.port.out.res;

import com.ime.lockmanager.user.domain.Role;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserInfoForAdminModifiedPageResponse {
    private String userName;
    private boolean membership;
    private String status;
    private String studentNum;
    private Long lockerNum;
    private Role role;
}
