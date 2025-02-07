package com.vishal.MoneyFlow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse implements Serializable {

    private String message;
    private Double updatedBalance;
}
