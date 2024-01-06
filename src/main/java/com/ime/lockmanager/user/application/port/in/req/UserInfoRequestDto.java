package com.ime.lockmanager.user.application.port.in.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoRequestDto {
    private Long userId;
}
