package com.ime.lockmanager.adminlist.application.port.in.res;

import com.ime.lockmanager.user.domain.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AdminListResponseDto {
    List<Long> lockerIdList;
    List<Boolean> memberShipList;
    List<Role> roleList;

    @Builder
    public AdminListResponseDto(List<Long> lockerIdList, List<Boolean> memberShipList, List<Role> roleList) {
        this.lockerIdList = lockerIdList;
        this.memberShipList = memberShipList;
        this.roleList = roleList;
    }
}
