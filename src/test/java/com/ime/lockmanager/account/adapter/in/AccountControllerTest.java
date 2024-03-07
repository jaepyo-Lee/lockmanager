package com.ime.lockmanager.account.adapter.in;

import com.ime.lockmanager.account.application.port.out.AccountCommandPort;
import com.ime.lockmanager.account.domain.Account;
import com.ime.lockmanager.major.application.port.out.major.MajorCommandPort;
import com.ime.lockmanager.major.domain.Major;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
class AccountControllerTest {
//    @Autowired
//    private MajorCommandPort majorCommandPort;
//    @Autowired
//    private AccountCommandPort accountCommandPort;

//    @BeforeEach
//    void init() {
//        Major major = Major.builder().name("AI로봇학과").build();
//        majorCommandPort.save(major);
//
//        Account account = Account.builder()
//                .ownerName("name")
//                .number("123321")
//                .bank("bank")
//                .major(major)
//                .build();
//        accountCommandPort.save(account);
//    }

    @DisplayName("학과 계좌정보 조회")
    @Test
    void findAccountInfo() throws Exception {
        test();
//
//        mockMvc.perform(
//                        get("/majors/" + "1" + "/accounts")
//                ).andDo(print())
//                .andExpect(status().isOk());
    }

    public static void test(){
        int asd=5;
        System.out.println(asd);
    }
}