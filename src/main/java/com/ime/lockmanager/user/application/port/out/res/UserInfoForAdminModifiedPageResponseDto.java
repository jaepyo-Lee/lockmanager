package com.ime.lockmanager.user.application.port.out.res;

import com.ime.lockmanager.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
public class UserInfoForAdminModifiedPageResponseDto {
    private String name;
    private boolean member_ship;
    private String status;
    private String student_num;
    private Long locker_id;
    private Role role;

    public UserInfoForAdminModifiedPageResponseDto(String name, boolean member_ship, String status, String student_num, Long locker_id,Role role) {
        this.name = name;
        this.member_ship = member_ship;
        this.status = status;
        this.student_num = student_num;
        this.role = role;
        if(locker_id!=null){
            this.locker_id = locker_id;
        }else {
            this.locker_id = null;
        }
    }

    public UserInfoForAdminModifiedPageResponse toResponse(){
        return UserInfoForAdminModifiedPageResponse.builder()
                .lockerNum(locker_id)
                .membership(member_ship)
                .studentNum(student_num)
                .userName(name)
                .role(role)
                .status(status)
                .build();
    }
}
