package com.ime.lockmanager.user.application.port.in;

import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.locker.domain.Period;
import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class UserUseCaseTest {
    /*UserInfoResponseDto findUserInfo(UserInfoRequestDto userRequestDto);

    Page<UserInfoResponseDto> findAllUserInfo(Pageable pageable);

    boolean checkAdmin(String studentNum);

    void cancelLocker(UserCancelLockerRequestDto cancelLockerDto);

    */

    @Autowired
    private UserQueryPort userQueryPort;
    @Autowired
    private UserUseCase userUseCase;
    @Autowired
    private LockerQueryPort lockerQueryPort;
    @BeforeEach
    void tearDown() {
        userQueryPort.deleteAll();
        lockerQueryPort.deleteAll();
    }

    @DisplayName("유저를 찾을때(유저가 존재할때)")
    @Test
    void findUserWithOne(){
        //given
        User user = getUser("이재표", "재학", "19011721");
        userQueryPort.save(user);
        //when
        UserInfoResponseDto userInfo = userUseCase.findUserInfo(UserInfoRequestDto.builder()
                .studentNum(user.getStudentNum())
                .build());
        //then
        assertAll(
                () -> assertThat(userInfo.getUserName()).isEqualTo(user.getName()),
                () -> assertThat(userInfo.getStatus()).isEqualTo(user.getStatus()),
                () -> assertThat(userInfo.getStudentNum()).isEqualTo(user.getStudentNum())
        );
    }

    @DisplayName("유저를 찾을때(유저가 존재하지 않을때)")
    @Test
    void findUserWithout(){
        //given
        //when
        //then
        assertThatThrownBy(() -> userUseCase.findUserInfo(UserInfoRequestDto.builder()
                .build())).isInstanceOf(RuntimeException.class);
    }


    private static User getUser(String name, String status, String studentNum) {
        return User.builder()
                .name(name)
                .role(Role.ROLE_ADMIN)
                .status(status)
                .studentNum(studentNum)
                .membership(true)
                .build();
    }

//    void modifiedUserInfo(List<ModifiedUserInfoRequestDto> requestDto) throws Exception;
    @DisplayName("유저 정보 수정")
    @Test
    void test() throws Exception {
        //given
        userQueryPort.save(getUser("이재표", "재학", "19011721"));
        lockerQueryPort.save(getLocker(1L));
        ModifiedUserInfoRequestDto modifiedUserInfoRequestDto = getModifiedUserInfoRequestDto(Role.ROLE_USER, true, "19011721", "1");
        //when
        userUseCase.modifiedUserInfo(List.of(modifiedUserInfoRequestDto));
        //then
        User byStudentNum = userQueryPort.findByStudentNum("19011721").orElseThrow(NotFoundUserException::new);
        Assertions.assertAll(
                ()->assertThat(byStudentNum.getStudentNum()).isEqualTo("19011721"),
                ()->assertThat(byStudentNum.getLocker().getId()).isEqualTo(1)
        );
    }

    private static Locker getLocker(long lockerId) {
        return Locker.builder()
                .usable(true)
                .period(Period.builder()
                        .endDateTime(LocalDateTime.now().plusDays(1))
                        .startDateTime(LocalDateTime.now().minusDays(1))
                        .build())
                .id(lockerId)
                .user(null)
                .build();
    }

    private static ModifiedUserInfoRequestDto getModifiedUserInfoRequestDto(Role roleUser, boolean membership, String studentNum, String lockerNumber) {
        return ModifiedUserInfoRequestDto.builder()
                .role(roleUser)
                .membership(membership)
                .studentNum(studentNum)
                .lockerNumber(lockerNumber)
                .build();
    }
}