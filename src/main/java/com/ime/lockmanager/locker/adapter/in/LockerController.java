package com.ime.lockmanager.locker.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.locker.adapter.in.res.LockersInfoInMajorResponse;
import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import com.ime.lockmanager.locker.application.port.in.req.FindAllLockerInMajorRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;


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
