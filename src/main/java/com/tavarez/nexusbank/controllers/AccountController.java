package com.tavarez.nexusbank.controllers;

import com.tavarez.nexusbank.dto.request.AccountRequestDTO;
import com.tavarez.nexusbank.dto.response.AccountResponseDTO;
import com.tavarez.nexusbank.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller que expõe os endpoints para operações com contas bancárias.
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * Endpoint POST para criação de conta.
     * Recebe o JSON de requisição, processa no service e retorna 201 Created.
     */
    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody @Valid AccountRequestDTO dto) {
        AccountResponseDTO response = accountService.createAccount(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}