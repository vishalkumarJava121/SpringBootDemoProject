package com.vishal.MoneyFlow.dao;

import com.vishal.MoneyFlow.constant.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="transaction_id")
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(name ="type")
    private TransactionType type;  // CREDIT, DEBIT

    @Column(name ="amount")
    private Double amount;

    @Column(name ="timestamp")
    private LocalDateTime timestamp;

    public Transaction(Account account, TransactionType type, Double amount, LocalDateTime now) {
        this.timestamp = now;
        this.account = account;
        this.type = type;
        this.amount = amount;

    }
}
