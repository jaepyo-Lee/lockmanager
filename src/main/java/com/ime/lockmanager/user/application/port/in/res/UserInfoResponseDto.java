package com.ime.lockmanager.user.application.port.in.res;

import com.ime.lockmanager.user.adapter.in.res.UserInfoAdminResponse;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.UserTier;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponseDto {
    private String userName;
    private String studentNum;
    private UserTier userTier;
    private String lockerNum;

    private Role role;
    private String status;
    private String majorDetail;

    public UserInfoAdminResponse toResponse(){
        return UserInfoAdminResponse.builder()
                .lockerNum(lockerNum)
                .userTier(userTier)
                .userName(userName)
                .role(role)
                .status(status)
                .studentNum(studentNum)
                .build();
    }
}
