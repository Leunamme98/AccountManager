package com.ing.accountmanager.repository;

import com.ing.accountmanager.model.Compte;
import com.ing.accountmanager.model.Transaction;
import io.micrometer.common.KeyValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
