package com.iamkr23.bankapplication.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Integer accNo) {
        super("Account not found with account number: " + accNo);
    }
}