package com.ime.lockmanager.user.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.user.adapter.in.req.ChangePasswordRequest;
import com.ime.lockmanager.user.adapter.in.res.UserCancelLockerResponse;
import com.ime.lockmanager.user.adapter.in.res.UserInfoResponse;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.in.req.ChangePasswordRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserCancelLockerRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.UserCancelLockerResponseDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserUseCase userUseCase;

    @GetMapping("/info")
    public SuccessResponse findUserInfo(Principal principal) throws Exception {
        log.info("{}정보조회",principal.getName());
        UserInfoResponseDto userInfo = userUseCase.findUserInfo(
                UserInfoRequestDto.builder()
                        .studentNum(principal.getName())
                        .build());
        return new SuccessResponse(UserInfoResponse.fromResponseDto(
                userInfo
        ));
    }

    @PutMapping
    public SuccessResponse cancelLocker(Principal principal){
        userUseCase.cancelLocker(
                UserCancelLockerRequestDto.builder()
                        .studentNum(principal.getName())
                        .build()
        );
        return SuccessResponse.ok();
    }

    @PutMapping("/password")
    public SuccessResponse changePassword(Principal principal, @RequestBody ChangePasswordRequest request){
        userUseCase.changePassword(
                ChangePasswordRequestDto.builder()
                        .studentNum(principal.getName())
                        .newPassword(request.getNewPassword())
                        .currentPassword(request.getCurrentPassword())
                        .build());
        return new SuccessResponse("비밀번호가 수정되었습니다.");
    }

    @GetMapping("/role")
    public SuccessResponse getUserRole(Principal principal){
        return new SuccessResponse(userUseCase.checkAdmin(principal.getName()));
    }
}
