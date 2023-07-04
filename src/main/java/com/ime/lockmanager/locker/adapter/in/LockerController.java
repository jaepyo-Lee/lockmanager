package com.ime.lockmanager.locker.adapter.in;

import com.ime.lockmanager.locker.adapter.in.req.LockerRegisterRequest;
import com.ime.lockmanager.locker.adapter.in.res.LockerRegisterResponse;
import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/locker")
public class LockerController {

    private final LockerUseCase lockerUseCase;

    @PostMapping("/register")
    public LockerRegisterResponse registerLocker(Principal principal, @RequestBody LockerRegisterRequest lockerRegisterRequest) throws Exception {
        return LockerRegisterResponse.fromResponse(lockerUseCase.register(lockerRegisterRequest.toRequestDto(principal.getName())));
    }
}
