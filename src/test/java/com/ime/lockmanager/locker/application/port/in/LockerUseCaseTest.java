package com.ime.lockmanager.locker.application.port.in;

import com.ime.lockmanager.common.format.exception.locker.AlreadyReservedLockerException;
import com.ime.lockmanager.common.format.exception.locker.IsNotReserveTimeException;
import com.ime.lockmanager.common.format.exception.user.AlreadyReservedUserException;
import com.ime.lockmanager.common.format.exception.user.InvalidReservedStatusException;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.req.LockerSetTimeRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerReserveResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.application.service.RedissonLockLockerFacade;
import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.locker.domain.Period;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class LockerUseCaseTest {
    @Autowired
    private LockerUseCase lockerUseCase;

    @Autowired
    private UserQueryPort userQueryPort;

    @Autowired
    private LockerQueryPort lockerQueryPort;

    @Autowired
    private RedissonLockLockerFacade redissonLockLockerFacade;

    @BeforeEach
    public void cleanUp(){
        userQueryPort.deleteAll();
        lockerQueryPort.deleteAll();
    }

//    LockerReserveResponseDto findReserveLocker();
    @DisplayName("예약된 사물함 조회테스트(모든 사물함을 예약했을때)")
    @Test
    void findReserveLockerWithFullTest() throws Exception {
        //given
        LocalDateTime start = now().minusDays(1);
        LocalDateTime end = now().plusDays(1);
        userQueryPort.save(getUser("test", "재학", "19011721"));
        userQueryPort.save(getUser("test2", "재학", "19011722"));
        lockerQueryPort.save(getLocker(1L, end, start));
        lockerQueryPort.save(getLocker(2L, end, start));
        lockerUseCase.register(LockerRegisterRequestDto.builder()
                .studentNum("19011721")
                .lockerNum(1L)
                .build());
        lockerUseCase.register(LockerRegisterRequestDto.builder()
                .studentNum("19011722")
                .lockerNum(2L)
                .build());
        //when
        LockerReserveResponseDto reserveLocker = lockerUseCase.findReserveLocker();
        //then
        assertThat(reserveLocker.getLockerIdList()).hasSize(2);
    }

    @DisplayName("예약된 사물함 조회테스트(예약되지 않은사물함이 존재할때)")
    @Test
    void findReserveLockerWithoutFullTest() throws Exception {
        //given
        LocalDateTime start = now().minusDays(1);
        LocalDateTime end = now().plusDays(1);
        userQueryPort.save(getUser("test", "재학", "19011721"));
        userQueryPort.save(getUser("test2", "재학", "19011722"));
        lockerQueryPort.save(getLocker(1L, end, start));
        lockerQueryPort.save(getLocker(2L, end, start));
        lockerUseCase.register(LockerRegisterRequestDto.builder()
                .studentNum("19011721")
                .lockerNum(1L)
                .build());
        //when
        LockerReserveResponseDto reserveLocker = lockerUseCase.findReserveLocker();
        //then
        assertThat(reserveLocker.getLockerIdList()).hasSize(1);
    }


//    LockerPeriodResponseDto getLockerPeriod();
    @DisplayName("사물함의 시간을 조회하는 메서드")
    @Test
    void getLockerPeriod(){
        //given
        LocalDateTime start = now().minusDays(1);
        LocalDateTime end = now().plusDays(1);
        lockerQueryPort.save(getLocker(1L, end,start));
        //when
        //then
        assertAll(
                () -> assertThat(
                        lockerUseCase.getLockerPeriod().getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .isEqualTo(start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))),
                () -> assertThat(
                        lockerUseCase.getLockerPeriod().getEndDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .isEqualTo(end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        );
    }



    @DisplayName("사물함 시간설정 테스트")
    @Test
    void setLockerPeriod(){
        //given
        lockerQueryPort.save(getLocker(1L,null,null));
        LocalDateTime start = now().minusDays(1);
        LocalDateTime end = now().plusDays(1);
        //when
        lockerUseCase.setLockerPeriod(LockerSetTimeRequestDto.builder()
                .startDateTime(start)
                .endDateTime(end)
                .build());
        //then
        assertAll(
                () -> assertThat(
                        lockerQueryPort.findByLockerId(1L).get().getPeriod().getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .isEqualTo(start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))),
                () -> assertThat(
                        lockerQueryPort.findByLockerId(1L).get().getPeriod().getEndDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .isEqualTo(end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        );
    }

    @DisplayName("배정된 사물함을 초기화")
    @Test
    void initLockerTest() throws Exception {
        //given
        userQueryPort.save(getUser("test", "재학", "test"));
        lockerQueryPort.save(getLocker(1L, now().plusDays(1), now().minusDays(1)));
        lockerUseCase.register(LockerRegisterRequestDto.builder()
                .lockerNum(1L)
                .studentNum("test")
                .build());
        //when
        lockerUseCase.initLockerInfo();
        //then
        List<Locker> all = lockerQueryPort.findAll();
        assertThat(all.stream().filter(locker -> locker.isUsable()==false).count()).isEqualTo(0);
    }

    @DisplayName("휴햑생이 사물함을 예약할때")
    @Test
    void reserveLockerWithLeaveStudent() throws Exception {
        //given
        User user = userQueryPort.save(getUser("이재표", "휴학", "19011721"));
        Locker locker = lockerQueryPort.save(
                getLocker(1L, now().plusDays(1), now().minusDays(1))
        );
        LockerRegisterRequestDto lockerRegisterRequestDto = getLockerRegisterRequestDto(user, locker);
        //when
        //then
        Assertions.assertThatThrownBy(() -> lockerUseCase.register(lockerRegisterRequestDto)).isInstanceOf(RuntimeException.class);
    }

    @DisplayName("졸업생이 사물함을 예약할때")
    @Test
    void reserveLockerWithGraduatedStudent() throws Exception {
        //given
        User user = userQueryPort.save(getUser("이재표", "졸업", "19011721"));
        Locker locker = lockerQueryPort.save(
                getLocker(1L, now().plusDays(1), now().minusDays(1))
        );
        LockerRegisterRequestDto lockerRegisterRequestDto = getLockerRegisterRequestDto(user, locker);
        //when
        //then
        Assertions.assertThatThrownBy(() -> lockerUseCase.register(lockerRegisterRequestDto)).isInstanceOf(InvalidReservedStatusException.class);
    }

    @DisplayName("예약시간이 되지 않았는데 예약을 진행할때")
    @Test
    void reserveLockerNotBetweenPeriod(){
        //given
        User user = userQueryPort.save(getUser("이재표", "재학", "19011721"));
        Locker locker = lockerQueryPort.save(
                getLocker(1L, now().plusDays(2), now().plusDays(1))
        );
        LockerRegisterRequestDto lockerRegisterRequestDto = getLockerRegisterRequestDto(user, locker);
        //when
        //then
        Assertions.assertThatThrownBy(() -> lockerUseCase.register(lockerRegisterRequestDto)).isInstanceOf(IsNotReserveTimeException.class);
    }

    @DisplayName("이미 예약된 사물함을 예약시도할때 에러 반환 테스트")
    @Test
    void reserveLockerWithAlreadyReservedLocker() throws Exception {
        //given
        User user1 = userQueryPort.save(getUser("이재표1", "재학", "19011721"));
        User user2 = userQueryPort.save(getUser("이재표2", "재학", "19011722"));

        Locker locker = lockerQueryPort.save(
                getLocker(1L, now().plusDays(2), now().minusDays(1))
        );
        LockerRegisterRequestDto lockerRegisterRequestDto1 = getLockerRegisterRequestDto(user1, locker);
        LockerRegisterRequestDto lockerRegisterRequestDto2 = getLockerRegisterRequestDto(user2, locker);

        //when
        lockerUseCase.register(lockerRegisterRequestDto1);
        //then
        Assertions.assertThatThrownBy(() -> lockerUseCase.register(lockerRegisterRequestDto2)).isInstanceOf(AlreadyReservedLockerException.class);
    }

    @DisplayName("이미 예약한 사용자가 예약시도할때 에러 반환 테스트")
    @Test
    void reserveLockerWithAlreadyReserveUser() throws Exception {
        //given
        User user1 = userQueryPort.save(getUser("이재표1", "재학", "19011721"));

        Locker locker1 = lockerQueryPort.save(
                getLocker(1L, now().plusDays(2), now().minusDays(1))
        );
        Locker locker2 = lockerQueryPort.save(
                getLocker(2L, now().plusDays(2), now().minusDays(1))
        );
        LockerRegisterRequestDto lockerRegisterRequestDto1 = getLockerRegisterRequestDto(user1, locker1);
        LockerRegisterRequestDto lockerRegisterRequestDto2 = getLockerRegisterRequestDto(user1, locker2);

        //when
        lockerUseCase.register(lockerRegisterRequestDto1);
        //then
        Assertions.assertThatThrownBy(() -> lockerUseCase.register(lockerRegisterRequestDto2)).isInstanceOf(AlreadyReservedUserException.class);
    }

    @DisplayName("재학생이 각각 다른 사물함을 예약할때")
    @Test
    void reserveDifferentLockerWithDifferentUser() throws Exception {
        User user1 = userQueryPort.save(getUser("이재표","재학","19011721"));
        User user2 = userQueryPort.save(getUser("심주미","재학","19011822"));

        Locker locker1 = lockerQueryPort.save(
                getLocker(1L, now().plusDays(1), now().minusDays(1))
        );
        Locker locker2 = lockerQueryPort.save(
                getLocker(2L, now().plusDays(1), now().minusDays(1))
        );
        LockerRegisterRequestDto dto1 = getLockerRegisterRequestDto(user1, locker1);
        LockerRegisterRequestDto dto2 = getLockerRegisterRequestDto(user2, locker2);
        LockerRegisterResponseDto register = lockerUseCase.register(dto1);
        LockerRegisterResponseDto register1 = lockerUseCase.register(dto2);
        assertThat(register.getLockerNum()).isEqualTo(dto1.getLockerNum());
        assertThat(register1.getLockerNum()).isEqualTo(dto2.getLockerNum());
    }

    private static LockerRegisterRequestDto getLockerRegisterRequestDto(User user1, Locker locker1) {
        return LockerRegisterRequestDto.builder()
                .studentNum(user1.getStudentNum())
                .lockerNum(locker1.getId())
                .build();
    }

    private static Locker getLocker(long id, LocalDateTime endDateTime, LocalDateTime startDateTime) {
        return Locker.builder()
                .id(id)
                .period(
                        Period.builder()
                                .endDateTime(endDateTime)
                                .startDateTime(startDateTime)
                                .build()
                )
                .usable(true)
                .build();
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

    @DisplayName("여러명이 동시에 같은 사물함을 예약할때 데이터 정합성테스트")
    @Test
    void reserveConcurrencyTest() throws InterruptedException {
        //given
        log.info("동시성 테스트 준비");
        for(int i=0;i<100;i++){
            String name = Integer.toString(i);
            Role role = Role.ROLE_USER;
            String status = "재학";
            String studentNum = Integer.toString(19011721 + i);
            boolean membership = true;
            userQueryPort.save(
                    User.builder()
                            .membership(membership)
                            .studentNum(studentNum)
                            .status(status)
                            .role(role)
                            .name(name)
                            .build()
            );
            log.info(studentNum);
        }
        Locker locker1 = lockerQueryPort.save(
                getLocker(1L, now().plusDays(1), now().minusDays(1))
        );

        int numberOfThread=100;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThread);
        CountDownLatch countDownLatch = new CountDownLatch(numberOfThread);

        //when
        log.info("lockerReserve 동시성 테스트 진행");
        for(int i=0;i<100;i++){
            String studentNums = Integer.toString(19011721 + i);
            service.execute(
                    ()->{
                        try {
                            redissonLockLockerFacade.register(LockerRegisterRequestDto.builder()
                                            .lockerNum(1L)
                                            .studentNum(studentNums)
                                    .build());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        } finally {
                            countDownLatch.countDown();
                        }
                    }
            );
        }
        countDownLatch.await();

        //then
        log.info("lockerReserve 동시성 테스트 검증");
        int count = 0;
        for(int i=0;i<100;i++){
            String studentNum = Integer.toString(19011721 + i);
            Optional<User> byStudentNum = userQueryPort.findByStudentNum(studentNum);
            if(byStudentNum.get().getLocker()!=null){
                count++;
            }
        }

        Assertions.assertThat(count).isEqualTo(1);
    }
}