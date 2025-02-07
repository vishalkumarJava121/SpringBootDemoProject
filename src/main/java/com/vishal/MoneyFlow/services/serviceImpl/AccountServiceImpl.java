package com.vishal.MoneyFlow.services.serviceImpl;

import com.vishal.MoneyFlow.constant.TransactionType;
import com.vishal.MoneyFlow.dao.Account;
import com.vishal.MoneyFlow.dao.Transaction;
import com.vishal.MoneyFlow.dto.AccountRequest;
import com.vishal.MoneyFlow.dto.AccountResponse;
import com.vishal.MoneyFlow.exception.AccountAlreadyExistException;
import com.vishal.MoneyFlow.exception.AccountNotFoundException;
import com.vishal.MoneyFlow.respository.AccountRepository;
import com.vishal.MoneyFlow.services.AccountService;
import jakarta.persistence.Version;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {



    @Autowired
    AccountRepository accountRepository;

    @Override
    public double checkBalance(String accountNumber) {
        return 0;
    }

    @Transactional
    @Override
    public AccountResponse createAccount(AccountRequest request) {
        Optional<Account> existingAccount = accountRepository.findByAadharNoAndAccountHolderName(request.getAadharNumber(), request.getAccountHolderName());

        if (existingAccount.isPresent()) {
            throw new AccountAlreadyExistException("An account already exists for Aadhar: " + request.getAadharNumber() +
                    " and Name: " + request.getAccountHolderName());
        }
        try {
            if (existingAccount.isEmpty()) {
                Account newAccount = new Account();
                newAccount.setAccountHolderName(request.getAccountHolderName());
                newAccount.setAccountNumber("ACC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                newAccount.setBalance(request.getDepositAmount());
                newAccount.setAccountCreationDate(LocalDateTime.now());
                newAccount.setAadharNo(request.getAadharNumber());
                Transaction transaction = new Transaction(newAccount, TransactionType.DEBIT, request.getDepositAmount(), LocalDateTime.now());
                List<Transaction> transactionList = new ArrayList<>();
                transactionList.add(transaction);
                newAccount.setTransactions(transactionList);
                try {
                    accountRepository.save(newAccount);
                }catch (OptimisticLockingFailureException e) {
                    throw new RuntimeException("Update failed due to concurrent modification. Please retry.");
                }

            }
        }catch (Exception ex)
        {
            throw new RuntimeException("Error While creating New  Account");
        }
        return new AccountResponse("Account Created", request.getAccountHolderName(), request.getDepositAmount());

    }


    @Cacheable(value = "accounts", key = "#accountNumber")
    public AccountResponse getAccount(String accountNumber)  {
        System.out.println("Fetching from DB...");
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);

        if (account.isEmpty()) {
            throw new AccountNotFoundException(" Account Number doesn't exist ::" + accountNumber );
        }else {
            return new AccountResponse(account.get().getAccountNumber(), account.get().getAccountHolderName(), account.get().getBalance());
        }
    }



    @Version
    @CacheEvict(value = "accounts", key = "#accountNumber")
    public void updateAccountBalance(String accountNumber, Double newBalance)  {
        System.out.println("updateAccountBalance from DB...");
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        if (account.isEmpty()) {
            throw new AccountNotFoundException(" Account Number doesn't exist ::" + accountNumber);
        }else {
            account.get().setBalance(newBalance);
            try {
                accountRepository.save(account.get());
            }catch (OptimisticLockingFailureException e) {
                throw new RuntimeException("Update failed due to concurrent modification. Please retry.");
            }
        }
    }
}
