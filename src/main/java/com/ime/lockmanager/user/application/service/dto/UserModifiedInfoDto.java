package com.ime.lockmanager.user.application.service.dto;

import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoRequestDto;
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

    public static UserModifiedInfoDto fromModifiedUserInfoRequestDto(ModifiedUserInfoRequestDto requestDto){
        return UserModifiedInfoDto.builder()
                .role(requestDto.getRole())
                .membership(requestDto.isMembership())
                .build();
    }
}
