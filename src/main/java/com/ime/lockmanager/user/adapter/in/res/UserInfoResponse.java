package com.ime.lockmanager.user.adapter.in.res;

import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {
    private String userName;
    private String userNum;
    private boolean membership;
    private Long lockerNum;
    public static UserInfoResponse fromResponseDto(UserInfoResponseDto userInfoResponseDto) {
        return UserInfoResponse.builder()
                .lockerNum(userInfoResponseDto.getLockerNum())
                .userNum(userInfoResponseDto.getStudentNum())
                .userName(userInfoResponseDto.getUserName())
                .membership(userInfoResponseDto.isMembership())
                .build();
    }
}
