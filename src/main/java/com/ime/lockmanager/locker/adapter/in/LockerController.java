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

//    private final RedissonLockLockerFacade redissonLockLockerFacade;
    private final LockerService lockerService;

    @PostMapping("/register")
    public LockerRegisterResponse registerLocker(Principal principal, @RequestBody LockerRegisterRequest lockerRegisterRequest) throws Exception {
        return LockerRegisterResponse.fromResponse(/*redissonLockLockerFacade*/lockerService.register(lockerRegisterRequest.toRequestDto(principal.getName())));
    }

    @GetMapping()
    public LockerReserveResponse findReservedLocker(){
        return LockerReserveResponse.fromResponse(lockerService.findReserveLocker());
    }
}
