package com.ime.lockmanager.locker.application.port.in.res;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LockerCreateResponseDto {
    private String createdLockerName;
    private Long createdLockerId;
}
