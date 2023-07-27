package com.ime.lockmanager.user.application.port.in.req;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChangePasswordRequestDto {
    private String studentNum;
    private String newPassword;
    private String currentPassword;
    @Builder
    public ChangePasswordRequestDto(String studentNum, String newPassword,String currentPassword) {
        this.studentNum = studentNum;
        this.newPassword = newPassword;
        this.currentPassword = currentPassword;
    }
}
