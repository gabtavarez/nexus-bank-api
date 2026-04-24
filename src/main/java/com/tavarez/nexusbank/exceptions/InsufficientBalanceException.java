package com.tavarez.nexusbank.exceptions;

public class InsufficientBalanceException extends BusinessException {
    public InsufficientBalanceException() {
        super("Saldo insuficiente para realizar a transação.");
    }
}
