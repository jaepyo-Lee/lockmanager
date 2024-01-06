package com.ime.lockmanager.locker.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.locker.adapter.in.res.AllLockersInMajorResponse;
import com.ime.lockmanager.locker.adapter.in.res.LockerPeriodResponse;
import com.ime.lockmanager.locker.adapter.in.res.LockerReserveResponse;
import com.ime.lockmanager.locker.adapter.in.res.LockersInfoInMajorResponse;
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
@RequestMapping("${api.user.prefix}")
class LockerController {

    private final LockerUseCase lockerUseCase;

    @ApiOperation(
            value = "사물함 정보조회",
            notes = "사물함 이름, 기간, 각 사물함 칸의 예약여부정보"
    )
    @GetMapping("/users/{userId}/majors/lockers")
    public SuccessResponse<LockersInfoInMajorResponse> findAllLockerInMajor(@ApiIgnore Authentication authentication,
                                                                            @PathVariable Long userId) {
        return new SuccessResponse(lockerUseCase.findAllLockerInMajor(FindAllLockerInMajorRequestDto.builder()
                .userId(userId).build()));

    }
}
