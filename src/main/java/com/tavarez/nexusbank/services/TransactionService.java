package com.tavarez.nexusbank.services;


import com.tavarez.nexusbank.dto.request.TransactionRequestDTO;
import com.tavarez.nexusbank.dto.response.TransactionResponseDTO;
import com.tavarez.nexusbank.entities.Account;
import com.tavarez.nexusbank.entities.Transaction;
import com.tavarez.nexusbank.enums.TransactionStatus;
import com.tavarez.nexusbank.exceptions.BusinessException;
import com.tavarez.nexusbank.exceptions.InsufficientBalanceException;
import com.tavarez.nexusbank.repositories.AccountRepository;
import com.tavarez.nexusbank.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Serviço responsável por processar transações financeiras.
 * Realiza a validação de saldo, atualização de contas e registro da transação.
 */
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Executa uma transferência entre duas contas.
     * O método é Transacional: se houver erro no depósito, o saque é cancelado (rollback).
     *
     * @param dto Contém números das contas, valor e tipo da transação.
     * @return TransactionResponseDTO com os dados do comprovante.
     */
    @Transactional
    public TransactionResponseDTO executeTransfer(TransactionRequestDTO dto) {
        // 1. Busca as contas envolvidas pelo número
        Account source = accountRepository.findByAccountNumber(dto.sourceAccountNumber())
                .orElseThrow(() -> new BusinessException("Source account not found"));

        Account target = accountRepository.findByAccountNumber(dto.targetAccountNumber())
                .orElseThrow(() -> new BusinessException("Target account not found"));

        // 2. Valida se a conta de origem tem saldo suficiente
        if (source.getBalance().compareTo(dto.amount()) < 0) {
            throw new InsufficientBalanceException();
        }

        // 3. Realiza a movimentação financeira
        source.setBalance(source.getBalance().subtract(dto.amount()));
        target.setBalance(target.getBalance().add(dto.amount()));

        // 4. Salva as contas atualizadas
        accountRepository.save(source);
        accountRepository.save(target);

        // 5. Registra a transação no histórico
        Transaction transaction = Transaction.builder()
                .sourceAccount(source)
                .targetAccount(target)
                .amount(dto.amount())
                .type(dto.type())
                .status(TransactionStatus.COMPLETED)
                .createdAt(LocalDateTime.now())
                .build();

        transaction = transactionRepository.save(transaction);

        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getSourceAccount().getAccountNumber(),
                transaction.getTargetAccount().getAccountNumber(),
                transaction.getType(),
                transaction.getStatus(),
                transaction.getCreatedAt() // Este valor cairá no campo 'timestamp' do Record
        );
    }
}
