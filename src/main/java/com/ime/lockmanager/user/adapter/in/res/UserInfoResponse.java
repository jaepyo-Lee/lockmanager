package com.ime.lockmanager.user.adapter.in.res;

import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class UserInfoResponse {
    private String userName;
    private String userNum;
    private boolean membership;
    private Long lockerNum;
    public static UserInfoResponse fromResponse(UserInfoResponseDto userInfoResponseDto) {
        return UserInfoResponse.builder()
                .lockerNum(userInfoResponseDto.getLockerNum())
                .userNum(userInfoResponseDto.getUserNum())
                .userName(userInfoResponseDto.getUserName())
                .membership(userInfoResponseDto.isMembership())
                .build();
    }
}
