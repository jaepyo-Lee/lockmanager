package com.ime.lockmanager.auth.application.port.in.res;

import com.ime.lockmanager.auth.adapter.in.res.LoginTokenResponse;
import com.ime.lockmanager.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginTokenResponseDto {
    private String accessToken;
    private String refreshToken;
    private Role role;
    private Long userId;
    private Long majorId;
    public static LoginTokenResponseDto of(String accessToken,String refreshToken,Role role,Long userId,Long majorId){
        return new LoginTokenResponseDto().builder()
                .majorId(majorId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(role)
                .userId(userId)
                .build();
    }

    public LoginTokenResponse toResponse(){
        return LoginTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(role)
                .userId(userId)
                .majorId(majorId)
                .build();
    }
}
