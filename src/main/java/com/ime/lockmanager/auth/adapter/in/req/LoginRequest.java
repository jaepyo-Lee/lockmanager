package com.ime.lockmanager.auth.adapter.in.req;

import com.ime.lockmanager.auth.application.port.in.response.LoginRequestDto;
import com.sun.istack.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class LoginRequest {
    @NotNull
    private String studentNum;

    @NotNull
    private String password;

    public LoginRequestDto toRequestDto(){
        return LoginRequestDto.builder()
                .studentNum(studentNum)
                .password(password)
                .build();
    }

}
