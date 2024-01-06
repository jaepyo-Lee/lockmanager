package com.ime.lockmanager.user.application.port.in.req;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ModifiedUserInfoRequestDto {
    private List<ModifiedUserInfoDto> modifiedUserInfoList;

}
