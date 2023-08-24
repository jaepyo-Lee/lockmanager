package com.ime.lockmanager.locker.adapter.in.res;

import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LockerRegisterResponse {
    private Long lockerNum;
    private String studentNum;
    public static LockerRegisterResponse fromResponse(LockerRegisterResponseDto lockerRegisterResponseDto) {
        return LockerRegisterResponse.builder()
                .lockerNum(lockerRegisterResponseDto.getLockerNum())
                .studentNum(lockerRegisterResponseDto.getStudentNum())
                .build();
    }
}
