package com.tavarez.nexusbank.dto.response;

public record UserResponseDTO(
        Long id,
        String username,
        String email
) {}