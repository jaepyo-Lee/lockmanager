package com.ime.lockmanager.user.application.port.in.req;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class UserRequestDto {
    private String studentNum;
}
