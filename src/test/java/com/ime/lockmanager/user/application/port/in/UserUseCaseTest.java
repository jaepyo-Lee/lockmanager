package com.ime.lockmanager.user.application.port.in;

import com.ime.lockmanager.common.format.exception.locker.InvalidCancelLockerException;
import com.ime.lockmanager.common.format.exception.user.NotFoundUserException;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.locker.domain.Period;
import com.ime.lockmanager.reservation.application.port.in.ReservationUseCase;
import com.ime.lockmanager.reservation.application.port.out.ReservationQueryPort;
import com.ime.lockmanager.reservation.application.service.RedissonLockReservationFacade;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.application.port.in.req.ModifiedUserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserCancelLockerRequestDto;
import com.ime.lockmanager.user.application.port.in.req.UserInfoRequestDto;
import com.ime.lockmanager.user.application.port.in.res.UserInfoResponseDto;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class UserUseCaseTest {

    @Autowired
    private UserQueryPort userQueryPort;
    @Autowired
    private UserUseCase userUseCase;
    @Autowired
    private LockerQueryPort lockerQueryPort;
    @Autowired
    private RedissonLockReservationFacade redissonLockReservationFacade;
    @Autowired
    private ReservationQueryPort reservationQueryPort;
    @Autowired
    private ReservationUseCase reservationUseCase;
    @BeforeEach
    void tearDown() {
        reservationQueryPort.deleteAll();
        userQueryPort.deleteAll();
        lockerQueryPort.deleteAll();
    }

//    boolean checkAdmin(String studentNum);
    @DisplayName("사용자의 권한이 관리자일때 권한을 체크하는 메서드")
    @Test
    void checkAdminWithAdminUserTest(){
        //given
        userQueryPort.save(getUserSetAdmin("test", "재학", "19011721", Role.ROLE_ADMIN));
        //when
        //then
        assertThat(userUseCase.checkAdmin("19011721")).isTrue();
    }

    @DisplayName("사용자의 권한이 관리자가 아닐때 권한 체크하는 메서드")
    @Test
    void checkAdminWithoutAdminUserTest(){
        //given
        userQueryPort.save(getUserSetAdmin("test", "재학", "19011721", Role.ROLE_USER));
        //when
        //then
        assertThat(userUseCase.checkAdmin("19011721")).isFalse();
    }



//    Page<UserInfoResponseDto> findAllUserInfo(Pageable pageable);
    @DisplayName("모든 사용자의 정보가 1페이지를 넘지 않을때 모든 사용자의 정보 조회 테스트")
    @Test
    void findAllUserInfoInOnePageTest(){
        //given
        userQueryPort.save(getUser("이재표", "재학", "19011721"));
        userQueryPort.save(getUser("이재표1", "재학", "19011722"));
        userQueryPort.save(getUser("이재표2", "재학", "19011723"));
        PageRequest pageRequest = PageRequest.of(0, 5);
        //when
        Page<UserInfoResponseDto> allUserInfo = userUseCase.findAllUserInfo(pageRequest);
        //then
        assertThat(allUserInfo.stream().count()).isEqualTo(3);
    }

    @DisplayName("모든 사용자의 정보가 1페이지를 넘을때 모든 사용자의 정보 조회 테스트")
    @Test
    void findAllUserInfoInMorePageTest(){
        //given
        userQueryPort.save(getUser("이재표", "재학", "19011721"));
        userQueryPort.save(getUser("이재표1", "재학", "19011722"));
        userQueryPort.save(getUser("이재표2", "재학", "19011723"));
        userQueryPort.save(getUser("이재표3", "재학", "19011724"));
        userQueryPort.save(getUser("이재표4", "재학", "19011725"));
        userQueryPort.save(getUser("이재표5", "재학", "19011726"));
        userQueryPort.save(getUser("이재표6", "재학", "19011727"));
        userQueryPort.save(getUser("이재표7", "재학", "19011728"));
        PageRequest pageRequest1 = PageRequest.of(0, 5);
        PageRequest pageRequest2 = PageRequest.of(1, 5);

        //when
        Page<UserInfoResponseDto> allUserInfo = userUseCase.findAllUserInfo(pageRequest1);
        Page<UserInfoResponseDto> allUserInfo1 = userUseCase.findAllUserInfo(pageRequest2);
        //then
        assertAll(()->assertThat(allUserInfo.stream().count()).isEqualTo(5),
                ()->assertThat(allUserInfo1.stream().count()).isEqualTo(3));
    }

    @DisplayName("사용자가 없을때 모든 사용자의 정보 조회 테스트")
    @Test
    void findAllUserInfoWithNoUserTest(){
        //given

        PageRequest pageRequest1 = PageRequest.of(0, 5);

        //when
        Page<UserInfoResponseDto> allUserInfo = userUseCase.findAllUserInfo(pageRequest1);
        //then
        assertThat(allUserInfo.stream().count()).isEqualTo(0);
    }


//    void cancelLocker(UserCancelLockerRequestDto cancelLockerDto);
    @DisplayName("해당 학번의 학생이 가진 사물함을 취소하기 테스트")
    @Test
    void cancelLockerByStudentNumWithUserHaveLockerTest() throws Exception {
        //given
        userQueryPort.save(getUser("이재표", "재학", "19011721"));
        lockerQueryPort.save(getLocker(1L));
        reservationUseCase.registerForUser(LockerRegisterRequestDto.builder()
                .studentNum("19011721")
                .lockerDetailId(1L)
                .build());
        UserCancelLockerRequestDto cancelLockerRequestDto = UserCancelLockerRequestDto.builder()
                .studentNum("19011721")
                .build();

        //when
        userUseCase.cancelLockerByStudentNum(cancelLockerRequestDto);
        //then
        User byStudentNum = userQueryPort.findByStudentNum("19011721").orElseThrow(NotFoundUserException::new);
        assertAll(()->assertThat(byStudentNum.getLocker()).isNull(),
                ()->assertThat(byStudentNum.getStudentNum()).isEqualTo("19011721"));
    }

    @DisplayName("해당 학번의 사물함이 없는 학생의 사물함 취소하기 테스트")
    @Test
    void cancelLockerByStudentNumWithUserNotHaveLockerTest() throws Exception {
        //given
        userQueryPort.save(getUser("이재표", "재학", "19011721"));
        UserCancelLockerRequestDto cancelLockerRequestDto = UserCancelLockerRequestDto.builder()
                .studentNum("19011721")
                .build();

        //when
        //then
        assertThatThrownBy(()->userUseCase.cancelLockerByStudentNum(cancelLockerRequestDto)).isInstanceOf(InvalidCancelLockerException.class) ;

    }

    //    UserInfoResponseDto findUserInfo(UserInfoRequestDto userRequestDto);
    @DisplayName("학번을 통해 유저를 찾을때(유저가 존재할때)")
    @Test
    void findUserWithOne(){
        //given
        User user = getUser("이재표", "재학", "19011721");
        userQueryPort.save(user);
        //when
        UserInfoResponseDto userInfo = userUseCase.findUserInfoByStudentNum(UserInfoRequestDto.builder()
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
        assertThatThrownBy(() -> userUseCase.findUserInfoByStudentNum(UserInfoRequestDto.builder()
                .build())).isInstanceOf(NotFoundUserException.class);
    }




//    void modifiedUserInfo(List<ModifiedUserInfoRequestDto> requestDto) throws Exception;
    @DisplayName("사물함을 예약하지 않은 유저의 사물함 정보 수정")
    @Test
    void modifiedUserWithoutLockerTest() throws Exception {
        //given
        userQueryPort.save(getUser("이재표", "재학", "19011721"));
        lockerQueryPort.save(getLocker(1L));
        ModifiedUserInfoRequestDto modifiedUserInfoRequestDto = getModifiedUserInfoRequestDto(Role.ROLE_USER, true, "19011721", "1");
        //when
        userUseCase.modifiedUserInfo(List.of(modifiedUserInfoRequestDto));
        //then
        Reservation reservationByStudentNum = reservationQueryPort.findReservationByStudentNum("19011721");
        Assertions.assertAll(
                ()->assertThat(reservationByStudentNum.getUser().getStudentNum()).isEqualTo("19011721"),
                ()->assertThat(reservationByStudentNum.getLocker().getId()).isEqualTo(1)
        );
    }

    @DisplayName("사물함을 예약한 유저의 사물함 정보 수정")
    @Test
    void modifiedUserWithLockerTest() throws Exception {
        //given
        userQueryPort.save(getUser("이재표", "재학", "19011721"));
        lockerQueryPort.save(getLocker(1L));
        lockerQueryPort.save(getLocker(2L));
        reservationUseCase.registerForUser(LockerRegisterRequestDto.builder()
                .studentNum("19011721")
                .lockerDetailId(1L)
                .build());
        ModifiedUserInfoRequestDto modifiedUserInfoRequestDto = getModifiedUserInfoRequestDto(Role.ROLE_USER, true, "19011721", "2");
        //when
        userUseCase.modifiedUserInfo(List.of(modifiedUserInfoRequestDto));
        //then
        User byStudentNum = userQueryPort.findByStudentNum("19011721").orElseThrow(NotFoundUserException::new);
        Reservation reservationByStudentNum = reservationQueryPort.findReservationByStudentNum("19011721");
        Assertions.assertAll(
                () -> assertThat(byStudentNum.getStudentNum()).isEqualTo("19011721"),
                () -> assertThat(reservationByStudentNum.getLocker().getId()).isEqualTo(2L)
        );
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

    private static User getUserSetAdmin(String name, String status, String studentNum, Role roleAdmin) {
        return User.builder()
                .name(name)
                .role(roleAdmin)
                .status(status)
                .studentNum(studentNum)
                .membership(true)
                .build();
    }

    private static Locker getLocker(long lockerId,User user) {
        return Locker.builder()
                .usable(true)
                .period(Period.builder()
                        .endDateTime(LocalDateTime.now().plusDays(1))
                        .startDateTime(LocalDateTime.now().minusDays(1))
                        .build())
                .id(lockerId)
                .user(user)
                .build();
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