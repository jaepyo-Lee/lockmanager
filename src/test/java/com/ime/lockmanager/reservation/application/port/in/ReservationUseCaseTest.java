package com.ime.lockmanager.reservation.application.port.in;

import com.ime.lockmanager.locker.application.port.in.req.LockerRegisterRequestDto;
import com.ime.lockmanager.locker.application.port.in.res.ReservationOfLockerResponseDto;
import com.ime.lockmanager.locker.application.port.out.LockerQueryPort;
import com.ime.lockmanager.locker.domain.locker.Locker;
import com.ime.lockmanager.locker.domain.Period;
import com.ime.lockmanager.reservation.application.service.RedissonLockReservationFacade;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class ReservationUseCaseTest {

    @Autowired
    private ReservationUseCase reservationUseCase;

    @Autowired
    private UserQueryPort userQueryPort;

    @Autowired
    private LockerQueryPort lockerQueryPort;
    @Autowired
    private RedissonLockReservationFacade redissonLockReservationFacade;
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
        redissonLockReservationFacade.registerForUser(LockerRegisterRequestDto.builder()
                .lockerDetailId(1L)
                .studentNum("19011721")
                .build());
        //when
        ReservationOfLockerResponseDto reserveLocker = reservationUseCase.findReservedLockers();
        //then
        assertThat(reserveLocker.getLockerIdList()).hasSize(1);
    }

    private static LockerRegisterRequestDto getLockerRegisterRequestDto(User user1, Locker locker1) {
        return LockerRegisterRequestDto.builder()
                .studentNum(user1.getStudentNum())
                .lockerDetailId(locker1.getId())
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
}