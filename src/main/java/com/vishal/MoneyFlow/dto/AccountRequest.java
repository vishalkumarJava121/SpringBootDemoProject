package com.vishal.MoneyFlow.dto;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class AccountRequest  {


    @NotBlank(message = "Account holder name is required")
    private String accountHolderName;

    private String accountNumber;

    @NotBlank(message = "Aaadhar Number  is required")
    private String aadharNumber;

    private Double depositAmount;



}
