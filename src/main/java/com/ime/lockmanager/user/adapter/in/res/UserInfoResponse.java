package com.ime.lockmanager.user.adapter.in.res;

import com.ime.lockmanager.user.application.port.out.res.UserInfoResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {
    private String userName;
    private String studentNum;
    private boolean membership;
    private String reservedLockerNum;
    private String reservedLockerName;
    private String majorDetail;
    public static UserInfoResponse fromResponseDto(UserInfoResponseDto userInfoResponseDto) {
        return UserInfoResponse.builder()
                .reservedLockerNum(userInfoResponseDto.getLockerNum())
                .studentNum(userInfoResponseDto.getStudentNum())
                .userName(userInfoResponseDto.getName())
                .membership(userInfoResponseDto.isMembership())
                .majorDetail(userInfoResponseDto.getMajorDetail())
                .reservedLockerName(userInfoResponseDto.getLockerName())
                .build();
    }
}
