package com.tavarez.nexusbank.services;

import com.tavarez.nexusbank.dto.request.AccountRequestDTO;
import com.tavarez.nexusbank.dto.response.AccountResponseDTO;
import com.tavarez.nexusbank.entities.Account;
import com.tavarez.nexusbank.entities.User;
import com.tavarez.nexusbank.enums.AccountStatus;
import com.tavarez.nexusbank.exceptions.BusinessException;
import com.tavarez.nexusbank.repositories.AccountRepository;
import com.tavarez.nexusbank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public AccountResponseDTO createAccount(AccountRequestDTO dto) {
        User owner = userRepository.findById(dto.userId())
                .orElseThrow(() -> new BusinessException("User not found with ID: " + dto.userId()));

        Account account = Account.builder()
                .accountNumber(generateUniqueAccountNumber())
                .balance(new java.math.BigDecimal("1000.00"))
                .type(dto.type())
                .status(AccountStatus.ACTIVE)
                .owner(owner)
                .build();

        account = accountRepository.save(account);

        return new AccountResponseDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getType(),
                account.getStatus()
        );
    }

    private String generateUniqueAccountNumber() {
        Random random = new Random();
        String number;
        boolean exists;

        do {
            number = String.valueOf(100000 + random.nextInt(900000));
            exists = accountRepository.existsByAccountNumber(number);
        } while (exists);

        return number;
    }

    @Transactional
    public void deposit(String accountNumber, java.math.BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BusinessException("Account not found"));
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    public AccountResponseDTO findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(account -> new AccountResponseDTO(
                        account.getId(),
                        account.getAccountNumber(),
                        account.getBalance(),
                        account.getType(),
                        account.getStatus()
                ))
                .orElseThrow(() -> new BusinessException("Account not found with number: " + accountNumber));
    }
}