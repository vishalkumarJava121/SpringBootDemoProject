package com.vishal.MoneyFlow.exception;

public class AccountAlreadyExistException extends RuntimeException{
    public AccountAlreadyExistException(String accountAlreadyCreated) {
        super(accountAlreadyCreated);
    }
}
