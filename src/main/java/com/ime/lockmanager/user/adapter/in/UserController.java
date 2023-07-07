package com.ime.lockmanager.user.adapter.in;

import com.ime.lockmanager.user.adapter.in.res.UserInfoResponse;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserUseCase userUseCase;

    @GetMapping("/info")
    public UserInfoResponse findUserInfo(Principal principal) throws Exception {
        log.info("{}정보조회",principal.getName());
        UserInfoResponseDto userInfo = userUseCase.findUserInfo(
                UserInfoRequestDto.builder()
                        .studentNum(principal.getName())
                        .build());
        return UserInfoResponse.fromResponse(
                userInfo
        );
    }
}
