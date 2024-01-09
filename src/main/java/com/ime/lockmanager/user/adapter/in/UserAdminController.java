package com.ime.lockmanager.user.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.user.adapter.in.req.DetermineApplyingRequest;
import com.ime.lockmanager.user.adapter.in.req.ModifiedUserInfoRequest;
import com.ime.lockmanager.user.adapter.in.res.AllApplyingStudentPageResponse;
import com.ime.lockmanager.user.adapter.in.res.UserInfoAdminPageResponse;
import com.ime.lockmanager.user.adapter.in.res.UserTierResponse;
import com.ime.lockmanager.user.application.port.in.UserUseCase;
import com.ime.lockmanager.user.application.port.out.res.AllUserInfoForAdminResponseDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.admin.prefix}/users")
class UserAdminController {

    private final UserUseCase userUseCase;

    @ApiOperation(
            value = "모든 사용자의 정보를 조회",
            notes = "모든 사용자의 정보를 조회하여 반환해주는 API(관리자용),검색기능 추가예정,uri 수정될수 있음"
    )
    @ApiImplicitParam(
            name = "page"
            , value = "원하는 페이지번호"
            , required = true
            , dataType = "int"
            , defaultValue = "0")
    @GetMapping("")
    public SuccessResponse<UserInfoAdminPageResponse> adminInfo(@ApiIgnore Authentication authentication,
                                                                @RequestParam(name = "page", defaultValue = "0") int page) {
        Page<AllUserInfoForAdminResponseDto> allUserInfo = userUseCase.findAllUserInfo(authentication.getName(), page);
        return new SuccessResponse(
                UserInfoAdminPageResponse.builder()
                        .adminResponse(allUserInfo.stream()
                                .map(allUserInfoForAdminResponseDto -> allUserInfoForAdminResponseDto.toResponse())
                                .collect(Collectors.toList()))
                        .currentPage(allUserInfo.getNumber())
                        .totalPage(allUserInfo.getTotalPages())
                        .currentElementSize(allUserInfo.getNumberOfElements())
                        .build()
        );

    }

    @ApiOperation(
            value = "수정된 사용자의 정보를 받아 실제 dB에 업데이트해주는 API(관리자용)"
    )
    @PatchMapping("")
    public SuccessResponse modifiedUserInfo(@ApiIgnore Principal principal,
                                            @Valid @RequestBody ModifiedUserInfoRequest modifiedUserInfoRequest)
            throws Exception {
        log.info("{} : 사용자 정보 수정(관리자)", principal.getName());
        userUseCase.modifiedUserInfo(modifiedUserInfoRequest.toRequestDto());
        return SuccessResponse.ok();
    }

    @ApiImplicitParam(
            name = "page"
            , value = "원하는 페이지번호"
            , required = true
            , dataType = "int"
            , defaultValue = "0")
    @ApiOperation(value = "학생회비 납부 신청자 조회")
    @GetMapping("/tier/apply")
    public SuccessResponse<AllApplyingStudentPageResponse> findAllApplyingStudent(@ApiIgnore Authentication authentication,
                                                                                  @RequestParam(name = "page",
                                                                                          defaultValue = "0") int page) {
        AllApplyingStudentPageResponse response = userUseCase.findAllApplying(authentication.getName(), page)
                .toResponse();
        return new SuccessResponse(response);
    }

    @ApiOperation(value = "학생회비 납부 신청자 승인 및 거절 API")
    @ApiImplicitParam(
            name = "isApprove"
            , value = "학생회비 납부 승인 또는 거절하기 위한 boolean값"
            , required = true
            , dataType = "boolean")
    @PostMapping("/tier/apply")
    public SuccessResponse<UserTierResponse> determineApplying(@ApiIgnore Authentication authentication,
                                                               @Valid @RequestBody DetermineApplyingRequest request,
                                                               @RequestParam(name = "isApprove") boolean isApprove) {
        UserTierResponse response = userUseCase.determineApplying(request.toRequestDto(), isApprove).toResponse();
        return new SuccessResponse(response);
    }
}
