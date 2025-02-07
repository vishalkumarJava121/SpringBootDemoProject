package com.vishal.MoneyFlow.services;

import com.vishal.MoneyFlow.constant.TransactionType;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    public void sendTransactionAlert(String accountNumber, TransactionType type, Double amount);
}
