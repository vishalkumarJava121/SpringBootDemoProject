package com.vishal.MoneyFlow.services;

import com.vishal.MoneyFlow.dto.AccountRequest;
import com.vishal.MoneyFlow.dto.AccountResponse;
import com.vishal.MoneyFlow.exception.AccountNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    public double checkBalance(String accountNumber);

    AccountResponse createAccount(AccountRequest request);
    public void updateAccountBalance(String accountNumber, Double newBalance) throws AccountNotFoundException;
    public AccountResponse getAccount(String accountNumber) throws AccountNotFoundException;
}
