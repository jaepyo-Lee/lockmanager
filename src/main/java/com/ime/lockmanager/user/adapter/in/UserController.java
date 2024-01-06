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
        log.info("{} : 정보조회", principal.getName());
        UserInfoQueryResponseDto userInfo = userUseCase.findUserInfoByStudentNum(
                UserInfoRequestDto.builder()
                        .userId(userId)
                        .build());
        return new SuccessResponse(UserInfoResponse.fromResponseDto(
                userInfo
        ));
    }

    @Deprecated
    @ApiOperation(
            value = "사용자 권한 조회",
            notes = "사용자의 권한 조회 API"
    )
    @GetMapping("/role")
    public SuccessResponse getUserRole(Principal principal) {
        return new SuccessResponse(userUseCase.checkAdmin(principal.getName()));
    }

    @ApiOperation(value = "학생회비 납부 신청")
    @PostMapping("/membership")
    public SuccessResponse<UserInfoResponse> applyMembership(@ApiIgnore Authentication authentication) {
        return new SuccessResponse(userUseCase.applyMembership(authentication.getName()).toResponse());
    }

    @ApiOperation(value = "학생회비 납부 조회")
    @GetMapping("/membership")
    public SuccessResponse<CheckMembershipResponse> checkMembership(@ApiIgnore Authentication authentication) {
        CheckMembershipResponse checkMembershipResponse = userUseCase.checkMembership(authentication.getName())
                .toResponse();
        return new SuccessResponse(checkMembershipResponse);
    }
}
