package com.ime.lockmanager.user.application.port.out;

import com.ime.lockmanager.reservation.application.port.out.ReservationQueryPort;
import com.ime.lockmanager.user.domain.Role;
import com.ime.lockmanager.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class UserQueryPortTest {

    @Autowired
    private UserQueryPort userQueryPort;
    @Autowired
    private ReservationQueryPort reservationQueryPort;

    @BeforeEach
    void tearDown() {
        reservationQueryPort.deleteAll();
        userQueryPort.deleteAll();
    }

    @DisplayName("오름차순으로 학번을 정렬하여 모든 사용자를 조회하는 테스트")
    @Test
    void findAllOrderByStudentNumAscTest(){
        //given
        for(int i=10;i>0;i--){
            userQueryPort.save(getUser(String.valueOf(i),String.valueOf(i),String.valueOf(i)));
        }
        PageRequest request1 = PageRequest.of(0, 5);
        PageRequest request2 = PageRequest.of(1, 5);
        //when
        Page<User> allOrderByStudentNumAsc = userQueryPort.findAllOrderByStudentNumAsc(request1);
        Page<User> allOrderByStudentNumAsc1 = userQueryPort.findAllOrderByStudentNumAsc(request2);
        //then
        assertThat(allOrderByStudentNumAsc.stream().findFirst().get().getStudentNum()).isEqualTo("1");
    }

    @DisplayName("사용자가 없을때 오름차순으로 학번을 정렬하여 모든 사용자를 조회하는 테스트")
    @Test
    void findAllOrderByStudentNumAscWithoutUserTest(){
        //given
        PageRequest request1 = PageRequest.of(0, 5);
        //when
        Page<User> allOrderByStudentNumAsc = userQueryPort.findAllOrderByStudentNumAsc(request1);
        //then
        assertThat(allOrderByStudentNumAsc.get().collect(Collectors.toList())).isEmpty();
    }

    @DisplayName("모든 사용자 조회메서드 테스트")
    @Test
    void findAllTest(){
        //given
        userQueryPort.save(getUser("test", "test", "test"));
        //when
        List<User> users = userQueryPort.findAll();
        //then
        assertThat(users).hasSize(1);
    }

    @DisplayName("사용자가 존재하지 않을때 모든 사용자 조회메서드 테스트")
    @Test
    void findAllWithoutUserTest(){
        //given
        //when
        List<User> users = userQueryPort.findAll();
        //then
        assertThat(users).hasSize(0);
    }

    @DisplayName("사용자 삭제 테스트")
    @Test
    void deleteUserTest(){
        //given
        userQueryPort.save(getUser("test", "test", "test"));
        //when
        userQueryPort.deleteAll();
        //then
        assertThat(userQueryPort.findAll()).hasSize(0);
    }

    @DisplayName("사용자 저장 테스트")
    @Test
    void saveUserTest(){
        //given
        //when
        User save = userQueryPort.save(getUser("test", "test", "test"));
        //then
        assertAll(() -> assertEquals(save.getStatus(), "test"),
                () -> assertEquals(save.getStudentNum(), "test"),
                () -> assertEquals(save.getName(), "test")
        );
    }

    @DisplayName("학번으로 사용자 조회 테스트")
    @Test
    void userFindByStudentNumTest(){
        //given
        userQueryPort.save(getUser("test", "test", "test"));
        //when
        Optional<User> test = userQueryPort.findByStudentNum("test");
        //then
        assertAll(()->assertEquals(test.get().getStudentNum(),"test"),
                ()->assertEquals(test.get().getName(),"test"),
                ()->assertEquals(test.get().getStatus(),"test"));
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