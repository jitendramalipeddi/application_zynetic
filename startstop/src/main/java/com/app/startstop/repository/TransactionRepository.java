package com.app.startstop.repository;

import com.app.startstop.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    Transaction findByTransactionId(int transactionId);
    Transaction findByChargerId(Long chargerId);
}
