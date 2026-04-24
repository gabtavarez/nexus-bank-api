package com.tavarez.nexusbank.dto.request;

public record UserRequestDTO(
        String username,
        String email,
        String password
) {}