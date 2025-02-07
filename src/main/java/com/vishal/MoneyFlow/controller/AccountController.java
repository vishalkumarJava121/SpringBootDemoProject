package com.vishal.MoneyFlow.controller;

import com.vishal.MoneyFlow.config.RateLimited;
import com.vishal.MoneyFlow.dto.AccountRequest;
import com.vishal.MoneyFlow.dto.AccountResponse;
import com.vishal.MoneyFlow.exception.AccountNotFoundException;
import com.vishal.MoneyFlow.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> checkBalance(@PathVariable String accountNumber) throws AccountNotFoundException {
        AccountResponse accountResponse = accountService.getAccount(accountNumber);
        return ResponseEntity.ok(accountResponse);
    }

    @GetMapping("/health")
    public ResponseEntity<String> checkHealth(){

        return ResponseEntity.ok("App is Working");
    }

    @PostMapping("/create")
    @RateLimited
    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid AccountRequest request) throws IOException {

        AccountResponse response = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/balance/{accountNumber}/{amount}")
    @RateLimited
    public ResponseEntity<String> updateAccountBalance(@PathVariable String accountNumber, @PathVariable Double amount) throws IOException {
        accountService.updateAccountBalance(accountNumber,amount);
        return ResponseEntity.status(HttpStatus.CREATED).body("Balance Updated Sucessfully");
    }
}
