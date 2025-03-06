package com.ing.accountmanager.repository;

import com.ing.accountmanager.model.Mouvement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MouvementRepository extends JpaRepository<Mouvement, String> {
}
