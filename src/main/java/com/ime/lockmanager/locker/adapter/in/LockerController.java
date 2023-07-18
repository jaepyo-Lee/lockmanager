package com.ime.lockmanager.locker.adapter.in;

import com.ime.lockmanager.locker.adapter.in.req.LockerRegisterRequest;
import com.ime.lockmanager.locker.adapter.in.res.LockerRegisterResponse;
import com.ime.lockmanager.locker.adapter.in.res.LockerReserveResponse;
import com.ime.lockmanager.locker.application.service.LockerService;
import com.ime.lockmanager.locker.application.service.RedissonLockLockerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/locker")
public class LockerController {

    private final RedissonLockLockerFacade redissonLockLockerFacade;
    private final LockerService lockerService;

    //사물함 예약하는 api
    @PostMapping("/register")
    public LockerRegisterResponse registerLocker(Principal principal, @RequestBody LockerRegisterRequest lockerRegisterRequest) throws Exception {
        return LockerRegisterResponse.fromResponse(redissonLockLockerFacade.register(lockerRegisterRequest.toRequestDto(principal.getName())));
    }

    //예약된 사물함 가져오기
    @GetMapping("/reserved")
    public LockerReserveResponse findReservedLocker(){
        return LockerReserveResponse.fromResponse(lockerService.findReserveLocker());
    }
}
