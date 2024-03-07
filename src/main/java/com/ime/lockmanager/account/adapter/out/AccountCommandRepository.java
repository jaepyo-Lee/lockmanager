package com.ime.lockmanager.account.adapter.out;

import com.ime.lockmanager.account.application.port.out.AccountCommandPort;
import com.ime.lockmanager.account.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AccountCommandRepository implements AccountCommandPort {
    private final AccountRepository accountRepository;
    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }
}
