package com.ime.lockmanager.auth.application.service;

import com.ime.lockmanager.auth.application.port.in.response.LoginRequestDto;
import com.ime.lockmanager.auth.application.port.in.response.TokenResponseDto;
import com.ime.lockmanager.auth.application.port.in.usecase.AuthUseCase;
import com.ime.lockmanager.auth.application.port.out.AuthToRedisQueryPort;
import com.ime.lockmanager.auth.application.port.out.AuthToUserQueryPort;
import com.ime.lockmanager.auth.domain.AuthUser;
import com.ime.lockmanager.common.format.exception.auth.InvalidRefreshTokenException;
import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.common.jwt.JwtHeaderUtil;
import com.ime.lockmanager.common.jwt.JwtProvider;
import com.ime.lockmanager.common.jwt.TokenSet;
import com.ime.lockmanager.common.webclient.sejong.service.dto.res.SejongMemberResponseDto;
import com.ime.lockmanager.common.webclient.sejong.service.SejongLoginService;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AuthService implements AuthUseCase {
    private final AuthToUserQueryPort authToUserQueryPort;
    private final JwtProvider jwtProvider;
    private final AuthToRedisQueryPort authToRedisQueryPort;
    private final JwtHeaderUtil jwtHeaderUtil;
    private final SejongLoginService sejongLoginService;

    @Override
    @Transactional
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        User user = authToUserQueryPort.findByStudentNum(loginRequestDto.getId()).orElseGet(() -> {
            SejongMemberResponseDto sejongMemberResponseDto = sejongLoginService.callSejongMemberDetailApi(loginRequestDto.toSejongMemberDto());
            return authToUserQueryPort.save(User.builder()
                    .name(sejongMemberResponseDto.getResult().getBody().getName())
                    .status(sejongMemberResponseDto.getResult().getBody().getStatus())
                    .studentNum(loginRequestDto.getId())
                    .role(Role.ROLE_USER)
                    .build());
        });
        TokenSet tokenSet = makeToken(user);
        authToRedisQueryPort.refreshSave(loginRequestDto.getId(),jwtHeaderUtil.getBearerToken(tokenSet.getRefreshToken()));
        return TokenResponseDto.of(tokenSet.getAccessToken(),tokenSet.getRefreshToken());
    }

    @Override
    @Transactional
    public TokenResponseDto reissue(String refreshToken) {
        String bearerToken = jwtHeaderUtil.getBearerToken(refreshToken);
        String studentNum = (String) jwtProvider.convertAuthToken(bearerToken).getTokenClaims().get("studentNum");
        String redisRT = authToRedisQueryPort.getRefreshToken(studentNum); //리프레시토큰은 학번 안들어가있음

        if(Objects.isNull(redisRT) || !redisRT.equals(refreshToken)){
            throw new InvalidRefreshTokenException();
        }
        User byStudentNum = authToUserQueryPort.findByStudentNum(studentNum)
                .orElseThrow(NotFoundUserException::new);
        TokenSet tokenSet = makeToken(byStudentNum);
        authToRedisQueryPort.removeAndSaveRefreshToken(studentNum,jwtHeaderUtil.getBearerToken(tokenSet.getRefreshToken()));
        return TokenResponseDto.of(tokenSet.getAccessToken(),tokenSet.getRefreshToken());
    }

    @Override
    public ResponseEntity<String> logout(String accessToken) {
        String bearerToken = jwtHeaderUtil.getBearerToken(accessToken);
        Long tokenExpiration = jwtProvider.getTokenExpiration(bearerToken);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(authentication.getPrincipal());
        SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String studentNum = SecurityContextHolder.getContext().getAuthentication().getName();
        if(authToRedisQueryPort.getRefreshToken(studentNum)!=null){
            authToRedisQueryPort.removeRefreshToken(studentNum);
        }
        authToRedisQueryPort.logoutTokenSave(jwtHeaderUtil.getBearerToken(accessToken), Duration.ofMillis(tokenExpiration));
        return ResponseEntity.ok("로그아웃되었습니다.");
    }



    private TokenSet makeToken(User user){
        AuthUser authUser = AuthUser.of(user);
        String refreshToken = "Bearer "+jwtProvider.createRefreshToken(authUser);
        String accessToken = "Bearer "+ jwtProvider.createAccessToken(authUser);
        return TokenSet.of(accessToken, refreshToken);
    }


}
