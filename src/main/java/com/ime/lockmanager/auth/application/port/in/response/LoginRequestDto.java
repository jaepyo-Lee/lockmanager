package com.ime.lockmanager.auth.application.port.in.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    private String studentNum;
    private String password;
}
