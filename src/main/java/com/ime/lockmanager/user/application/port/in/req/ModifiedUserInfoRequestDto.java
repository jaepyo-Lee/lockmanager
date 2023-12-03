package com.ime.lockmanager.user.application.port.in.req;

import com.ime.lockmanager.user.adapter.in.req.ModifiedUserInfo;
import com.ime.lockmanager.user.domain.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ModifiedUserInfoRequestDto {
    private List<ModifiedUserInfoDto> modifiedUserInfoList;

}
