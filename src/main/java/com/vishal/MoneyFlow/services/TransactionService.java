package com.vishal.MoneyFlow.services;

import com.vishal.MoneyFlow.dto.TransactionHistoryResponse;
import com.vishal.MoneyFlow.dto.TransactionRequest;
import com.vishal.MoneyFlow.dto.TransactionResponse;
import com.vishal.MoneyFlow.exception.AccountNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
    public TransactionResponse processTransaction(TransactionRequest request) throws AccountNotFoundException;

    List<TransactionHistoryResponse> getTransactionHistory(Integer transactionId);
}
