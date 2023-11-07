package com.ime.lockmanager.auth.application.port.in.usecase;

import com.ime.lockmanager.auth.application.port.in.response.LoginRequestDto;
import com.ime.lockmanager.auth.application.port.in.response.TokenResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface AuthUseCase {
    TokenResponseDto login(LoginRequestDto loginRequestDto);

    TokenResponseDto reissue(String refreshToken);

    void logout(String accessToken);

}
