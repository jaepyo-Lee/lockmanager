package com.ime.lockmanager.locker.application.port.in.req;

import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LockerRegisterRequestDto {
    private String studentNum;
    private Long lockerDetailId;

    public static LockerRegisterRequestDto of(String studentNum,Long lockerDetailId){
        return LockerRegisterRequestDto.builder()
                .studentNum(studentNum)
                .lockerDetailId(lockerDetailId)
                .build();
    }
}
