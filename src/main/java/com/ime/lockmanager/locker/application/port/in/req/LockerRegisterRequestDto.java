package com.ime.lockmanager.locker.application.port.in.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LockerRegisterRequestDto {
    private String studentNum;
    private Long lockerNum;
}
