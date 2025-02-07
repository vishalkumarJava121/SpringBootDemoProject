package com.vishal.MoneyFlow.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse implements Serializable {

    private String accountNumber;
    private String accountHolderName;
    private Double balance;
}
