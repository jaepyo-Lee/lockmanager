package com.ime.lockmanager.user.application.service.dto;

import com.ime.lockmanager.user.adapter.in.req.ModifiedUserInfo;
import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoDto;
import com.ime.lockmanager.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UserModifiedInfoDto {
    private Role role;
    private boolean membership;

    public static UserModifiedInfoDto fromModifiedUserInfoDto(ModifiedUserInfoDto userInfoDto){
        return UserModifiedInfoDto.builder()
                .role(userInfoDto.getRole())
                .membership(userInfoDto.isMembership())
                .build();
    }
}