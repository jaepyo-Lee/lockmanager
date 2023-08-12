package com.ime.lockmanager.user.adapter.in.res;

import com.ime.lockmanager.user.domain.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoAdminResponse {
    private String userName;
    private String studentNum;
    private boolean membership;
    private Long lockerNum;
    private Role role;
    private String status;
}
