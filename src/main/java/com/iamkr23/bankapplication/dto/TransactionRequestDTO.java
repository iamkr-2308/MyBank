package com.iamkr23.bankapplication.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TransactionRequestDTO {

    @Positive(message = "Amount must be greater than zero")
    private double amount;
}