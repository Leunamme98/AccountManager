package com.ing.accountmanager.repository;

import com.ing.accountmanager.model.Compte;
import com.ing.accountmanager.model.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompteRepository extends JpaRepository<Compte, String> {

    Optional<Compte> findByProprietaireCompte(Membre proprietaireCompte);
    boolean existsByNumero(String numero);
    Optional<Compte> findByNumero(String numero);
}
