package com.ime.lockmanager.user.application.port.out.res;

import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import com.ime.lockmanager.user.domain.MembershipState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Getter
public class UserInfoQueryResponseDto {
    private String name;
    private MembershipState membershipState;
    private String status;
    private String studentNum;

    private String lockerNum;
    private String lockerName;
    private String majorDetail;

    public UserInfoQueryResponseDto(String name, MembershipState membershipState, String status, String studentNum, String lockerNum, String lockerName, String majorDetail) {
        this.name = name;
        this.membershipState = membershipState;
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

    public UserInfoQueryResponseDto to(){
        return UserInfoQueryResponseDto.builder()
                .lockerNum(lockerNum)
                .membershipState(membershipState)
                .name(name)
                .status(status)
                .studentNum(studentNum)
                .majorDetail(majorDetail)
                .build();
    }

}
