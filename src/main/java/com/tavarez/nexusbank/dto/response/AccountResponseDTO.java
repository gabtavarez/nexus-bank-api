package com.tavarez.nexusbank.dto.response;

import com.tavarez.nexusbank.enums.AccountStatus;
import com.tavarez.nexusbank.enums.AccountType;

import java.math.BigDecimal;

public record AccountResponseDTO(
        Long id,
        String accountNumber,
        BigDecimal balance,
        AccountType type,
        AccountStatus status
) {}