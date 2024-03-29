package com.ime.lockmanager.user.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.user.adapter.in.res.CheckMembershipResponse;
import com.ime.lockmanager.user.adapter.in.res.UserInfoResponse;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.out.res.UserInfoQueryResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.user.prefix}/users")
class UserController {

    private final UserUseCase userUseCase;

    @ApiOperation(
            value = "사용자 정보 조회",
            notes = "마이페이지접속시 사용자 정보조회 API"
    )
    @GetMapping("/{userId}")
    public SuccessResponse<UserInfoResponse> findUserInfo(@ApiIgnore Principal principal,@PathVariable Long userId) throws Exception {
        log.info("{} : 정보조회 시작", principal.getName());
        UserInfoQueryResponseDto userInfo = userUseCase.findUserInfoByStudentNum(
                UserInfoRequestDto.builder()
                        .userId(userId)
                        .build());
        log.info("{} : 정보조회 끝", principal.getName());
        return new SuccessResponse(UserInfoResponse.fromResponseDto(
                userInfo
        ));
    }


    @ApiOperation(value = "학생회비 납부 신청")
    @PostMapping("/{userId}/membership")
    public SuccessResponse applyMembership(@ApiIgnore Authentication authentication,
                                                             @PathVariable Long userId) {
        userUseCase.applyMembership(userId);
        return SuccessResponse.ok();
    }
}
