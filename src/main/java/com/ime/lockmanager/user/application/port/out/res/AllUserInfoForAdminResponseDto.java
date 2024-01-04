package com.ime.lockmanager.user.application.port.out.res;

import com.ime.lockmanager.user.adapter.in.res.UserInfoAdminResponse;
import com.ime.lockmanager.user.domain.MembershipState;
import com.ime.lockmanager.user.domain.Role;
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
    private MembershipState membershipState;
    private String status;
    private String studentNum;
    private String lockerName;
    private String lockerNum;
    private Role role;

    public AllUserInfoForAdminResponseDto(String name,
                                          MembershipState membershipState,
                                          String status,
                                          String studentNum,
                                          Role role,
                                          String lockerName,
                                          String lockerNum) {
        this.name = name;
        this.membershipState = membershipState;
        this.status = status;
        this.studentNum = studentNum;
        this.role = role;
        this.lockerNum = lockerNum;
        this.lockerName = lockerName;
    }

    public UserInfoAdminResponse toResponse() {
        return UserInfoAdminResponse.builder()
                .lockerName(lockerName)
                .lockerNum(lockerNum)
                .membershipState(membershipState)
                .studentNum(studentNum)
                .userName(name)
                .role(role)
                .status(status)
                .build();
    }
}
