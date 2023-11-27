package com.ime.lockmanager.auth.application.port.in.usecase;

import com.ime.lockmanager.auth.application.port.in.req.LoginRequestDto;
import com.ime.lockmanager.auth.application.port.in.res.LoginTokenResponseDto;
import com.ime.lockmanager.auth.application.port.in.res.TokenResponseDto;

public interface AuthUseCase {
    LoginTokenResponseDto login(LoginRequestDto loginRequestDto);

    TokenResponseDto reissue(String refreshToken);

    void logout(String accessToken);

}
