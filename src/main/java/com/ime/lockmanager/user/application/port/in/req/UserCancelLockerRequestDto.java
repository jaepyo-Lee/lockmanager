package com.ime.lockmanager.user.application.port.in.req;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCancelLockerRequestDto {
    private String studentNum;

    @Builder
    public UserCancelLockerRequestDto(String studentNum) {
        this.studentNum = studentNum;
    }
}
