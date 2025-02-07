package com.vishal.MoneyFlow.exception;

import org.springframework.validation.Errors;

public class MethodArgumentNotValidException extends Throwable {
    public Errors getBindingResult() {
        return null;
    }
}
