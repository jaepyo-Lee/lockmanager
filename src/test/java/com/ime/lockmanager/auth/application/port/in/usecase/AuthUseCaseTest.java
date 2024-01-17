package com.ime.lockmanager.auth.application.port.in.usecase;

import com.ime.lockmanager.auth.application.port.in.req.LoginRequestDto;
import com.ime.lockmanager.auth.application.port.out.AuthToRedisQueryPort;
import com.ime.lockmanager.auth.application.port.out.AuthToUserQueryPort;
import com.ime.lockmanager.common.jwt.JwtHeaderUtil;
import com.ime.lockmanager.common.jwt.JwtProvider;
import com.ime.lockmanager.common.webclient.sejong.service.SejongLoginService;
import com.ime.lockmanager.common.webclient.sejong.service.dto.req.SejongMemberRequestDto;
import com.ime.lockmanager.common.webclient.sejong.service.dto.res.SejongMemberResponseBody;
import com.ime.lockmanager.common.webclient.sejong.service.dto.res.SejongMemberResponseDto;
import com.ime.lockmanager.common.webclient.sejong.service.dto.res.SejongMemberResponseResult;
import com.ime.lockmanager.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.ime.lockmanager.user.domain.Role.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

//@ExtendWith(MockitoExtension.class)
class AuthUseCaseTest {
/*    private static final String ACCESS_TOKEN = "testAccessToken";
    private static final String REFRESH_TOKEN = "testRefreshToken";

    @Mock
    private AuthToUserQueryPort authToUserQueryPort;
    @Mock
    private JwtProvider jwtProvider;
    private AuthUseCase authUseCase;

    @Mock
    private SejongLoginService sejongLoginService;
    @Mock
    private AuthToRedisQueryPort authToRedisQueryPort;
    @Mock
    private JwtHeaderUtil jwtHeaderUtil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
//        authUseCase = new AuthService(authToUserQueryPort, jwtProvider, authToRedisQueryPort, jwtHeaderUtil, sejongLoginService);
    }

    @DisplayName("로그인 성공(회원가입이 안되어있는 상태)")
    @Test
    void loginSuccessWithoutSignup() {
        //given

        //when
        //then

    }

    private static SejongMemberRequestDto mockSejongMemberRequestDto() {
        return SejongMemberRequestDto.builder()
                .id("19011721")
                .pw("test")
                .build();
    }

    private void mockJwtProvider(){
        given(jwtProvider.createAccessToken(any())).willReturn(ACCESS_TOKEN);
        given(jwtProvider.createRefreshToken(any())).willReturn(REFRESH_TOKEN);
    }
    private static LoginRequestDto mockLoginRequestDto() {
        return LoginRequestDto.builder()
                .id("19011721")
                .pw("test")
                .build();
    }

    private static SejongMemberResponseDto mockSejongMemberResponseDto() {
        return SejongMemberResponseDto.builder()
                .msg("test")
                .result(SejongMemberResponseResult.builder()
                        .is_auth("true")
                        .body(SejongMemberResponseBody.builder()
                                .grade("3")
                                .major("무인이동체공학전공")
                                .status("재학")
                                .build())
                        .build())
                .build();
    }

    private static User mockUser() {
        return User.builder()
                .studentNum("19011721")
                .membership(false)
                .status("재학")
                .role(ROLE_USER)
                .name("이재표")
                .build();
    }*/
}