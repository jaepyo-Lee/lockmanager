package com.ime.lockmanager.locker.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.locker.adapter.in.res.LockerPeriodResponse;
import com.ime.lockmanager.locker.adapter.in.res.LockerReserveResponse;
import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/locker")
class LockerController {

    private final LockerUseCase lockerUseCase;


    @ApiOperation(
            value = "사물함 예약기간 조회",
            notes = "사물함의 예약기간을 조회하는 API"
    )
    @GetMapping("/period")
    public SuccessResponse getPeriod() {
        return new SuccessResponse(LockerPeriodResponse.fromResponse(lockerUseCase.getLockerPeriod()));
    }

    @ApiOperation(
            value = "현재시간 조회",
            notes = "서버의 현재시간을 조회하는 API(위치 및 URL변경될지도모름)"
    )
    @GetMapping("/livetime")
    public SuccessResponse getLiveTime(){
        return new SuccessResponse(now());
    }
}
