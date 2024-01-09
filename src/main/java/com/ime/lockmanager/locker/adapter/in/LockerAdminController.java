package com.ime.lockmanager.locker.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.locker.adapter.in.req.LockerCreateRequest;
import com.ime.lockmanager.locker.adapter.in.req.LockerSetTimeRequest;
import com.ime.lockmanager.locker.adapter.in.req.ModifyLockerInfoReqeust;
import com.ime.lockmanager.locker.adapter.in.res.LockerCreateResponse;
import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import com.ime.lockmanager.locker.application.port.in.req.LockerCreateRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LeftLockerResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.admin.prefix}")
class LockerAdminController {
    private final LockerUseCase lockerUseCase;

    @ApiOperation(
            value = "남은 사물함 목록 조회 api"
    )
    @GetMapping("/majors/{majorId}/lockers/left-list")
    public SuccessResponse<LeftLockerResponse> getLeftLocker(@ApiIgnore Authentication authentication,
                                                             @PathVariable Long majorId) {
        return new SuccessResponse(lockerUseCase.getLeftLocker(majorId).toResponse());
    }

    @ApiOperation(
            value = "새로운 사물함 생성",
            notes = "새로운 사물함을 생성하는 API"
    )
    @PostMapping("/majors/{majorId}/lockers")
    public SuccessResponse<LockerCreateResponse> createLocker(@ApiIgnore Authentication authentication,
                                                              @RequestPart(required = false) MultipartFile image,
                                                              @PathVariable Long majorId,
                                                              @Valid @RequestPart LockerCreateRequest lockerCreateRequest) throws IOException {
        log.info("{} : 새로운 사물함 생성", authentication.getName());
        String createdLockerName = lockerUseCase.createLocker(
                        LockerCreateRequestDto
                                .fromRequest(lockerCreateRequest, image), majorId
                )
                .getCreatedLockerName();
        return new SuccessResponse(
                LockerCreateResponse.builder()
                        .createdLockerName(createdLockerName)
                        .build()
        );
    }

    @ApiOperation(
            value = "사물함 정보 수정",
            notes = "사물함의 여러 정보를 수정하는 api"
    )
    @PatchMapping("/lockers/{lockerId}")
    public SuccessResponse modifyLockerInfo(@ApiIgnore Authentication authentication,
                                            @PathVariable Long lockerId,
                                            @RequestPart(required = false) MultipartFile image,
                                            @Valid @RequestPart ModifyLockerInfoReqeust modifyLockerInfoReqeust) throws IOException {
        lockerUseCase.modifyLockerInfo(modifyLockerInfoReqeust.toReqeustDto(lockerId,image));
        return SuccessResponse.ok();
    }
}
