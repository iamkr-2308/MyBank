package com.iamkr23.bankapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDTO {

    private String txnType;
    private double amount;
    private double balanceAfter;
    private LocalDateTime txnDate;
}