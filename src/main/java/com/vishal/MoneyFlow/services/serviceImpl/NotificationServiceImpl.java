package com.vishal.MoneyFlow.services.serviceImpl;

import com.vishal.MoneyFlow.constant.TransactionType;
import com.vishal.MoneyFlow.services.NotificationService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Async("taskExecutor")
    public CompletableFuture<String> sendTransactionAlert(String accountNumber, TransactionType type, Double amount) {
        try {
            Thread.sleep(2000); // Simulate delay in sending notification
            System.out.println("Notification Sent: Account " + accountNumber + " - " + type + " of " + amount);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return CompletableFuture.failedFuture(e);
        }
        return CompletableFuture.completedFuture("Task Completed: " + accountNumber);
    }
}
