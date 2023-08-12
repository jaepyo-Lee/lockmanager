package com.ime.lockmanager.locker.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.locker.adapter.in.req.LockerRegisterRequest;
import com.ime.lockmanager.locker.adapter.in.res.LockerPeriodResponse;
import com.ime.lockmanager.locker.adapter.in.res.LockerRegisterResponse;
import com.ime.lockmanager.locker.adapter.in.res.LockerReserveResponse;
import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import com.ime.lockmanager.locker.application.service.RedissonLockLockerFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/locker")
public class LockerController {

    private final RedissonLockLockerFacade redissonLockLockerFacade;
    private final LockerUseCase lockerUseCase;


    //사물함 예약하는 api
    @ApiOperation(
            value = "사물함 예약",
            notes = "사용자가 사물함을 선택할시 해당 사물함을 예약하는 API"
    )
    @PostMapping("/register")
    public SuccessResponse registerLocker(Principal principal, @RequestBody LockerRegisterRequest lockerRegisterRequest) throws Exception {
        System.out.println(principal.getName());
        return new SuccessResponse(LockerRegisterResponse.fromResponse(redissonLockLockerFacade.register(lockerRegisterRequest.toRequestDto(principal.getName()))));
    }

    //예약된 사물함 가져오기
    @ApiOperation(
            value = "예약된 사물함 조회",
            notes = "예약된 사물함을 조회하는 API"
    )
    @GetMapping("/reserved")
    public SuccessResponse findReservedLocker(){
        return new SuccessResponse(LockerReserveResponse.fromResponse(lockerUseCase.findReserveLocker()));
    }

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
