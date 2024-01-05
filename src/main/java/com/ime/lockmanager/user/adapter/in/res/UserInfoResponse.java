package com.ime.lockmanager.user.adapter.in.res;

import com.ime.lockmanager.user.application.port.out.res.UserInfoQueryResponseDto;
import com.ime.lockmanager.user.domain.UserState;
import com.ime.lockmanager.user.domain.UserTier;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {

    private String userName;
    private String studentNum;
    private UserTier userTier;
    private UserState userState;
    private String reservedLockerNum;
    private String reservedLockerName;
    private String majorDetail;
    public static UserInfoResponse fromResponseDto(UserInfoQueryResponseDto userInfoQueryResponseDto) {
        return UserInfoResponse.builder()
                .reservedLockerNum(userInfoQueryResponseDto.getLockerNum())
                .studentNum(userInfoQueryResponseDto.getStudentNum())
                .userName(userInfoQueryResponseDto.getName())
                .majorDetail(userInfoQueryResponseDto.getMajorDetail())
                .reservedLockerName(userInfoQueryResponseDto.getLockerName())
                .userTier(userInfoQueryResponseDto.getUserTier())
                .userState(userInfoQueryResponseDto.getUserState())
                .build();
    }
}
