package com.ime.lockmanager.auth.adapter.in.request;

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
    private String name;

    @NotNull
    private String studentNum;

    public LoginRequestDto toRequestDto(){
        return LoginRequestDto.builder()
                .name(name)
                .studentNum(studentNum)
                .build();
    }

}
