package com.ing.accountmanager.repository;

import com.ing.accountmanager.model.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembreRepository extends JpaRepository<Membre, String> {
}
