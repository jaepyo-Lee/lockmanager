package com.ime.lockmanager.user.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.user.adapter.in.req.ModifiedUserInfoRequest;
import com.ime.lockmanager.user.adapter.in.res.UserInfoAdminResponse;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.AllUserInfoResponseDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/api/user")
class UserAdminController {

    private final UserUseCase userUseCase;

    @ApiOperation(
            value = "모든 사용자의 정보를 조회",
            notes = "모든 사용자의 정보를 조회하여 반환해주는 API(관리자용)"
    )
    @GetMapping("")
    public SuccessResponse adminInfo(Pageable pageable){
        Page<UserInfoResponseDto> allUserInfo = userUseCase.findAllUserInfo(pageable);
        return new SuccessResponse(
                allUserInfo.stream()
                        .map(userInfoResponseDto -> userInfoResponseDto.toResponse())
                        .collect(Collectors.toList())
        );

    }

    @ApiOperation(
            value = "사용자 정보 수정",
            notes = "수정된 사용자의 정보를 받아 실제 dB에 업데이트해주는 API(관리자용)"
    )
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
