package com.ime.lockmanager.locker.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.locker.adapter.in.req.LockerSetTimeRequest;
import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
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
public class LockerAdminController {
    private final LockerUseCase lockerUseCase;

    @PostMapping("/time")
    public SuccessResponse setPeriod(HttpServletRequest req, @RequestBody LockerSetTimeRequest timeRequest){
        lockerUseCase.setLockerPeriod(timeRequest.toRequestDto());
        return new SuccessResponse("시간설정완료");
    }

    @PostMapping("/reset")
    public SuccessResponse resetLocker(){
        lockerUseCase.initLockerInfo();
        return SuccessResponse.ok();
    }
}
