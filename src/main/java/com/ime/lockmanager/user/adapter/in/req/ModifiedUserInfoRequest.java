package com.ime.lockmanager.user.adapter.in.req;

import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoRequestDto;
import com.ime.lockmanager.user.domain.Role;
import lombok.Getter;

@Getter
public class ModifiedUserInfoRequest {
    private String studentNum;
    private Role role;
    private boolean membership;
    private String lockerNumber;
    public ModifiedUserInfoRequestDto toRequestDto(){
        return ModifiedUserInfoRequestDto.builder()
                .lockerNumber(lockerNumber)
                .membership(membership)
                .studentNum(studentNum)
                .role(role)
                .build();
    }
}
