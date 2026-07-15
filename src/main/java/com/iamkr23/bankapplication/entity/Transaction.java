package com.iamkr23.bankapplication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_no", nullable = false)
    private Account account;

    private String txnType;

    private double amount;

    private double balanceAfter;

    private LocalDateTime txnDate;

    @PrePersist
    protected void onCreate() {
        txnDate = LocalDateTime.now();
    }
}