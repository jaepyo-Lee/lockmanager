package com.ime.lockmanager.locker.application.port.in.req;

import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LockerRegisterRequestDto {
    private Long userId;
    private Long lockerDetailId;

    public static LockerRegisterRequestDto of(Long userId,Long lockerDetailId){
        return LockerRegisterRequestDto.builder()
                .userId(userId)
                .lockerDetailId(lockerDetailId)
                .build();
    }
}
