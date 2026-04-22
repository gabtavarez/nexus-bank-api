package com.tavarez.nexusbank.repositories;

import com.tavarez.nexusbank.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
