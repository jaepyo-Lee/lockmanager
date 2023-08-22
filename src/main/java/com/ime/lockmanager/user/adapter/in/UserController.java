package com.ime.lockmanager.user.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.user.adapter.in.res.UserInfoResponse;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.in.req.UserCancelLockerRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
class UserController {

    private final UserUseCase userUseCase;

    @ApiOperation(
            value = "사용자 정보 조회",
            notes = "마이페이지접속시 사용자 정보조회 API"
    )
    @GetMapping("/info")
    public SuccessResponse findUserInfo(Principal principal) throws Exception {
        log.info("{}정보조회",principal.getName());
        UserInfoResponseDto userInfo = userUseCase.findUserInfoByStudentNum(
                UserInfoRequestDto.builder()
                        .studentNum(principal.getName())
                        .build());
        return new SuccessResponse(UserInfoResponse.fromResponseDto(
                userInfo
        ));
    }

    @ApiOperation(
            value = "사물함 취소",
            notes = "현재 사용자의 예약된 사물함을 취소하는 API"
    )
    @PutMapping
    public SuccessResponse cancelLocker(Principal principal){
        userUseCase.cancelLockerByStudentNum(
                UserCancelLockerRequestDto.builder()
                        .studentNum(principal.getName())
                        .build()
        );
        return SuccessResponse.ok();
    }

    @ApiOperation(
            value = "사용자 권한 조회",
            notes = "사용자의 권한 조회 API"
    )
    @GetMapping("/role")
    public SuccessResponse getUserRole(Principal principal){
        return new SuccessResponse(userUseCase.checkAdmin(principal.getName()));
    }
}
