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
    private String id;
    @NotNull
    private String pw;

    public LoginRequestDto toRequestDto(){
        return LoginRequestDto.builder()
                .id(id)
                .pw(pw)
                .build();
    }

}
