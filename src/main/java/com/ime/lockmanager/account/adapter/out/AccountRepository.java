package com.ime.lockmanager.account.adapter.out;

import com.ime.lockmanager.account.domain.Account;
import com.ime.lockmanager.major.domain.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByMajorId(Long majorId);

    Account save(Account entity);

    Optional<Account> findByMajor(Major major);
}
