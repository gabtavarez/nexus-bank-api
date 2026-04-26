package com.tavarez.nexusbank.controllers;

import com.tavarez.nexusbank.dto.request.TransactionRequestDTO;
import com.tavarez.nexusbank.dto.response.TransactionResponseDTO;
import com.tavarez.nexusbank.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDTO> transfer(@RequestBody @Valid TransactionRequestDTO dto) {
        TransactionResponseDTO response = transactionService.executeTransfer(dto);
        return ResponseEntity.ok(response);
    }
}