package com.iamkr23.bankapplication.controller;

import com.iamkr23.bankapplication.dto.AccountRequestDTO;
import com.iamkr23.bankapplication.dto.AccountResponseDTO;
import com.iamkr23.bankapplication.dto.TransactionRequestDTO;
import com.iamkr23.bankapplication.dto.TransactionResponseDTO;
import com.iamkr23.bankapplication.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // Create account
    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@Valid @RequestBody AccountRequestDTO dto) {
        AccountResponseDTO response = accountService.createAccount(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get all accounts
    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    // Get balance
    @GetMapping("/{accNo}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable Integer accNo) {
        return ResponseEntity.ok(accountService.getBalance(accNo));
    }

    // Deposit
    @PostMapping("/{accNo}/deposit")
    public ResponseEntity<String> deposit(@PathVariable Integer accNo,
                                          @Valid @RequestBody TransactionRequestDTO dto) {
        accountService.deposit(accNo, dto.getAmount());
        return ResponseEntity.ok("Deposit successful");
    }

    // Withdraw
    @PostMapping("/{accNo}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable Integer accNo,
                                           @Valid @RequestBody TransactionRequestDTO dto) {
        accountService.withdraw(accNo, dto.getAmount());
        return ResponseEntity.ok("Withdrawal successful");
    }

//    // Transaction history
//    @GetMapping("/{accNo}/transactions")
//    public ResponseEntity<List<TransactionResponseDTO>> getTransactionHistory(@PathVariable Integer accNo) {
//        return ResponseEntity.ok(accountService.getTransactionHistory(accNo));
//    }

    @GetMapping("/{accNo}/transactions")
    public ResponseEntity<Page<TransactionResponseDTO>> getTransactionHistory(
            @PathVariable Integer accNo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(accountService.getTransactionHistory(accNo, page, size));
    }
}