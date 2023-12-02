package com.ime.lockmanager.user.application.port.out.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserInfoResponseDto {
    private String name;
    private boolean member_ship;
    private String status;
    private String student_num;

    private String locker_num;
    private String locker_name;
    private String majorDetail;

    public UserInfoResponseDto(String name, boolean member_ship, String status, String student_num, String locker_num, String locker_name, String majorDetail) {
        this.name = name;
        this.member_ship = member_ship;
        this.status = status;
        this.student_num = student_num;
        this.majorDetail = majorDetail;
        if(locker_num!=null){
            this.locker_num = locker_num;
            this.locker_name = locker_name;
        }else {
            this.locker_num = null;
            this.locker_name = null;
        }
    }

    public com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto to(){
        return com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto.builder()
                .lockerNum(locker_num)
                .membership(member_ship)
                .userName(name)
                .status(status)
                .studentNum(student_num)
                .majorDetail(majorDetail)
                .build();
    }

}
