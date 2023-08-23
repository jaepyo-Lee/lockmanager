package com.ime.lockmanager.user.application.port.out.res;

import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserInfoForMyPageResponseDto {
    private String name;
    private boolean member_ship;
    private String status;
    private String student_num;

    private Long locker_id;

    public UserInfoForMyPageResponseDto(String name, boolean member_ship, String status, String student_num, Long locker_id) {
        this.name = name;
        this.member_ship = member_ship;
        this.status = status;
        this.student_num = student_num;
        if(locker_id!=null){
            this.locker_id = locker_id;
        }else {
            this.locker_id = null;
        }
    }

    public UserInfoResponseDto to(){
        return UserInfoResponseDto.builder()
                .lockerNum(locker_id)
                .membership(member_ship)
                .userName(name)
                .status(status)
                .studentNum(student_num)
                .build();
    }

}
