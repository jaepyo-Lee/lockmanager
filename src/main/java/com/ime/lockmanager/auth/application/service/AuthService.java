package com.ime.lockmanager.auth.application.service;

import com.ime.lockmanager.auth.application.port.in.response.LoginRequestDto;
import com.ime.lockmanager.auth.application.port.in.response.TokenResponseDto;
import com.ime.lockmanager.auth.application.port.in.usecase.AuthUseCase;
import com.ime.lockmanager.auth.application.port.out.AuthToUserQueryPort;
import com.ime.lockmanager.auth.domain.AuthUser;
import com.ime.lockmanager.common.security.JwtProvider;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService implements AuthUseCase {
    private final AuthToUserQueryPort authToUserQueryPort;
    private final JwtProvider jwtProvider;

    @Override
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        User user = authToUserQueryPort.findByStudentNum(loginRequestDto.getStudentNum())
                .orElseThrow(() -> new NullPointerException());
        AuthUser authUser = AuthUser.of(user);
        String refreshToken = "Bearer "+jwtProvider.createRefreshToken(authUser);
        String accessToken = "Bearer "+ jwtProvider.createAccessToken(authUser);

        return TokenResponseDto.of(accessToken, refreshToken);
    }
}
