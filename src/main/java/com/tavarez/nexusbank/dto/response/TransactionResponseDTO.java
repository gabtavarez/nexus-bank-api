package com.tavarez.nexusbank.dto.response;

public record TransactionResponseDTO (
        Long id,
        java.math.BigDecimal amount,
        String sourceAccountNumber,
        String destinationAccountNumber,
        com.tavarez.nexusbank.enums.TransactionType type,
        com.tavarez.nexusbank.enums.TransactionStatus status,
        java.time.LocalDateTime timestamp
) {}