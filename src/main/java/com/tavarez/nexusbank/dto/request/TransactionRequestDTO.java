package com.tavarez.nexusbank.dto.request;

import com.tavarez.nexusbank.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionRequestDTO(
        @NotNull String sourceAccountNumber,
        @NotNull String targetAccountNumber,
        @NotNull @Positive BigDecimal amount, // BigDecimal garante a precisão para o banco
        @NotNull TransactionType type
) {}