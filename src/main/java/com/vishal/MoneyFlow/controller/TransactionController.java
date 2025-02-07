package com.vishal.MoneyFlow.controller;

import com.vishal.MoneyFlow.dto.TransactionHistoryResponse;
import com.vishal.MoneyFlow.dto.TransactionRequest;
import com.vishal.MoneyFlow.dto.TransactionResponse;
import com.vishal.MoneyFlow.exception.AccountNotFoundException;
import com.vishal.MoneyFlow.services.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> processTransaction(@RequestBody @Valid TransactionRequest request) throws AccountNotFoundException {
        TransactionResponse response = transactionService.processTransaction(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<TransactionHistoryResponse>> getTransactionHistory(@PathVariable Integer transactionId) {
        List<TransactionHistoryResponse> transactions = transactionService.getTransactionHistory(transactionId);
        return ResponseEntity.ok(transactions);
    }
}
