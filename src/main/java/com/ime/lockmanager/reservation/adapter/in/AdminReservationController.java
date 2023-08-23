package com.ime.lockmanager.reservation.adapter.in;

import com.ime.lockmanager.common.format.success.SuccessResponse;
import com.ime.lockmanager.reservation.application.port.in.ReservationUseCase;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/api/resevation")
public class AdminReservationController {
    private final ReservationUseCase reservationUseCase;
    @ApiOperation(
            value = "사물함 정보 전체 삭제",
            notes = "사물함 초기화(eg. 학기초 및 롤백)의 경우 예약된 모든 사물함을 초기화하는 API"
    )
    @PostMapping("/reset")
    public SuccessResponse resetLocker(Principal principal){
        reservationUseCase.resetReservation(principal);
        return SuccessResponse.ok("초기화가 완료되었습니다.");
    }
}