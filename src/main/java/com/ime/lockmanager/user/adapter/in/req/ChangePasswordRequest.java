package com.ime.lockmanager.user.adapter.in.req;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    private String newPassword;
    private String currentPassword;
}