package com.ime.lockmanager.adminlist.application.port.in;

import com.ime.lockmanager.adminlist.application.port.in.res.AdminListResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.locker.domain.Period;
import com.ime.lockmanager.reservation.application.port.out.ReservationQueryPort;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AdminListUseCaseTest {
    @Autowired
    private AdminListUseCase adminListUseCase;

    @Autowired
    private LockerQueryPort lockerQueryPort;
    @Autowired
    private ReservationQueryPort reservationQueryPort;
    @Autowired
    private UserQueryPort userQueryPort;

    @BeforeEach
    void tearDown() {
        reservationQueryPort.deleteAll();
        userQueryPort.deleteAll();
        lockerQueryPort.deleteAll();
    }


    @DisplayName("관리자페이지의 각 목록 리스트의 정보 받기(사물함 예약이 안되었을때)")
    @Test
    void getAdminListUseCaseWithoutReserve(){
        //given
        lockerQueryPort.save(Locker.builder()
                .period(Period.builder()
                        .startDateTime(LocalDateTime.now())
                        .endDateTime(LocalDateTime.now())
                        .build())
                .id(1L)
                .build());
        //when
        AdminListResponseDto adminList = adminListUseCase.getAdminList();
        //then
        assertThat(adminList.getLockerIdList()).isEqualTo(List.of(1L));
        assertThat(adminList.getRoleList().containsAll(List.of(Role.ROLE_ADMIN, Role.ROLE_USER)));
        assertThat(adminList.getMemberShipList()).containsAll(List.of(true, false));
    }

    @Transactional
    @DisplayName("관리자페이지의 각 목록 리스트의 정보 받기(사물함 예약이 되었을때)")
    @Test
    void getAdminListUseCaseWithReserve() throws Exception {
/*        //given
        Locker locker = Locker.builder()
                .usable(false)
                .period(Period.builder()
                        .startDateTime(LocalDateTime.now())
                        .endDateTime(LocalDateTime.now())
                        .build())
                .user(null)
                .id(1L)
                .build();

        User user = User.builder()
                .name("이재표")
                .role(Role.ROLE_ADMIN)
                .status("재학")
                .studentNum("19011721")
                .locker(null)
                .membership(true)
                .build();

        Locker saveLocker = lockerQueryPort.save(locker);
        User saveUser = userQueryPort.save(user);

        saveUser.registerLocker(saveLocker);

        //when
        AdminListResponseDto adminList = adminListUseCase.getAdminList();
        //then
        assertThat(adminList.getLockerIdList()).isEqualTo(List.of());
        assertThat(adminList.getRoleList().containsAll(List.of(Role.ROLE_ADMIN, Role.ROLE_USER)));
        assertThat(adminList.getMemberShipList()).containsAll(List.of(true, false));*/
    }

    @DisplayName("관리자페이지의 각 목록 리스트의 정보 받기(어느 정보도 없을때)")
    @Test
    void test(){
        //given
        //when
        AdminListResponseDto adminList = adminListUseCase.getAdminList();
        //then
        assertThat(adminList.getLockerIdList()).isEqualTo(List.of());
        assertThat(adminList.getRoleList().containsAll(List.of(Role.ROLE_ADMIN, Role.ROLE_USER)));
        assertThat(adminList.getMemberShipList()).containsAll(List.of(true, false));
    }
}