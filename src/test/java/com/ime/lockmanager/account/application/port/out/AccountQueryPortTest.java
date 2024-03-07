package com.ime.lockmanager.account.application.port.out;

import com.ime.lockmanager.account.domain.Account;
import com.ime.lockmanager.major.application.port.out.major.MajorCommandPort;
import com.ime.lockmanager.major.application.port.out.major.MajorQueryPort;
import com.ime.lockmanager.major.domain.Major;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class AccountQueryPortTest {
    @Autowired
    AccountQueryPort accountQueryPort;
    @Autowired
    MajorCommandPort majorCommandPort;
    @Autowired
    AccountCommandPort accountCommandPort;

    @DisplayName("학과 아이디로 계좌 조회")
    @Test
    void findByMajorIdTest() {
        //given
        Major major = Major.builder().name("AI로봇학과").build();
        Major saveMajor = majorCommandPort.save(major);

        Account account = Account.builder()
                .major(saveMajor)
                .number("123123")
                .bank("bank")
                .ownerName("name")
                .build();
        Account saveAccount = accountCommandPort.save(account);
        //when
        Account findAccount = accountQueryPort.findByMajorId(saveMajor.getId()).get();
        //then
        Assertions.assertAll(
                ()->Assertions.assertEquals(findAccount.getNumber(),saveAccount.getNumber()),
                ()->Assertions.assertEquals(findAccount.getBank(),saveAccount.getBank()),
                ()->Assertions.assertEquals(findAccount.getOwnerName(),saveAccount.getOwnerName()),
                ()->Assertions.assertEquals(findAccount.getMajor().getId(),saveAccount.getMajor().getId())
        );
    }

    @DisplayName("계좌를 등록하지 않은 학과일때 계좌 조회")
    @Test
    void NotSavefindByMajorIdTest() {
        //given
        Major major = Major.builder().name("AI로봇학과").build();
        Major saveMajor = majorCommandPort.save(major);
        //when
        //then
        Assertions.assertTrue(accountQueryPort.findByMajorId(saveMajor.getId()).isEmpty());
    }

    @DisplayName("다른 학과의 아이디로 계좌 조회")
    @Test
    void findByDifferentMajorIdTest() {
        //given
        Major major = Major.builder().name("AI로봇학과").build();
        Major saveMajor = majorCommandPort.save(major);
        //when
        //then
        Assertions.assertTrue(accountQueryPort.findByMajorId(saveMajor.getId()+1).isEmpty());
    }
}