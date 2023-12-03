package com.ime.lockmanager.user.adapter.in.req;

import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoRequestDto;
import com.ime.lockmanager.user.domain.Role;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ModifiedUserInfoRequest {
    private List<ModifiedUserInfo> modifiedUserInfoList;

    public ModifiedUserInfoRequestDto toRequestDto() {
        return ModifiedUserInfoRequestDto.builder()
                .modifiedUserInfoList(modifiedUserInfoList.stream()
                        .map(ModifiedUserInfo::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
