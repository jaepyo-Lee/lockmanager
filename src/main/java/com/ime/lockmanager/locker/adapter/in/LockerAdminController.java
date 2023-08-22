package com.ime.lockmanager.locker.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.locker.adapter.in.req.LockerSetTimeRequest;
import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/api/locker")
class LockerAdminController {
    private final LockerUseCase lockerUseCase;

    @ApiOperation(
            value = "사물함 예약 기간 저장",
            notes = "사물함의 예약기간을 저장하기 위한 API"
    )
    @PostMapping("/time")
    public SuccessResponse setPeriod(HttpServletRequest req, @RequestBody LockerSetTimeRequest timeRequest){
        lockerUseCase.setLockerPeriod(timeRequest.toRequestDto());
        return SuccessResponse.ok("시간설정완료");
    }

    @ApiOperation(
            value = "사물함 정보 전체 삭제",
            notes = "사물함 초기화(eg. 학기초 및 롤백)의 경우 예약된 모든 사물함을 초기화하는 API"
    )
    @PostMapping("/reset")
    public SuccessResponse resetLocker(){
        lockerUseCase.initLockerInfo();
        return SuccessResponse.ok();
    }
}
