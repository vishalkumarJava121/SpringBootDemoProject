package com.vishal.MoneyFlow.controller;

import com.vishal.MoneyFlow.config.RateLimited;
import com.vishal.MoneyFlow.dto.AccountRequest;
import com.vishal.MoneyFlow.dto.AccountResponse;
import com.vishal.MoneyFlow.services.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Double> checkBalance(@PathVariable String accountNumber){
        Double response = accountService.checkBalance(accountNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> checkHealth(){

        return ResponseEntity.ok("App is Working");
    }

    @PostMapping("/create")
    @RateLimited
    public ResponseEntity<AccountResponse> createAccount(@RequestBody  AccountRequest request) throws IOException {

        AccountResponse response = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
