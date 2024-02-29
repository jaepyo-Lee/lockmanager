package com.ime.lockmanager.user.application.port.in.req;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DetermineApplyingRequestDto {
    private String studentNum;
}
