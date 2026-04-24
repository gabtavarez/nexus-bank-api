package com.tavarez.nexusbank.exceptions;

public class InsufficientBalanceException extends BusinessException {
    public InsufficientBalanceException() {
        super("Insufficient Balance.");
    }
}
