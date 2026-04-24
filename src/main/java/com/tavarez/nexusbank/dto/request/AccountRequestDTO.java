package com.tavarez.nexusbank.dto.request;

import com.tavarez.nexusbank.enums.AccountType;

public record AccountRequestDTO(
        Long userId,
        AccountType type
) {}