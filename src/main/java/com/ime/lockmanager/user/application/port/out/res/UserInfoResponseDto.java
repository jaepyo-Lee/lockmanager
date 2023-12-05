package com.ime.lockmanager.user.application.port.out.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Getter
public class UserInfoResponseDto {
    private String name;
    private boolean membership;
    private String status;
    private String studentNum;

    private String lockerNum;
    private String lockerName;
    private String majorDetail;

    public UserInfoResponseDto(String name, boolean membership, String status, String studentNum, String lockerNum, String lockerName, String majorDetail) {
        this.name = name;
        this.membership = membership;
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

    public com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto to(){
        return com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto.builder()
                .lockerNum(lockerNum)
                .membership(membership)
                .userName(name)
                .status(status)
                .studentNum(studentNum)
                .majorDetail(majorDetail)
                .build();
    }

}
