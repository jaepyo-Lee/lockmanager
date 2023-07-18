package com.ime.lockmanager.user.application.port.in.req;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChangePasswordRequestDto {
    private String studentNum;
    private String newPassword;

    @Builder
    public ChangePasswordRequestDto(String studentNum, String newPassword) {
        this.studentNum = studentNum;
        this.newPassword = newPassword;
    }
}
