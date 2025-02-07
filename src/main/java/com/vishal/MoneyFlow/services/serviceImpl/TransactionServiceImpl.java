package com.vishal.MoneyFlow.services.serviceImpl;

import com.vishal.MoneyFlow.constant.TransactionType;
import com.vishal.MoneyFlow.dao.Account;
import com.vishal.MoneyFlow.dao.Transaction;
import com.vishal.MoneyFlow.dto.TransactionHistoryResponse;
import com.vishal.MoneyFlow.dto.TransactionRequest;
import com.vishal.MoneyFlow.dto.TransactionResponse;
import com.vishal.MoneyFlow.exception.AccountNotFoundException;
import com.vishal.MoneyFlow.exception.InsufficientBalanceException;
import com.vishal.MoneyFlow.respository.AccountRepository;
import com.vishal.MoneyFlow.respository.TransactionRepository;
import com.vishal.MoneyFlow.services.NotificationService;
import com.vishal.MoneyFlow.services.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private  AccountRepository accountRepository;

    @Autowired
    private  TransactionRepository transactionRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional

    @Override
    public TransactionResponse processTransaction(TransactionRequest request) throws AccountNotFoundException {
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (request.getType() == TransactionType.DEBIT && account.getBalance() < request.getAmount()) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        double newBalance = request.getType() == TransactionType.DEBIT
                ? account.getBalance() - request.getAmount()
                : account.getBalance() + request.getAmount();
        try {
            account.setBalance(newBalance);
            accountRepository.save(account);

            Transaction transaction = new Transaction(account, request.getType(), request.getAmount(), LocalDateTime.now());
            transactionRepository.save(transaction);

            // Asynchronously send a notification
            notificationService.sendTransactionAlert(account.getAccountNumber(), request.getType(), request.getAmount());
        }catch (Exception ex) {
            retryTransaction(request);
        }

        return new TransactionResponse("Transaction successful", newBalance);
    }

    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public TransactionResponse retryTransaction(TransactionRequest request) throws AccountNotFoundException {
        return processTransaction(request);
    }

    @Override
    public List<TransactionHistoryResponse> getTransactionHistory(Integer transactionId) {
        List<Transaction> transactionList = transactionRepository.findByTransactionId(transactionId);
        List<TransactionHistoryResponse> transactionResponseList = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            TransactionHistoryResponse transactionHistoryResponse = new TransactionHistoryResponse(transaction.getTransactionId(), transaction.getType().toString(), transaction.getAmount(), transaction.getTimestamp());
            transactionResponseList.add(transactionHistoryResponse);
        }
        return transactionResponseList;
    }

}
