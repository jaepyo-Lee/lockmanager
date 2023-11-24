package com.ime.lockmanager.user.adapter.in.res;

import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {
    private String userName;
    private String studentNum;
    private boolean membership;
    private Long lockerNum;
    private String lockerName;
    private String majorDetail;
    public static UserInfoResponse fromResponseDto(UserInfoResponseDto userInfoResponseDto) {
        return UserInfoResponse.builder()
                .lockerNum(userInfoResponseDto.getLockerNum())
                .studentNum(userInfoResponseDto.getStudentNum())
                .userName(userInfoResponseDto.getUserName())
                .membership(userInfoResponseDto.isMembership())
                .build();
    }
}
