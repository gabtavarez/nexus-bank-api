package com.tavarez.nexusbank.repositories;

import com.tavarez.nexusbank.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByAccountNumber(String accountNumber);
    java.util.Optional<Account> findByAccountNumber(String accountNumber);
}
