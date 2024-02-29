package com.ime.lockmanager.account.application.port.in;

import com.ime.lockmanager.account.application.port.in.req.SaveOrModifyAccountRequestDto;
import com.ime.lockmanager.account.application.port.in.res.AccountInfoResponseDto;
import com.ime.lockmanager.account.application.port.in.res.SaveOrModifyAccountResponseDto;
import com.ime.lockmanager.account.application.port.out.AccountQueryPort;
import com.ime.lockmanager.account.domain.Account;
import com.ime.lockmanager.common.format.exception.account.NotFoundAccountException;
import com.ime.lockmanager.major.application.port.out.major.MajorQueryPort;
import com.ime.lockmanager.major.domain.Major;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class AccountUsecaseTest {
    @Autowired
    UserQueryPort userQueryPort;
    @Autowired
    AccountQueryPort accountQueryPort;
    @Autowired
    MajorQueryPort majorQueryPort;
    @Autowired
    AccountUsecase accountUsecase;



    @DisplayName("저장된 학과 계좌가 없을때 저장")
    @Test
    void saveAccountInfo() {
        //given
        Major major = majorQueryPort.save(createMajor("AI로봇학과"));
        //when
        SaveOrModifyAccountRequestDto dto = SaveOrModifyAccountRequestDto.builder()
                .accountNum("123123123")
                .accountNum("우리")
                .bank("이재표")
                .build();
        SaveOrModifyAccountResponseDto responseDto = accountUsecase.saveOrModifyAccountInfo(major.getId(), dto);
        //then
        assertAll(
                () -> assertEquals(responseDto.getAccountNum(), dto.getAccountNum()),
                () -> assertEquals(responseDto.getBank(), dto.getBank()),
                () -> assertEquals(responseDto.getOwnerName(), dto.getOwnerName())
        );
    }

    @DisplayName("저장된 학과 계좌가 있을때 수정")
    @Test
    void ModifyAccountInfo() {
        //given
        Major major = majorQueryPort.save(createMajor("AI로봇학과"));
        Account saveAccount = accountQueryPort.save(createAccount("123123123", "우리", "이재표", major));
        //when
        SaveOrModifyAccountRequestDto changeDto = SaveOrModifyAccountRequestDto.builder()
                .accountNum("12313213")
                .accountNum("국민")
                .bank("이재표")
                .build();
        SaveOrModifyAccountResponseDto responseDto = accountUsecase.saveOrModifyAccountInfo(major.getId(), changeDto);
        //then
        assertAll(
                () -> assertEquals(responseDto.getAccountNum(), changeDto.getAccountNum()),
                () -> assertEquals(responseDto.getBank(), changeDto.getBank()),
                () -> assertEquals(responseDto.getOwnerName(), changeDto.getOwnerName())
        );
    }

    @DisplayName("저장된 학과계좌 조회")
    @Test
    void findAccountInfo() {
        //given
        Major major = majorQueryPort.save(createMajor("AI로봇학과"));
        Account saveAccount = accountQueryPort.save(createAccount("123123123", "우리", "이재표", major));
        //when
        AccountInfoResponseDto accountInfo = accountUsecase.findAccountInfo(major.getId());
        //then
        assertAll(
                () -> assertEquals(accountInfo.getAccountNum(), saveAccount.getNumber()),
                () -> assertEquals(accountInfo.getBank(), saveAccount.getBank()),
                () -> assertEquals(accountInfo.getOwnerName(), saveAccount.getOwnerName())
        );
    }

    @DisplayName("저장된 학과계좌가 없을때 조회")
    @Test
    void findAccountInfoNoData() {
        //given
        //when
        Major major = majorQueryPort.save(createMajor("AI로봇학과"));
        //then
        assertThrows(NotFoundAccountException.class, () -> accountUsecase.findAccountInfo(major.getId()));
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