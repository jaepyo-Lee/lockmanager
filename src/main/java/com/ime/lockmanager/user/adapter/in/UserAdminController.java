package com.ime.lockmanager.user.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.user.adapter.in.req.ModifiedUserInfoRequest;
import com.ime.lockmanager.user.adapter.in.res.UserInfoAdminPageResponse;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.out.res.AllUserInfoForAdminResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.admin.prefix}/user")
class UserAdminController {

    private final UserUseCase userUseCase;

    @ApiOperation(
            value = "모든 사용자의 정보를 조회",
            notes = "모든 사용자의 정보를 조회하여 반환해주는 API(관리자용)"
    )
    @GetMapping("")
    public SuccessResponse adminInfo(@ApiIgnore Authentication authentication,
                                     Pageable pageable) {
        Page<AllUserInfoForAdminResponseDto> allUserInfo = userUseCase.findAllUserInfo(authentication.getName(),
                pageable);
        return new SuccessResponse(
                UserInfoAdminPageResponse.builder()
                        .adminResponse(allUserInfo.stream().map(allUserInfoForAdminResponseDto -> allUserInfoForAdminResponseDto.toResponse()).collect(Collectors.toList()))
                        .currentPage(allUserInfo.getNumber())
                        .totalPage(allUserInfo.getTotalPages())
                        .currentElementSize(allUserInfo.getNumberOfElements())
                        .build()
        );

    }

    @ApiOperation(
            value = "사용자 정보 수정",
            notes = "수정된 사용자의 정보를 받아 실제 dB에 업데이트해주는 API(관리자용)"
    )
    @PutMapping("/modifiedInfo")
    public SuccessResponse modifiedUserInfo(@ApiIgnore Principal principal, @RequestBody List<ModifiedUserInfoRequest> modifiedUserInfoRequest) throws Exception {
        log.info("{} : 사용자 정보 수정(관리자)", principal.getName());
        userUseCase.modifiedUserInfo(
                modifiedUserInfoRequest.stream()
                        .map(ModifiedUserInfoRequest::toRequestDto)
                        .collect(Collectors.toList())
        );
        return SuccessResponse.ok();
    }


}
