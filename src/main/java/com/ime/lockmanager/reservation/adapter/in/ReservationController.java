package com.ime.lockmanager.reservation.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.locker.adapter.in.res.LockerReserveResponse;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.adapter.in.res.LockerRegisterResponse;
import com.ime.lockmanager.reservation.adapter.in.res.CancelLockerDetailResponse;
import com.ime.lockmanager.reservation.application.port.in.ReservationUseCase;
import com.ime.lockmanager.reservation.application.service.RedissonLockReservationFacade;
import com.ime.lockmanager.user.application.port.in.req.UserCancelLockerRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.user.prefix}/users/{userId}")
public class ReservationController {
    private final RedissonLockReservationFacade redissonLockReservationFacade;
    private final ReservationUseCase reservationUseCase;

    @ApiOperation(
            value = "사물함 취소",
            notes = "현재 사용자의 예약된 사물함을 취소하는 API"
    )
    @PatchMapping("/lockerDetail/{lockerDetailId}/reservations")
    public SuccessResponse<CancelLockerDetailResponse> cancelLocker(@ApiIgnore Principal principal,
                                                                    @PathVariable Long userId,
                                                                    @PathVariable Long lockerDetailId) {
        log.info("{} : 사물함 취소", principal.getName());
        reservationUseCase.cancelLockerByStudentNum(
                UserCancelLockerRequestDto.of(userId, lockerDetailId)
        );
        return new SuccessResponse(CancelLockerDetailResponse.builder()
                .canceledLockerDetailNum(lockerDetailId)
                .studentNum(principal.getName()).build());
    }

    //사물함 예약하는 api
    @ApiOperation(
            value = "사물함 예약",
            notes = "사용자가 사물함을 선택할시 해당 사물함을 예약하는 API"
    )
    @PostMapping("/lockerDetail/{lockerDetailId}/reservations")
    public SuccessResponse<LockerRegisterResponse> registerLocker(@ApiIgnore Principal principal,
                                                                  @PathVariable Long userId,
                                                                  @PathVariable Long lockerDetailId) throws Exception {
        log.info("{} : 시믈함 예약진행", principal.getName());
        LockerRegisterResponse lockerRegisterResponse =
                LockerRegisterResponse.fromResponse(
                        redissonLockReservationFacade.registerForUser(
                                LockerRegisterRequestDto.of(userId, lockerDetailId))
                );
        log.info("{} : {}의 {}번 예약완료",
                lockerRegisterResponse.getStudentNum(),
                lockerRegisterResponse.getLockerName(),
                lockerRegisterResponse.getLockerDetailNum());
        return new SuccessResponse(lockerRegisterResponse);
    }

    //예약된 사물함 가져오기
    @Deprecated
    @ApiOperation(
            value = "예약된 사물함 조회",
            notes = "예약된 사물함을 조회하는 API(화면 표시용 api)"
    )
    @GetMapping("/reserved")
    public SuccessResponse findReservedLocker() {
        return new SuccessResponse(LockerReserveResponse.fromResponse(reservationUseCase.findReservedLockers()));
    }
}
