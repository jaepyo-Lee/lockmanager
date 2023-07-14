package com.ime.lockmanager.auth.application.port.in.usecase;

import com.ime.lockmanager.auth.adapter.in.request.LoginRequest;
import com.ime.lockmanager.auth.application.port.in.response.LoginRequestDto;
import com.ime.lockmanager.auth.application.port.in.response.TokenResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthUseCase {
    TokenResponseDto login(LoginRequestDto loginRequestDto);

    TokenResponseDto reissue(String refreshToken);

    ResponseEntity<String> logout(String accessToken);

}
