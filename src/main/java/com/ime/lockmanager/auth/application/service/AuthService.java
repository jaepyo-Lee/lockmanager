package com.ime.lockmanager.auth.application.service;

import com.ime.lockmanager.auth.application.port.in.response.LoginRequestDto;
import com.ime.lockmanager.auth.application.port.in.response.TokenResponseDto;
import com.ime.lockmanager.auth.application.port.in.usecase.AuthUseCase;
import com.ime.lockmanager.auth.application.port.out.AuthToRedisQueryPort;
import com.ime.lockmanager.auth.application.port.out.AuthToUserQueryPort;
import com.ime.lockmanager.auth.application.service.dto.LoginInfoDto;
import com.ime.lockmanager.auth.domain.AuthUser;
import com.ime.lockmanager.common.format.exception.auth.jwt.InvalidRefreshTokenException;
import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.common.jwt.JwtHeaderUtil;
import com.ime.lockmanager.common.jwt.JwtProvider;
import com.ime.lockmanager.common.jwt.TokenSet;
import com.ime.lockmanager.common.webclient.sejong.service.dto.res.SejongMemberResponseDto;
import com.ime.lockmanager.common.webclient.sejong.service.SejongLoginService;
import com.ime.lockmanager.major.application.port.in.MajorDetailUseCase;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.major.domain.MajorDetail;
import com.ime.lockmanager.user.domain.User;
import com.ime.lockmanager.user.domain.dto.UpdateUserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.Objects;

import static com.ime.lockmanager.user.domain.Role.ROLE_USER;

@Transactional
@RequiredArgsConstructor
@Service
class AuthService implements AuthUseCase {
    private final AuthToUserQueryPort authToUserQueryPort;
    private final JwtProvider jwtProvider;
    private final AuthToRedisQueryPort authToRedisQueryPort;
    private final JwtHeaderUtil jwtHeaderUtil;
    private final SejongLoginService sejongLoginService;
    private final MajorDetailUseCase majorDetailUseCase;

    @Override
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        SejongMemberResponseDto sejongMemberResponseDto = sejongLoginService.callSejongMemberDetailApi(loginRequestDto.toSejongMemberDto());
        Major major = findMajorByMajorDetailName(getMajorName(sejongMemberResponseDto));
        User user = saveOrFindUser(major, loginRequestDto, sejongMemberResponseDto);
        updateUserInfo(sejongMemberResponseDto, major, user);
        TokenSet tokenSet = makeToken(user);
        authToRedisQueryPort.refreshSave(loginRequestDto.getId(), jwtHeaderUtil.getBearerToken(tokenSet.getRefreshToken()));
        return TokenResponseDto.of(tokenSet.getAccessToken(), tokenSet.getRefreshToken());
    }

    private User saveOrFindUser(Major major, LoginRequestDto loginRequestDto, SejongMemberResponseDto sejongMemberResponseDto) {
        return authToUserQueryPort.findByStudentNum(loginRequestDto.getId()).orElseGet(() ->
                authToUserQueryPort.save(createUserByLoginInfo(
                        LoginInfoDto.builder()
                                .grade(sejongMemberResponseDto.getResult().getBody().getGrade())
                                .major(major)
                                .name(sejongMemberResponseDto.getResult().getBody().getName())
                                .status(sejongMemberResponseDto.getResult().getBody().getStatus())
                                .studentNum(loginRequestDto.getId())
                                .build())
                ));
    }

    private void updateUserInfo(SejongMemberResponseDto sejongMemberResponseDto, Major major, User user) {
        user.updateUserInfo(UpdateUserInfoDto.builder()
                .auth(true)
                .status(sejongMemberResponseDto.getResult().getBody().getStatus())
                .grade(sejongMemberResponseDto.getResult().getBody().getGrade())
                .major(major)
                .build());
    }

    private String getMajorName(SejongMemberResponseDto sejongMemberResponseDto) {
        return sejongMemberResponseDto.getResult().getBody().getMajor();
    }

    private Major findMajorByMajorDetailName(String majorName) {
        MajorDetail majorDetailByMajorName = findMajorDetailByName(majorName);
        Major major = majorDetailByMajorName.getMajor();
        return major;
    }

    private MajorDetail findMajorDetailByName(String majorName) {
        return majorDetailUseCase.findMajorDetailByName(majorName)
                .orElseThrow(() -> new IllegalStateException("등록되지 않은 학과입니다. 학생회에 문의해주세요!"));
    }


    private User createUserByLoginInfo(LoginInfoDto loginInfoDto) {
        return User.builder()
                .name(loginInfoDto.getName())
                .status(loginInfoDto.getStatus())
                .studentNum(loginInfoDto.getStudentNum())
                .role(ROLE_USER)
                .auth(true)
                .grade(loginInfoDto.getGrade())
                .major(loginInfoDto.getMajor())
                .build();
    }


    @Override
    public TokenResponseDto reissue(String refreshToken) {
        String bearerToken = jwtHeaderUtil.getBearerToken(refreshToken);
        String studentNum = (String) jwtProvider.convertAuthToken(bearerToken).getTokenClaims().get("studentNum");
        String redisRT = authToRedisQueryPort.getRefreshToken(studentNum); //리프레시토큰은 학번 안들어가있음

        if (Objects.isNull(redisRT) || !redisRT.equals(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }
        User byStudentNum = authToUserQueryPort.findByStudentNum(studentNum)
                .orElseThrow(NotFoundUserException::new);
        TokenSet tokenSet = makeToken(byStudentNum);
        authToRedisQueryPort.removeAndSaveRefreshToken(studentNum, jwtHeaderUtil.getBearerToken(tokenSet.getRefreshToken()));
        return TokenResponseDto.of(tokenSet.getAccessToken(), tokenSet.getRefreshToken());
    }

    @Override
    public void logout(String accessToken) {
        String bearerToken = jwtHeaderUtil.getBearerToken(accessToken);
        Long tokenExpiration = jwtProvider.getTokenExpiration(bearerToken);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(authentication.getPrincipal());
        SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String studentNum = SecurityContextHolder.getContext().getAuthentication().getName();
        if (authToRedisQueryPort.getRefreshToken(studentNum) != null) {
            authToRedisQueryPort.removeRefreshToken(studentNum);
        }
        authToRedisQueryPort.logoutTokenSave(jwtHeaderUtil.getBearerToken(accessToken), Duration.ofMillis(tokenExpiration));
    }

    private TokenSet makeToken(User user) {
        AuthUser authUser = AuthUser.of(user);
        String refreshToken = "Bearer " + jwtProvider.createRefreshToken(authUser);
        String accessToken = "Bearer " + jwtProvider.createAccessToken(authUser);
        return TokenSet.of(accessToken, refreshToken);
    }
}