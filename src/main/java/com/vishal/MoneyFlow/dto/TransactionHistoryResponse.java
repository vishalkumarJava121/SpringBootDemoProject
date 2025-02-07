package com.vishal.MoneyFlow.dto;

import com.vishal.MoneyFlow.constant.TransactionType;
import com.vishal.MoneyFlow.dao.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryResponse implements Serializable {

    private Long id;
    private String type;
    private Double amount;
    private LocalDateTime timestamp;
}
