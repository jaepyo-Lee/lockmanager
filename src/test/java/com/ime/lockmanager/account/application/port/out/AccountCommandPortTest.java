package com.ime.lockmanager.account.application.port.out;

import com.ime.lockmanager.account.domain.Account;
import com.ime.lockmanager.major.application.port.out.major.MajorCommandPort;
import com.ime.lockmanager.major.domain.Major;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@SpringBootTest
class AccountCommandPortTest {
    @Autowired
    MajorCommandPort majorCommandPort;
    @Autowired
    AccountCommandPort accountCommandPort;
    @DisplayName("계좌 정보 저장 테스트")
    @Test
    void accountSaveTest() {
        //given
        Major major = Major.builder().name("test").build();
        Major saveMajor = majorCommandPort.save(major);

        Account account = Account.builder()
                .major(saveMajor)
                .number("123123")
                .ownerName("name")
                .bank("bank")
                .build();

        //when
        Account saveAccount = accountCommandPort.save(account);
        //then
        Assertions.assertAll(
                ()->Assertions.assertEquals(saveAccount.getNumber(),account.getNumber()),
                ()->Assertions.assertEquals(saveAccount.getBank(),account.getBank()),
                ()->Assertions.assertEquals(saveAccount.getOwnerName(),account.getOwnerName()),
                ()->Assertions.assertEquals(saveAccount.getMajor(),account.getMajor())
        );
    }
}