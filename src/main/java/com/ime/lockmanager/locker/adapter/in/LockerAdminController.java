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


}
