package com.ime.lockmanager.locker.application.service;

import com.ime.lockmanager.locker.adapter.in.req.LockerRegisterRequest;
import com.ime.lockmanager.locker.adapter.out.LockerJpaRepository;
import com.ime.lockmanager.locker.adapter.out.LockerQueryRepository;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerRegisterResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.user.adapter.out.UserJpaRepository;
import com.ime.lockmanager.user.adapter.out.UserQueryRepository;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
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
import org.springframework.transaction.support.TransactionTemplate;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class LockerServiceTest {
    @Autowired
    private LockerService lockerService;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private RedissonLockLockerFacade redissonLockLockerFacade;

    @DisplayName("각각 다른 사물함을 예약할때")
    @Test
    void reserveTest() throws Exception {
        LockerRegisterRequestDto dto1 = LockerRegisterRequestDto.builder()
                .studentNum("19011722")
                .lockerNum(1L)
                .build();
        LockerRegisterRequestDto dto2 = LockerRegisterRequestDto.builder()
                .studentNum("19011723")
                .lockerNum(2L)
                .build();
        LockerRegisterResponseDto register = lockerService.register(dto1);
        LockerRegisterResponseDto register1 = lockerService.register(dto2);
        assertThat(register.getLockerNum()).isEqualTo(dto1.getLockerNum());
        assertThat(register1.getLockerNum()).isEqualTo(dto2.getLockerNum());
    }

    @DisplayName("여러명이 동시에 같은 사물함을 예약할때 데이터 정합성테스트")
    @Test
    void reserveConcurrencyTest() throws InterruptedException {
        log.info("동시성 테스트 시작");
        //given
        log.info("동시성 테스트 준비");
        int numberOfThread=2;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThread);
        CountDownLatch countDownLatch = new CountDownLatch(numberOfThread);

        //when
        log.info("lockerReserve 동시성 테스트 진행");
        LockerRegisterRequestDto dto1 = LockerRegisterRequestDto.builder()
                .studentNum("19011722")
                .lockerNum(1L)
                .build();
        LockerRegisterRequestDto dto2 = LockerRegisterRequestDto.builder()
                .studentNum("19011723")
                .lockerNum(1L)
                .build();
            service.execute(
                    ()->{
                        try {
                            redissonLockLockerFacade.register(dto1);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        } finally {
                            countDownLatch.countDown();
                        }
                    }
            );
            service.execute(
                    ()->{
                        try {
                            redissonLockLockerFacade.register(dto2);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }finally {
                            countDownLatch.countDown();
                        }
                    }
            );
            countDownLatch.await();

        //then
        log.info("lockerReserve 동시성 테스트 검증");
        Optional<User> byStudentNum = userJpaRepository.findByStudentNum("19011722");
        Optional<User> byStudentNum1 = userJpaRepository.findByStudentNum("19011723");
        log.info(byStudentNum.get().getStudentNum());
        log.info(byStudentNum1.get().getStudentNum());
        System.out.println(byStudentNum.get().getLocker());
        System.out.println(byStudentNum1.get().getLocker());

        Assertions.assertThatThrownBy(
                () -> {
                    Long id1 = byStudentNum1.get().getLocker().getId();
                    Long id = byStudentNum.get().getLocker().getId();
                }
        ).isInstanceOf(NullPointerException.class);
    }
}