package com.vishal.MoneyFlow.respository;

import com.vishal.MoneyFlow.dao.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByTransactionId(Integer transactionId);
}
