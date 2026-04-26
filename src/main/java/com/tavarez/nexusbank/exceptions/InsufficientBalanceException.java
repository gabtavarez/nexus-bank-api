package com.tavarez.nexusbank.exceptions;

public class InsufficientBalanceException extends BusinessException {
    public InsufficientBalanceException() {
        super("Insufficient balance for this transaction");
    }
}
