package com.ime.lockmanager.account.adapter.out;

import com.ime.lockmanager.account.application.port.out.AccountQueryPort;
import com.ime.lockmanager.account.domain.Account;
import com.ime.lockmanager.major.domain.Major;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountQueryRepository implements AccountQueryPort {
    private final AccountRepository accountRepository;
    @Override
    public Optional<Account> findByMajorId(Long majorId) {
        return accountRepository.findByMajorId(majorId);
    }

    @Override
    public Optional<Account> findByMajor(Major major) {
        return accountRepository.findByMajor(major);
    }
}
