package com.iamkr23.bankapplication.service;

import com.iamkr23.bankapplication.dto.AccountRequestDTO;
import com.iamkr23.bankapplication.dto.AccountResponseDTO;
import com.iamkr23.bankapplication.dto.TransactionResponseDTO;
import com.iamkr23.bankapplication.entity.Account;
import com.iamkr23.bankapplication.entity.Transaction;
import com.iamkr23.bankapplication.exception.AccountNotFoundException;
import com.iamkr23.bankapplication.exception.InsufficientBalanceException;
import com.iamkr23.bankapplication.repository.AccountRepository;
import com.iamkr23.bankapplication.repository.TransactionRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    // Create a new account
    public AccountResponseDTO createAccount(AccountRequestDTO dto) {
        Account account = new Account();
        account.setName(dto.getName());
        account.setEmail(dto.getEmail());
        account.setBalance(dto.getBalance());

        Account saved = accountRepository.save(account);
        return toResponseDTO(saved);
    }

    // Get all accounts
    public List<AccountResponseDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get balance
    public double getBalance(Integer accNo) {
        Account account = accountRepository.findById(accNo)
                .orElseThrow(() -> new AccountNotFoundException(accNo));
        return account.getBalance();
    }

    // Deposit — atomic operation
    @Transactional
    public void deposit(Integer accNo, double amount) {
        Account account = accountRepository.findById(accNo)
                .orElseThrow(() -> new AccountNotFoundException(accNo));

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        Transaction txn = new Transaction();
        txn.setAccount(account);
        txn.setTxnType("DEPOSIT");
        txn.setAmount(amount);
        txn.setBalanceAfter(account.getBalance());
        transactionRepository.save(txn);
    }

    // Withdraw — atomic operation
    @Transactional
    public void withdraw(Integer accNo, double amount) {
        Account account = accountRepository.findById(accNo)
                .orElseThrow(() -> new AccountNotFoundException(accNo));

        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException(accNo);
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        Transaction txn = new Transaction();
        txn.setAccount(account);
        txn.setTxnType("WITHDRAW");
        txn.setAmount(amount);
        txn.setBalanceAfter(account.getBalance());
        transactionRepository.save(txn);
    }

    // Transaction history
//    public List<TransactionResponseDTO> getTransactionHistory(Integer accNo) {
//        Account account = accountRepository.findById(accNo)
//                .orElseThrow(() -> new AccountNotFoundException(accNo));
//
//        return transactionRepository.findByAccountOrderByTxnDateDesc(account)
//                .stream()
//                .map(txn -> new TransactionResponseDTO(
//                        txn.getTxnType(),
//                        txn.getAmount(),
//                        txn.getBalanceAfter(),
//                        txn.getTxnDate()
//                ))
//                .toList();
//    }

    public Page<TransactionResponseDTO> getTransactionHistory(Integer accNo, int page, int size) {
        Account account = accountRepository.findById(accNo)
                .orElseThrow(() -> new AccountNotFoundException(accNo));

        Pageable pageable = PageRequest.of(page, size);

        Page<Transaction> transactionPage = transactionRepository.findByAccountOrderByTxnDateDesc(account, pageable);

        return transactionPage.map(txn -> new TransactionResponseDTO(
                txn.getTxnType(),
                txn.getAmount(),
                txn.getBalanceAfter(),
                txn.getTxnDate()
        ));
    }

    // Helper method — converts Entity to DTO
    private AccountResponseDTO toResponseDTO(Account account) {
        return new AccountResponseDTO(
                account.getAccountNo(),
                account.getName(),
                account.getEmail(),
                account.getBalance()
        );
    }
}