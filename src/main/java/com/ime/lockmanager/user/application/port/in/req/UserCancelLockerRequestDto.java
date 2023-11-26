package com.ime.lockmanager.user.application.port.in.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCancelLockerRequestDto {
    private String studentNum;
    private Long lockerDetailId;

    public static UserCancelLockerRequestDto of(String studentNum,Long lockerDetailId){
        return UserCancelLockerRequestDto.builder()
                .studentNum(studentNum)
                .lockerDetailId(lockerDetailId)
                .build();
    }
}
