package com.ime.lockmanager.reservation.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.locker.adapter.in.res.LockerReserveResponse;
import com.ime.lockmanager.reservation.adapter.in.req.LockerRegisterRequest;
import com.ime.lockmanager.locker.adapter.in.res.LockerRegisterResponse;
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
@RequestMapping("/api/reservation")
public class ReservationController {
    private final RedissonLockReservationFacade redissonLockReservationFacade;
    private final ReservationUseCase reservationUseCase;

    @ApiOperation(
            value = "사물함 취소",
            notes = "현재 사용자의 예약된 사물함을 취소하는 API"
    )
    @DeleteMapping
    public SuccessResponse cancelLocker(@ApiIgnore Principal principal){
        log.info("{} : 사물함 취소",principal.getName());
        reservationUseCase.cancelLockerByStudentNum(
                UserCancelLockerRequestDto.builder()
                        .studentNum(principal.getName())
                        .build()
        );
        return SuccessResponse.ok();
    }

    //사물함 예약하는 api
    @ApiOperation(
            value = "사물함 예약",
            notes = "사용자가 사물함을 선택할시 해당 사물함을 예약하는 API"
    )
    @PostMapping("/register")
    public SuccessResponse registerLocker(@ApiIgnore Principal principal, @RequestBody LockerRegisterRequest lockerRegisterRequest) throws Exception {
        log.info("{} : 시믈함 예약",principal.getName());
        return new SuccessResponse(LockerRegisterResponse.fromResponse(redissonLockReservationFacade.registerForUser(lockerRegisterRequest.toRequestDto(principal.getName()))));
    }

    //예약된 사물함 가져오기
    @ApiOperation(
            value = "예약된 사물함 조회",
            notes = "예약된 사물함을 조회하는 API(화면 표시용 api)"
    )
    @GetMapping("/reserved")
    public SuccessResponse findReservedLocker(){
        return new SuccessResponse(LockerReserveResponse.fromResponse(reservationUseCase.findReservedLockers()));
    }
}
