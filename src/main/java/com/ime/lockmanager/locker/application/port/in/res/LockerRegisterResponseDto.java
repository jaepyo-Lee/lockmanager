package com.ime.lockmanager.locker.application.port.in.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LockerRegisterResponseDto {
    private Long lockerNum;
    private String studentName;
}
