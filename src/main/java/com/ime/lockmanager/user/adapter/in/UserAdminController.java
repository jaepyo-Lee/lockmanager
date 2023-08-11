package com.ime.lockmanager.user.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.user.adapter.in.req.ModifiedUserInfoRequest;
import com.ime.lockmanager.user.adapter.in.res.UserInfoAdminResponse;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.AllUserInfoResponseDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/modifiedInfo")
    public SuccessResponse modifiedUserInfo(@RequestBody List<ModifiedUserInfoRequest> modifiedUserInfoRequest) throws Exception {
        userUseCase.modifiedUserInfo(
                modifiedUserInfoRequest.stream()
                        .map(ModifiedUserInfoRequest::toRequestDto)
                        .collect(Collectors.toList())
        );
        return SuccessResponse.ok();
    }


}
