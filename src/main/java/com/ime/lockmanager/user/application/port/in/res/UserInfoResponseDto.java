package com.ime.lockmanager.user.application.port.in.res;

import com.ime.lockmanager.user.adapter.in.res.UserInfoAdminResponse;
import com.ime.lockmanager.user.domain.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponseDto {
    private String userName;
    private String studentNum;
    private boolean membership;
    private Long lockerNum;

    private Role role;
    private String status;

    public UserInfoAdminResponse toResponse(){
        return UserInfoAdminResponse.builder()
                .lockerNum(lockerNum)
                .membership(membership)
                .userName(userName)
                .role(role)
                .status(status)
                .studentNum(studentNum)
                .build();
    }
}
