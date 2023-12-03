package com.ime.lockmanager.user.adapter.in.req;

import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoDto;
import com.ime.lockmanager.user.domain.Role;
import lombok.Getter;

@Getter
public class ModifiedUserInfo {
    private String studentNum;
    private Role role;
    private boolean membership;
    private Long lockerDetailId;

    public ModifiedUserInfoDto toDto(){
        return ModifiedUserInfoDto.builder()
                .lockerDetailId(lockerDetailId)
                .membership(membership)
                .role(role)
                .studentNum(studentNum)
                .build();
    }
}
