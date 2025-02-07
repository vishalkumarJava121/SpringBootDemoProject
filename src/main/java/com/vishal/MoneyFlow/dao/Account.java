package com.vishal.MoneyFlow.dao;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Accounts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"aadharNo", "accountHolderName"}) // Enforce uniqueness
})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(unique = true, nullable = false,name = "account_number")
    private String accountNumber;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(unique = false, nullable = false,name = "aadhar_no")
    private String aadharNo;

    @Column(name = "balance")
    private Double balance;

    @Column(name ="account_creation_Date")
    private LocalDateTime accountCreationDate;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions;
}
