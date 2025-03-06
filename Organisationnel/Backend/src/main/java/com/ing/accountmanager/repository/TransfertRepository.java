package com.ing.accountmanager.repository;

import com.ing.accountmanager.model.Transfert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransfertRepository extends JpaRepository<Transfert, String> {
}
