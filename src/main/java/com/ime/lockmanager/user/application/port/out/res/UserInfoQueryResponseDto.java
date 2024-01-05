package com.ime.lockmanager.user.application.port.out.res;

import com.ime.lockmanager.user.domain.MembershipState;
import com.ime.lockmanager.user.domain.UserState;
import com.ime.lockmanager.user.domain.UserTier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserInfoQueryResponseDto {
    private String name;
    private UserTier userTier;
    private UserState userState;
    private String status;
    private String studentNum;

    private String lockerNum;
    private String lockerName;
    private String majorDetail;
    @Builder
    public UserInfoQueryResponseDto(String name, UserTier userTier, String status, String studentNum, String lockerNum, String lockerName, String majorDetail,UserState userState) {
        this.userState = userState;
        this.name = name;
        this.userTier = userTier;
        this.status = status;
        this.studentNum = studentNum;
        this.majorDetail = majorDetail;
        if(lockerNum !=null){
            this.lockerNum = lockerNum;
            this.lockerName = lockerName;
        }else {
            this.lockerNum = null;
            this.lockerName = null;
        }
    }
}
