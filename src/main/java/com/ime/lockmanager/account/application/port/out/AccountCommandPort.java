package com.ime.lockmanager.account.application.port.out;

import com.ime.lockmanager.account.domain.Account;

public interface AccountCommandPort {
    Account save(Account account);
}
