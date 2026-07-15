package com.iamkr23.bankapplication.exception;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(Integer accNo) {
        super("Insufficient balance in account: " + accNo);
    }
}