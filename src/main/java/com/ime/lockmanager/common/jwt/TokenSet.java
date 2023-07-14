package com.ime.lockmanager.common.jwt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TokenSet {
    private String accessToken;
    private String refreshToken;

    public static TokenSet of(String accessToken,String refreshToken){
        return TokenSet.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
