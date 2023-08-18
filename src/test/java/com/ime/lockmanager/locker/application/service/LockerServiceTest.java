package com.ime.lockmanager.locker.application.service;

import com.ime.lockmanager.locker.adapter.out.LockerJpaRepository;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.locker.domain.Period;
import com.ime.lockmanager.user.adapter.out.UserJpaRepository;
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
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class LockerServiceTest {
    @Autowired
    private LockerService lockerService;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private LockerJpaRepository lockerJpaRepository;

    @Autowired
    private RedissonLockLockerFacade redissonLockLockerFacade;

    @BeforeEach
    public void cleanUp(){
        userJpaRepository.deleteAll();
        lockerJpaRepository.deleteAll();
    }

    @DisplayName("휴햑생이 사물함을 예약할때")
    @Test
    void reserveLockerWithLeaveStudent() throws Exception {
        //given
        User user = userJpaRepository.save(getUser("이재표", "휴학", "19011721"));
        Locker locker = lockerJpaRepository.save(
                getLocker(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().minusDays(1))
        );
        LockerRegisterRequestDto lockerRegisterRequestDto = getLockerRegisterRequestDto(user, locker);
        //when
        //then
        Assertions.assertThatThrownBy(() -> lockerService.register(lockerRegisterRequestDto)).isInstanceOf(RuntimeException.class);
    }

    @DisplayName("졸업생이 사물함을 예약할때")
    @Test
    void reserveLockerWithGraduatedStudent() throws Exception {
        //given
        User user = userJpaRepository.save(getUser("이재표", "졸업", "19011721"));
        Locker locker = lockerJpaRepository.save(
                getLocker(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().minusDays(1))
        );
        LockerRegisterRequestDto lockerRegisterRequestDto = getLockerRegisterRequestDto(user, locker);
        //when
        //then
        Assertions.assertThatThrownBy(() -> lockerService.register(lockerRegisterRequestDto)).isInstanceOf(RuntimeException.class);
    }

    @DisplayName("재학생이 각각 다른 사물함을 예약할때")
    @Test
    void reserveDifferentLockerWithDifferentUser() throws Exception {
        User user1 = userJpaRepository.save(getUser("이재표","재학","19011721"));
        User user2 = userJpaRepository.save(getUser("심주미","재학","19011822"));

        Locker locker1 = lockerJpaRepository.save(
                getLocker(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().minusDays(1))
        );
        Locker locker2 = lockerJpaRepository.save(
                getLocker(2L, LocalDateTime.now().plusDays(1), LocalDateTime.now().minusDays(1))
        );
        LockerRegisterRequestDto dto1 = getLockerRegisterRequestDto(user1, locker1);
        LockerRegisterRequestDto dto2 = getLockerRegisterRequestDto(user2, locker2);
        LockerRegisterResponseDto register = lockerService.register(dto1);
        LockerRegisterResponseDto register1 = lockerService.register(dto2);
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
            userJpaRepository.save(
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
        Locker locker1 = lockerJpaRepository.save(
                getLocker(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().minusDays(1))
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
            Optional<User> byStudentNum = userJpaRepository.findByStudentNum(studentNum);
            if(byStudentNum.get().getLocker()!=null){
                count++;
            }
        }

        Assertions.assertThat(count).isEqualTo(1);
    }
}