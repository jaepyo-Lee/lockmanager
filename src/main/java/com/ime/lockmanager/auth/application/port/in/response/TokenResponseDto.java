package com.ime.lockmanager.auth.application.port.in.response;

import com.ime.lockmanager.auth.adapter.in.request.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;

    public static TokenResponseDto of(String accessToken,String refreshToken){
        return new TokenResponseDto().builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
