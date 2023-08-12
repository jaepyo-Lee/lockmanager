package com.ime.lockmanager.locker.application.service;

import com.ime.lockmanager.locker.adapter.in.req.LockerRegisterRequest;
import com.ime.lockmanager.locker.adapter.out.LockerJpaRepository;
import com.ime.lockmanager.locker.adapter.out.LockerQueryRepository;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.Locker;
import com.ime.lockmanager.locker.domain.Period;
import com.ime.lockmanager.user.adapter.out.UserJpaRepository;
import com.ime.lockmanager.user.adapter.out.UserQueryRepository;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.MultipartStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionUsageException;
import org.springframework.transaction.support.TransactionTemplate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

    @DisplayName("각각 다른 사물함을 예약할때")
    @Test
    void reserveTest() throws Exception {
        User user1 = userJpaRepository.save(User.builder()
                .name("이재표")
                .role(Role.ROLE_ADMIN)
                .status("재학")
                .studentNum("19011721")
                .membership(true)
                .build());
        User user2 = userJpaRepository.save(User.builder()
                .name("심주미")
                .role(Role.ROLE_ADMIN)
                .status("졸업")
                .studentNum("19011822")
                .membership(true)
                .build());

        Locker locker1 = lockerJpaRepository.save(
                Locker.builder()
                        .id(1L)
                        .period(
                                Period.builder()
                                        .endDateTime(LocalDateTime.now().plusDays(1))
                                        .startDateTime(LocalDateTime.now().minusDays(1))
                                        .build()
                        )
                        .usable(true)
                        .build()
        );
        Locker locker2 = lockerJpaRepository.save(
                Locker.builder()
                        .id(2L)
                        .period(
                                Period.builder()
                                        .endDateTime(LocalDateTime.now().plusDays(1))
                                        .startDateTime(LocalDateTime.now().minusDays(1))
                                        .build()
                        )
                        .usable(true)
                        .build()
        );
        LockerRegisterRequestDto dto1 = LockerRegisterRequestDto.builder()
                .studentNum(user1.getStudentNum())
                .lockerNum(locker1.getId())
                .build();
        LockerRegisterRequestDto dto2 = LockerRegisterRequestDto.builder()
                .studentNum(user2.getStudentNum())
                .lockerNum(locker2.getId())
                .build();
        LockerRegisterResponseDto register = lockerService.register(dto1);
        LockerRegisterResponseDto register1 = lockerService.register(dto2);
        assertThat(register.getLockerNum()).isEqualTo(dto1.getLockerNum());
        assertThat(register1.getLockerNum()).isEqualTo(dto2.getLockerNum());
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
                Locker.builder()
                        .id(1L)
                        .period(
                                Period.builder()
                                        .endDateTime(LocalDateTime.now().plusDays(1))
                                        .startDateTime(LocalDateTime.now().minusDays(1))
                                        .build()
                        )
                        .usable(true)
                        .build()
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