package com.ime.lockmanager.reservation.adapter.in.req;

import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import lombok.Getter;

@Getter
public class LockerRegisterRequest {
    //사물함 번호
    private Long lockerId;

    public LockerRegisterRequestDto toRequestDto(String studentNum){
        return LockerRegisterRequestDto.builder()
                .lockerNum(lockerId)
                .studentNum(studentNum)
                .build();
    }
}
