package com.ime.lockmanager.user.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.user.adapter.in.res.UserInfoAdminResponse;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.AllUserInfoResponseDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/api/user")
public class UserAdminController {

    private final UserUseCase userUseCase;

    @GetMapping("")
    public SuccessResponse adminInfo(){
        List<UserInfoResponseDto> allUserInfo = userUseCase.findAllUserInfo();
        return new SuccessResponse(
                allUserInfo.stream()
                        .map(userInfoResponseDto -> userInfoResponseDto.toResponse())
                        .collect(Collectors.toList())
        );

    }

}
