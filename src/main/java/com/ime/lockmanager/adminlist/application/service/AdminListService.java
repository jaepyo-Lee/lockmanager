package com.ime.lockmanager.adminlist.application.service;

import com.ime.lockmanager.adminlist.application.port.in.AdminListUseCase;
import com.ime.lockmanager.adminlist.application.port.in.res.AdminListResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.user.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminListService implements AdminListUseCase {
    private final LockerQueryPort lockerQueryPort;
    @Override
    public AdminListResponseDto getAdminList() {
        List<Long> notReservedLockerId = lockerQueryPort.findNotReservedLockerId();

        List<Boolean> memberShipList = new ArrayList<>();
        memberShipList.add(true);
        memberShipList.add(false);

        List<Role> roleList=new ArrayList<>();
        roleList.add(Role.ROLE_ADMIN);
        roleList.add(Role.ROLE_USER);

        return AdminListResponseDto.builder()
                .lockerIdList(notReservedLockerId)
                .memberShipList(memberShipList)
                .roleList(roleList)
                .build();
    }
}
