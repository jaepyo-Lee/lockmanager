package com.ime.lockmanager.auth.application.service;

import com.ime.lockmanager.auth.application.port.in.req.LoginRequestDto;
import com.ime.lockmanager.auth.application.port.in.res.LoginTokenResponseDto;
import com.ime.lockmanager.auth.application.port.in.res.ReissueTokenResponseDto;
import com.ime.lockmanager.auth.application.port.in.usecase.AuthUseCase;
import com.ime.lockmanager.auth.application.port.out.AuthToRedisQueryPort;
import com.ime.lockmanager.auth.application.port.out.AuthToUserCommandPort;
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
import com.ime.lockmanager.user.domain.UserState;
import com.ime.lockmanager.user.domain.UserTier;
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
    private final AuthToUserCommandPort authToUserCommandPort;
    private final JwtProvider jwtProvider;
    private final AuthToRedisQueryPort authToRedisQueryPort;
    private final SejongLoginService sejongLoginService;
    private final MajorDetailUseCase majorDetailUseCase;

    @Override
    public LoginTokenResponseDto login(LoginRequestDto loginRequestDto) {
        SejongMemberResponseDto sejongMemberResponseDto = sejongLoginService.callSejongMemberDetailApi(loginRequestDto.toSejongMemberDto());
        MajorDetail majorDetail = findByNameWithMajor(getMajorName(sejongMemberResponseDto));
        Major major = majorDetail.getMajor();
        User user = saveOrFindUser(majorDetail, loginRequestDto, sejongMemberResponseDto);
        updateUserInfo(sejongMemberResponseDto, majorDetail, user);
        TokenSet tokenSet = makeToken(user);
        authToRedisQueryPort.refreshSave(loginRequestDto.getId(), JwtHeaderUtil.getBearerToken(tokenSet.getRefreshToken()));
        return LoginTokenResponseDto.of(tokenSet.getAccessToken(),
                tokenSet.getRefreshToken(),
                user.getRole(),
                user.getId(),
                major.getId(),
                major.getName());
    }

    private User saveOrFindUser(MajorDetail majorDetail, LoginRequestDto loginRequestDto, SejongMemberResponseDto sejongMemberResponseDto) {
        return authToUserQueryPort.findByStudentNum(loginRequestDto.getId()).orElseGet(() ->
                authToUserCommandPort.save(createUserByLoginInfo(
                        LoginInfoDto.builder()
                                .userTier(UserTier.NON_MEMBER)
                                .grade(sejongMemberResponseDto.getResult().getBody().getGrade())
                                .majorDetail(majorDetail)
                                .name(sejongMemberResponseDto.getResult().getBody().getName())
                                .status(sejongMemberResponseDto.getResult().getBody().getStatus())
                                .studentNum(loginRequestDto.getId())
                                .build())
                ));
    }

    private void updateUserInfo(SejongMemberResponseDto sejongMemberResponseDto, MajorDetail majorDetail, User user) {
        UserState matchUserState = UserState.match(sejongMemberResponseDto.getResult().getBody().getStatus());
        user.updateUserInfo(UpdateUserInfoDto.builder()
                .auth(true)
                .status(matchUserState)
                .grade(sejongMemberResponseDto.getResult().getBody().getGrade())
                .majorDetail(majorDetail)
                .build());
    }

    private String getMajorName(SejongMemberResponseDto sejongMemberResponseDto) {
        return sejongMemberResponseDto.getResult().getBody().getMajor();
    }

    private MajorDetail findByNameWithMajor(String majorName) {
        return majorDetailUseCase.findByNameWithMajor(majorName)
                .orElseThrow(() -> new IllegalStateException("등록되지 않은 학과입니다. 학생회에 문의해주세요!"));
    }


    private User createUserByLoginInfo(LoginInfoDto loginInfoDto) {
        UserState matchUserState = UserState.match(loginInfoDto.getStatus());

        return User.builder()
                .userTier(loginInfoDto.getUserTier())
                .name(loginInfoDto.getName())
                .userState(matchUserState)
                .studentNum(loginInfoDto.getStudentNum())
                .role(ROLE_USER)
                .auth(true)
                .grade(loginInfoDto.getGrade())
                .majorDetail(loginInfoDto.getMajorDetail())
                .build();
    }


    @Override
    public ReissueTokenResponseDto reissue(String refreshToken) {
        String bearerToken = JwtHeaderUtil.getBearerToken(refreshToken);
        String studentNum = (String) jwtProvider.convertAuthToken(bearerToken).getTokenClaims().get("studentNum");
        String redisRT = authToRedisQueryPort.getRefreshToken(studentNum); //리프레시토큰은 학번 안들어가있음

        if (Objects.isNull(redisRT) || !redisRT.equals(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }
        User byStudentNum = authToUserQueryPort.findByStudentNum(studentNum)
                .orElseThrow(NotFoundUserException::new);
        TokenSet tokenSet = makeToken(byStudentNum);
        authToRedisQueryPort.removeAndSaveRefreshToken(studentNum, JwtHeaderUtil.getBearerToken(tokenSet.getRefreshToken()));
        return ReissueTokenResponseDto.of(tokenSet.getAccessToken(), tokenSet.getRefreshToken());
    }

    @Override
    public void logout(String accessToken) {
        String bearerToken = JwtHeaderUtil.getBearerToken(accessToken);
        Long tokenExpiration = jwtProvider.getTokenExpiration(bearerToken);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(authentication.getPrincipal());
        SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String studentNum = SecurityContextHolder.getContext().getAuthentication().getName();
        if (authToRedisQueryPort.getRefreshToken(studentNum) != null) {
            authToRedisQueryPort.removeRefreshToken(studentNum);
        }
        authToRedisQueryPort.logoutTokenSave(JwtHeaderUtil.getBearerToken(accessToken), Duration.ofMillis(tokenExpiration));
    }

    private TokenSet makeToken(User user) {
        AuthUser authUser = AuthUser.of(user);
        String refreshToken = "Bearer " + jwtProvider.createRefreshToken(authUser);
        String accessToken = "Bearer " + jwtProvider.createAccessToken(authUser);
        return TokenSet.of(accessToken, refreshToken);
    }
}