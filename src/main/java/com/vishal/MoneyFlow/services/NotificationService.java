package com.vishal.MoneyFlow.services;

import com.vishal.MoneyFlow.constant.TransactionType;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface NotificationService {
    public CompletableFuture<String> sendTransactionAlert(String accountNumber, TransactionType type, Double amount);
}
