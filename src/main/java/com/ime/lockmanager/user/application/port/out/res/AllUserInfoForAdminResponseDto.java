package com.ime.lockmanager.user.application.port.out.res;

import com.ime.lockmanager.user.adapter.in.res.UserInfoAdminResponse;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.UserTier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllUserInfoForAdminResponseDto {
    private String name;
    private UserTier userTier;
    private String status;
    private String studentNum;
    private Role role;
    private Long userId;

    private String lockerName;
    private String lockerNum;

    public AllUserInfoForAdminResponseDto(String name,
                                          UserTier userTier,
                                          String status,
                                          String studentNum,
                                          Role role,
                                          String lockerName,
                                          String lockerNum) {
        this.name = name;
        this.userTier = userTier;
        this.status = status;
        this.studentNum = studentNum;
        this.role = role;
        this.lockerNum = lockerNum;
        this.lockerName = lockerName;
    }

    public UserInfoAdminResponse toResponse() {
        return UserInfoAdminResponse.builder()
                .userId(userId)
                .lockerName(lockerName)
                .lockerNum(lockerNum)
                .userTier(userTier)
                .studentNum(studentNum)
                .studentName(name)
                .role(role)
                .status(status)
                .build();
    }
}
