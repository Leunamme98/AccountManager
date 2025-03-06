package com.ing.accountmanager.service;

import com.ing.accountmanager.dto.CompteDto;
import com.ing.accountmanager.enums.MoyenDePaiement;
import com.ing.accountmanager.enums.StatutTransaction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CompteService {

    // Créer un compte
    CompteDto saveCompte(CompteDto compteDto);

    // Récupérer un compte par ID
    CompteDto getCompteById(String id);

    // Récupérer tous les comptes
    List<CompteDto> getAllComptes();

    // Récupérer les comptes avec pagination
    Page<CompteDto> getAllComptesPaginated(int page, int size);

    // Mettre à jour un compte
    CompteDto updateCompte(CompteDto compteDto);

    // Supprimer un compte
    void deleteCompte(String id);

    // Activer un compte
    CompteDto activateCompte(String id);

    // Désactiver un compte
    CompteDto deactivateCompte(String id);

    // Suspendre un compte
    CompteDto suspendreCompte(String id);

    // Effectuer un dépôt d'argent sur un compte
    CompteDto depositMoneyOnCompte(String id, Double montant, MoyenDePaiement moyenDePaiement);

    // Effectuer un retrait d'argent sur un compte
    CompteDto withdrawMoneyOnCompte(String id, Double montant, MoyenDePaiement moyenDePaiement);

    // Effectuer un transfert d'argent vers un autre
    CompteDto transferMoneyToCompte(String idCompteSource, String idCompteDestination, Double montant, MoyenDePaiement moyenDePaiement);

    // Vérifier le solde d'un compte
    Double checkBalanceCompte(String id);

    // Obtenir un compte grace à son numero de compte
    public CompteDto getCompteByNumero(String numeroCompte);
}
