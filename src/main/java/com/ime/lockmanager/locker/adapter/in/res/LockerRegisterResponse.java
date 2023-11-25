package com.ime.lockmanager.locker.adapter.in.res;

import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LockerRegisterResponse {
    private String lockerDetailNum;
    private String studentNum;
    private String lockerName;
    public static LockerRegisterResponse fromResponse(LockerRegisterResponseDto lockerRegisterResponseDto) {
        return LockerRegisterResponse.builder()
                .lockerDetailNum(lockerRegisterResponseDto.getLockerDetailNum())
                .lockerName(lockerRegisterResponseDto.getLockerName())
                .studentNum(lockerRegisterResponseDto.getStudentNum())
                .build();
    }
}
