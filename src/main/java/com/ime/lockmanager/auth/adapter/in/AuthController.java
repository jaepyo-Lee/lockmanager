package com.ime.lockmanager.auth.adapter.in;

import com.ime.lockmanager.auth.adapter.in.request.LoginRequest;
import com.ime.lockmanager.auth.application.port.in.response.TokenResponseDto;
import com.ime.lockmanager.auth.application.port.in.usecase.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthUseCase authUseCase;

    @PostMapping ("/login")
    public TokenResponseDto login(@RequestBody LoginRequest loginRequest){
        return authUseCase.login(loginRequest.toRequestDto());
    }

}
