package com.bsuir.piris.persistence;

import com.bsuir.piris.model.domain.Account;
import com.bsuir.piris.model.domain.AccountCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountCode(AccountCode accountCode);
}
