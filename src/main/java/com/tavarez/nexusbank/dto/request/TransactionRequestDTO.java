package com.tavarez.nexusbank.dto.request;

import com.tavarez.nexusbank.enums.TransactionType;

import java.math.BigDecimal;

public record TransactionRequestDTO(
        String sourceAccountNumber,
        String targetAccountNumber,
        BigDecimal amount,
        TransactionType type
) {}