package com.ime.lockmanager.account.application.port.out;

import com.ime.lockmanager.account.domain.Account;
import com.ime.lockmanager.major.domain.Major;

import java.util.Optional;

public interface AccountQueryPort {
    Optional<Account> findByMajor(Major major);

    Account save(Account account);
}
