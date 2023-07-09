package com.ime.lockmanager.user.application.port.in.res;

import lombok.Builder;
import lombok.Getter;
import reactor.util.annotation.Nullable;

@Getter
@Builder
public class UserInfoResponseDto {
    private String userName;
    private String userNum;
    private boolean membership;
    @Builder.Default
    private Long lockerNum=null;
}
