package com.ime.lockmanager.auth.adapter.in;

import com.ime.lockmanager.auth.adapter.in.req.LoginRequest;
import com.ime.lockmanager.auth.adapter.in.res.LoginTokenResponse;
import com.ime.lockmanager.auth.application.port.in.res.ReissueTokenResponseDto;
import com.ime.lockmanager.auth.application.port.in.usecase.AuthUseCase;
import com.ime.lockmanager.common.format.success.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

import static com.ime.lockmanager.common.format.success.SuccessResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
class AuthController {
    private final AuthUseCase authUseCase;

    @ApiOperation(
            value = "로그인 기능",
            notes = "세종대학교 학사정보시스템의 아이디와 비밀번호를 받아 로그인 하는 API"
    )
    @PostMapping("/login")
    public SuccessResponse<LoginTokenResponse> login(@ModelAttribute LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        log.info("{} : 로그인", loginRequest.getId());
        return new SuccessResponse(authUseCase.login(loginRequest.toRequestDto()).toResponse(), SUCCESS_LOGIN);
    }

    @ApiOperation(
            value = "토큰 재발급 기능",
            notes = "access token 만료시 refresh token을 이용하여 access token을 재발급받는 API"
    )
    @PostMapping("/reissue")
    public SuccessResponse<ReissueTokenResponseDto> reissue(Principal principal, @RequestHeader(value = "RefreshToken") String refreshToken) {
        log.info("{} : 토큰 재발급", principal.getName());
        return new SuccessResponse(authUseCase.reissue(refreshToken), SUCCESTT_REISSUE_TOKEN);
    }

    @ApiOperation(
            value = "로그아웃 기능",
            notes = "로그아웃 API"
    )
    @PostMapping("/logout")
    public SuccessResponse logout(@ApiIgnore Principal principal, @RequestHeader(value = "AccessToken") String accessToken) {
        log.info("{} : 로그아웃", principal.getName());
        authUseCase.logout(accessToken);
        return new SuccessResponse(SUCCESS_LOGOUT);
    }

}
