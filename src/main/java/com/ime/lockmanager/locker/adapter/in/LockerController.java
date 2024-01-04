package com.ime.lockmanager.locker.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.locker.adapter.in.res.AllLockersInMajorResponse;
import com.ime.lockmanager.locker.adapter.in.res.LockerPeriodResponse;
import com.ime.lockmanager.locker.adapter.in.res.LockerReserveResponse;
import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import com.ime.lockmanager.locker.application.port.in.req.FindAllLockerInMajorRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.AllLockersInMajorResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static java.time.LocalDateTime.now;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.user.prefix}/locker")
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


    @Deprecated
    @ApiOperation(
            value = "현재시간 조회",
            notes = "서버의 현재시간을 조회하는 API(위치 및 URL변경될지도모름)"
    )
    @GetMapping("/livetime")
    public SuccessResponse getLiveTime() {
        return new SuccessResponse(now());
    }

    @GetMapping("")
    public SuccessResponse<List<AllLockersInMajorResponse>> findAllLockerInMajor(@ApiIgnore Authentication authentication) {
        return new SuccessResponse(lockerUseCase.findAllLockerInMajor(FindAllLockerInMajorRequestDto.builder()
                .studentNum(authentication.getName()).build()));
    }
}
