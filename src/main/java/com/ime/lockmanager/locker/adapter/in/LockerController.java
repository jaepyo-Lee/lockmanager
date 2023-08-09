package com.ime.lockmanager.locker.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.locker.adapter.in.req.LockerRegisterRequest;
import com.ime.lockmanager.locker.adapter.in.res.LockerPeriodResponse;
import com.ime.lockmanager.locker.adapter.in.res.LockerRegisterResponse;
import com.ime.lockmanager.locker.adapter.in.res.LockerReserveResponse;
import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import com.ime.lockmanager.locker.application.service.RedissonLockLockerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.format.DateTimeFormatter;

import static java.time.LocalTime.now;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/locker")
public class LockerController {

    private final RedissonLockLockerFacade redissonLockLockerFacade;
    private final LockerUseCase lockerUseCase;


    //사물함 예약하는 api
    @PostMapping("/register")
    public SuccessResponse registerLocker(Principal principal, @RequestBody LockerRegisterRequest lockerRegisterRequest) throws Exception {
        System.out.println(principal.getName());
        return new SuccessResponse(LockerRegisterResponse.fromResponse(redissonLockLockerFacade.register(lockerRegisterRequest.toRequestDto(principal.getName()))));
    }

    //예약된 사물함 가져오기
    @GetMapping("/reserved")
    public SuccessResponse findReservedLocker(){
        return new SuccessResponse(LockerReserveResponse.fromResponse(lockerUseCase.findReserveLocker()));
    }


    @GetMapping("/period")
    public SuccessResponse getPeriod() {
        return new SuccessResponse(LockerPeriodResponse.fromResponse(lockerUseCase.getLockerPeriod()));
    }

    @GetMapping("/livetime")
    public SuccessResponse getLiveTime(){
        return new SuccessResponse(now());
    }
}
