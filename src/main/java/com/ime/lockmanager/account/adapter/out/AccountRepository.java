package com.ime.lockmanager.account.adapter.out;

import com.ime.lockmanager.account.domain.Account;
import com.ime.lockmanager.major.domain.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByMajor(Major major);

    Account save(Account entity);
}
