package com.ime.lockmanager.account.application.port.in;

import com.ime.lockmanager.account.application.port.out.AccountQueryPort;
import com.ime.lockmanager.account.application.service.AccountService;
import com.ime.lockmanager.account.domain.Account;
import com.ime.lockmanager.user.adapter.out.UserQueryRepository;
import com.ime.lockmanager.user.application.port.out.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class AccountUsecaseTest {
    private final UserQueryPort userQueryPort;
    private final AccountQueryPort accountQueryPort;
    @DisplayName("")
    @Test
    void findAccountInfo() {
        //given
        final AccountUsecase accountUsecase=new AccountService();
        //when

        //then
    }

    @Test
    void saveOrModifyAccountInfo() {
    }
}