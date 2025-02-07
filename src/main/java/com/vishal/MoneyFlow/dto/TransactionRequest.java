package com.vishal.MoneyFlow.dto;

import com.vishal.MoneyFlow.constant.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TransactionRequest implements Serializable {
    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @Positive(message = "Amount must be greater than zero")
    private Double amount;
}
