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

@Transactional
@RequiredArgsConstructor
@Service
class AuthService implements AuthUseCase {
    private final AuthToUserQueryPort authToUserQueryPort;
    private final JwtProvider jwtProvider;
    private final AuthToRedisQueryPort authToRedisQueryPort;
    private final JwtHeaderUtil jwtHeaderUtil;
    private final SejongLoginService sejongLoginService;

    @Override
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        SejongMemberResponseDto sejongMemberResponseDto = sejongLoginService.callSejongMemberDetailApi(loginRequestDto.toSejongMemberDto());
        User user = authToUserQueryPort.findByStudentNum(loginRequestDto.getId()).orElseGet(() ->
                authToUserQueryPort.save(User.builder()
                        .name(sejongMemberResponseDto.getResult().getBody().getName())
                        .status(sejongMemberResponseDto.getResult().getBody().getStatus())
                        .studentNum(loginRequestDto.getId())
                        .role(Role.ROLE_USER)
                        .auth(true)
                        .grade(sejongMemberResponseDto.getResult().getIs_auth())
                        .major(sejongMemberResponseDto.getResult().getBody().getMajor())
                        .build())
        );
        /*if(user.isAuth()==false){ //로그인이 아닌 학생회비 파일로 회원등록이 된 사람이 들어올때, 모든 값을 넣어줘야함
            updateUserInfo(user, sejongMemberResponseDto);
        }else{ // 이미 로그인을 했던사람, 재학상태만 바꾸면됨
            updateUserStatusInfo(user,sejongMemberResponseDto);
        } db 컬럼이 변경되면서 추가 정보를 넣어야하는데, 로그인한 사람들이 이미많은데, 정보를 넣어줄 방법이없어서 일단 주석 */
        updateUserInfo(user, sejongMemberResponseDto);
        TokenSet tokenSet = makeToken(user);
        authToRedisQueryPort.refreshSave(loginRequestDto.getId(),jwtHeaderUtil.getBearerToken(tokenSet.getRefreshToken()));
        return TokenResponseDto.of(tokenSet.getAccessToken(),tokenSet.getRefreshToken());
    }

    private void updateUserInfo(User user,SejongMemberResponseDto sejongMemberResponseDto) {
        user.updateUserInfo(sejongMemberResponseDto.toUpdateUserInfoDto());
    }

    @Override
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

    private void updateUserStatusInfo(User user, SejongMemberResponseDto sejongMemberResponseDto){
        user.updateUserStatusInfo(sejongMemberResponseDto.toUpdateUserStatusInfoDto());
    }


    private TokenSet makeToken(User user){
        AuthUser authUser = AuthUser.of(user);
        String refreshToken = "Bearer "+jwtProvider.createRefreshToken(authUser);
        String accessToken = "Bearer "+ jwtProvider.createAccessToken(authUser);
        return TokenSet.of(accessToken, refreshToken);
    }


}
