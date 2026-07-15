package com.iamkr23.bankapplication.service;

import com.iamkr23.bankapplication.entity.Account;
import com.iamkr23.bankapplication.exception.AccountNotFoundException;
import com.iamkr23.bankapplication.exception.InsufficientBalanceException;
import com.iamkr23.bankapplication.repository.AccountRepository;
import com.iamkr23.bankapplication.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountService accountService;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        testAccount = new Account();
        testAccount.setAccountNo(1);
        testAccount.setName("Test User");
        testAccount.setEmail("test@example.com");
        testAccount.setBalance(1000.0);
    }

    @Test
    void getBalance_shouldReturnBalance_whenAccountExists() {
        // Arrange
        when(accountRepository.findById(1)).thenReturn(Optional.of(testAccount));
        // Act
        double balance = accountService.getBalance(1);
        // Assert
        assertEquals(1000.0, balance);
}
    @Test
    void getBalance_shouldThrowException_whenAccountNotFound() {
        // Arrange
        when(accountRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AccountNotFoundException.class, () -> accountService.getBalance(99));
    }

    @Test
    void withdraw_shouldThrowException_whenInsufficientBalance() {
        // Arrange
        when(accountRepository.findById(1)).thenReturn(Optional.of(testAccount));

        // Act & Assert
        assertThrows(InsufficientBalanceException.class, () -> accountService.withdraw(1, 5000.0));
    }

    @Test
    void deposit_shouldIncreaseBalance() {
        // Arrange
        when(accountRepository.findById(1)).thenReturn(Optional.of(testAccount));

        // Act
        accountService.deposit(1, 500.0);

        // Assert
        assertEquals(1500.0, testAccount.getBalance());
        verify(accountRepository, times(1)).save(testAccount);
    }

    @Test
    void withdraw_shouldDecreaseBalance_whenSufficientFunds() {
        // Arrange
        when(accountRepository.findById(1)).thenReturn(Optional.of(testAccount));

        // Act
        accountService.withdraw(1, 300.0);

        // Assert
        assertEquals(700.0, testAccount.getBalance());
        verify(accountRepository, times(1)).save(testAccount);
    }
}
