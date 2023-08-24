package com.ime.lockmanager.auth.adapter.in;

import com.ime.lockmanager.auth.adapter.in.req.LoginRequest;
import com.ime.lockmanager.auth.application.port.in.usecase.AuthUseCase;
import com.ime.lockmanager.common.format.success.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
class AuthController {
    private final AuthUseCase authUseCase;

    @ApiOperation(
            value = "로그인 기능",
            notes = "세종대학교 학사정보시스템의 아이디와 비밀번호를 받아 로그인 하는 API"
    )
    @PostMapping ("/login")
    public SuccessResponse login(@ModelAttribute LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
        return new SuccessResponse(authUseCase.login(loginRequest.toRequestDto()));
    }

    @ApiOperation(
            value = "토큰 재발급 기능",
            notes = "access token 만료시 refresh token을 이용하여 access token을 재발급받는 API"
    )
    @PostMapping("/reissue")
    public SuccessResponse reissue(@RequestHeader(value = "RefreshToken") String refreshToken){
        return new SuccessResponse(authUseCase.reissue(refreshToken));
    }

    @ApiOperation(
            value = "로그아웃 기능",
            notes = "로그아웃 API"
    )
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader(value = "AccessToken") String accessToken){
        return authUseCase.logout(accessToken);
    }

}
