package com.tavarez.nexusbank.repositories;

import com.tavarez.nexusbank.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByAccountNumber(String accountNumber);
}
