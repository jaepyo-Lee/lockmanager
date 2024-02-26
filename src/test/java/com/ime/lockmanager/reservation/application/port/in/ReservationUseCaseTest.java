package com.ime.lockmanager.reservation.application.port.in;

import com.ime.lockmanager.locker.application.port.in.LockerUseCase;
import com.ime.lockmanager.locker.application.port.in.req.LockerCreateRequestDto;
import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.LockerCreateResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerDetailQueryPort;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.lockerdetail.LockerDetail;
import com.ime.lockmanager.major.application.port.out.MajorDetailQueryPort;
import com.ime.lockmanager.major.application.port.out.MajorQueryPort;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.major.domain.MajorDetail;
import com.ime.lockmanager.reservation.application.port.out.ReservationQueryPort;
import com.ime.lockmanager.reservation.domain.Reservation;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.ime.lockmanager.locker.adapter.in.req.NumberIncreaseDirection.DOWN;
import static com.ime.lockmanager.user.domain.UserState.ATTEND;
import static com.ime.lockmanager.user.domain.UserTier.MEMBER;
import static java.time.LocalDateTime.now;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class ReservationUseCaseTest {
    @Autowired
    private LockerDetailQueryPort lockerDetailQueryPort;
    @Autowired
    private ReservationQueryPort reservationQueryPort;
    @Autowired
    private UserQueryPort userQueryPort;
    @Autowired
    private LockerUseCase lockerUseCase;
    @Autowired
    private MajorQueryPort majorQueryPort;
    @Autowired
    private MajorDetailQueryPort majorDetailQueryPort;
    @Autowired
    private ReservationUseCase reservationUseCase;

    @DisplayName("여러명이 동시에 같은 사물함을 예약할때 데이터 정합성테스트")
    @Test
    void reserveConcurrencyTest() throws InterruptedException, IOException {
        //given
        log.info("동시성 테스트 준비");
        Major major = majorQueryPort.save(Major.builder().name("AI로봇학과").build());
        MajorDetail majorDetail = majorDetailQueryPort.save(
                MajorDetail.builder().major(major).name("무인이동체공학전공").build());
        List<Long> userIds = new ArrayList<>();

        log.info("사용자 생성");
        for (int i = 0; i < 100; i++) {
            String name = Integer.toString(i);
            Role role = Role.ROLE_USER;
            String status = "재학";
            String studentNum = Integer.toString(19011721 + i);
            boolean membership = true;
            User save = userQueryPort.save(
                    User.builder()
                            .majorDetail(majorDetail)
                            .userTier(MEMBER)
                            .userState(ATTEND)
                            .studentNum(studentNum)
                            .status(status)
                            .role(role)
                            .name(name)
                            .build()
            );
            userIds.add(save.getId());
            log.info(studentNum);
        }

        log.info("사물함 생성");
        LockerCreateResponseDto savedLocker = lockerUseCase.createLocker(LockerCreateRequestDto.builder()
                .endReservationTime(now().plusDays(1))
                .startReservationTime(now().minusDays(1))
                .lockerName("test")
                .totalColumn("15")
                .totalRow("10")
                .numberIncreaseDirection(DOWN)
                .userStates(List.of(ATTEND))
                .userTiers(List.of(MEMBER))
                .build(), major.getId());

        List<LockerDetail> lockerDetails = lockerDetailQueryPort
                .findLockerDetailByLocker(savedLocker.getCreatedLockerId());

        int numberOfThread = 100;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThread);
        CountDownLatch countDownLatch = new CountDownLatch(numberOfThread);

        //when
        log.info("lockerReserve 동시성 테스트 진행");
        for (Long userId : userIds) {

            service.execute(
                    () -> {
                        try {
                            reservationUseCase.registerForUser(LockerRegisterRequestDto.builder()
                                    .userId(userId)
                                    .lockerDetailId(lockerDetails.get(0).getId())
                                    .majorId(major.getId())
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

        for (Long userId : userIds) {
            Optional<Reservation> reservation = reservationQueryPort.findByUserId(userId);
            if (reservation.isPresent()) {
                log.info("예약번호 : " + reservation.get().getId());
                count++;
            }
        }

        log.info("끝났다~~");
        Assertions.assertThat(count).isEqualTo(1);
        log.info("검증됬다~~~~");
    }
}
