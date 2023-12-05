package com.ime.lockmanager.locker.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.locker.adapter.in.req.LockerCreateRequest;
import com.ime.lockmanager.locker.adapter.in.req.LockerSetTimeRequest;
import com.ime.lockmanager.locker.adapter.in.req.ModifyLockerInfoReqeust;
import com.ime.lockmanager.locker.adapter.in.res.LockerCreateResponse;
import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import com.ime.lockmanager.locker.application.port.in.req.LockerCreateRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerCreateResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.admin.prefix}/locker")
class LockerAdminController {
    private final LockerUseCase lockerUseCase;

    @ApiOperation(
            value = "사물함 예약 기간 저장",
            notes = "사물함의 예약기간을 저장하기 위한 API, 사용안함"
    )
    @PostMapping("/time")
    public SuccessResponse setPeriod(@ApiIgnore Principal principal, HttpServletRequest req, @RequestBody LockerSetTimeRequest timeRequest) {
        log.info("{} : 시간설정(관리자)", principal.getName());
        lockerUseCase.setLockerPeriod(timeRequest.toRequestDto());
        return SuccessResponse.ok("시간설정완료");
    }

    @ApiOperation(
            value = "새로운 사물함 생성",
            notes = "새로운 사물함을 생성하는 API"
    )
    @PostMapping()
    public SuccessResponse<LockerCreateResponse> createLocker(@ApiIgnore Authentication authentication,
                                                              @RequestBody LockerCreateRequest lockerCreateRequest) {
        log.info("{} : 새로운 사물함 생성", authentication.getName());

        String createdLockerName = lockerUseCase.createLocker(
                        LockerCreateRequestDto
                                .fromRequestDto(lockerCreateRequest), authentication.getName()
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
    @PutMapping("/{lockerId}")
    public SuccessResponse modifyLockerInfo(@ApiIgnore Authentication authentication,
                                            @PathVariable Long lockerId,
                                            @RequestBody ModifyLockerInfoReqeust modifyLockerInfoReqeust){
        lockerUseCase.modifyLockerInfo(modifyLockerInfoReqeust.toReqeustDto(lockerId));
        return SuccessResponse.ok();
    }
}
