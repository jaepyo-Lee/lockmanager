package com.ime.lockmanager.account.application.port.out;

import com.ime.lockmanager.account.domain.Account;
import com.ime.lockmanager.major.application.port.out.MajorQueryPort;
import com.ime.lockmanager.major.domain.Major;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ActiveProfiles("test")
@DataJpaTest
class AccountQueryPortTest {
    @Autowired
    AccountQueryPort accountQueryPort;

    @Autowired
    MajorQueryPort majorQueryPort;

    @Test
    void saveAccountTest() {
        //given
        Major major = majorQueryPort.save(createMajor("AI로봇학과"));
        //when
        Account account = createAccount("123123123", "우리", "이재표", major);
        Account saveAccount = accountQueryPort.save(account);
        //then
        assertAll(
                () -> assertEquals(saveAccount.getNumber(), account.getNumber()),
                () -> assertEquals(saveAccount.getBank(), account.getBank()),
                () -> assertEquals(saveAccount.getOwnerName(), account.getNumber())
        );
    }

    @Test
    void test() {
        //given
        accountQueryPort.findByMajor()
        //when

        //then
    }

    @Test
    void test() {
        //given
        accountQueryPort.findByMajorId()
        //when

        //then
    }

    private static Account createAccount(String number, String bank, String ownerName, Major major) {
        return Account.builder()
                .number(number)
                .bank(bank)
                .ownerName(ownerName)
                .major(major)
                .build();
    }
    private static Major createMajor(String majorName) {
        return Major.of(majorName);
    }
}