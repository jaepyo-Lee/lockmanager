package com.ime.lockmanager.auth.adapter.in;

import antlr.Token;
import com.ime.lockmanager.auth.adapter.in.request.LoginRequest;
import com.ime.lockmanager.auth.application.port.in.response.TokenResponseDto;
import com.ime.lockmanager.auth.application.port.in.usecase.AuthUseCase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthUseCase authUseCase;

    @ApiOperation(
            value = "로그인 api",
            notes = "아이디와 비밀번호를 받아 로그인 하는 api"
    )
    @PostMapping ("/login")
    public TokenResponseDto login(@RequestBody LoginRequest loginRequest){
        return authUseCase.login(loginRequest.toRequestDto());
    }

    @PostMapping("/reissue")
    public TokenResponseDto reissue(@RequestHeader(value = "RefreshToken") String refreshToken){
        return authUseCase.reissue(refreshToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<String>logout(@RequestHeader(value = "AccessToken") String accessToken){
        return authUseCase.logout(accessToken);
    }

}
