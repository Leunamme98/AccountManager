package com.ing.accountmanager.controller;

import com.ing.accountmanager.dto.CompteDto;
import com.ing.accountmanager.enums.MoyenDePaiement;
import com.ing.accountmanager.exception.ResourceNotFoundException;
import com.ing.accountmanager.service.CompteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/account-manager/api/comptes")
public class CompteController {

    private final CompteService compteService;

    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }

    // Créer le compte d'un membre de l'association
    @PostMapping
    public ResponseEntity<CompteDto> saveCompte(@Valid @RequestBody CompteDto compteDto) {
        CompteDto compte = compteService.saveCompte(compteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(compte);
    }

    // Obtenir un compte via son ID
    @GetMapping("/{idCompte}")
    public ResponseEntity<CompteDto> getCompteById(@PathVariable String idCompte) {
        CompteDto compte = compteService.getCompteById(idCompte);
        return ResponseEntity.ok(compte);
    }

    // Obtenir les comptes de tous les membres
    @GetMapping
    public ResponseEntity<List<CompteDto>> getAllComptes() {
        List<CompteDto> comptes = compteService.getAllComptes();
        return ResponseEntity.ok(comptes);
    }

    // Modifier les informations d'un compte
    @PutMapping
    public ResponseEntity<CompteDto> updateCompte(@Valid @RequestBody CompteDto compteDto) {
        CompteDto compte = compteService.updateCompte(compteDto);
        return ResponseEntity.ok(compte);
    }

    // Obtenir tous les comptes par pagination
    @GetMapping("/pagination")
    public ResponseEntity<Page<CompteDto>> getAllComptes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<CompteDto> comptes = compteService.getAllComptesPaginated(page, size);
        return ResponseEntity.ok(comptes);
    }

    // Activer un compte
    @PutMapping("/activate/{idCompte}")
    public ResponseEntity<CompteDto> activateCompte(@PathVariable String idCompte) {
        CompteDto compte = compteService.activateCompte(idCompte);
        return ResponseEntity.ok(compte);
    }

    // Désactiver un compte
    @PutMapping("/deactivate/{idCompte}")
    public ResponseEntity<CompteDto> deactivateCompte(@PathVariable String idCompte) {
        CompteDto compte = compteService.deactivateCompte(idCompte);
        return ResponseEntity.ok(compte);
    }

    // Suspendre un compte
    @PutMapping("/suspend/{idCompte}")
    public ResponseEntity<CompteDto> suspendCompte(@PathVariable String idCompte) {
        CompteDto compte = compteService.suspendreCompte(idCompte);
        return ResponseEntity.ok(compte);
    }

    // Faire un dépôt sur un compte
    @PutMapping("/deposit/{idCompte}")
    public ResponseEntity<CompteDto> depositCompte(
            @PathVariable String idCompte,
            @RequestParam Double montant,
            @RequestParam MoyenDePaiement moyenDePaiement
    ) {
        CompteDto compte = compteService.depositMoneyOnCompte(idCompte, montant, moyenDePaiement);
        return ResponseEntity.ok(compte);
    }

    // Retirer de l'argent d'un compte
    @PutMapping("/{idCompte}/retrait")
    public ResponseEntity<CompteDto> withdrawMoney(
            @PathVariable String idCompte,
            @RequestParam Double montant,
            @RequestParam MoyenDePaiement moyenDePaiement) {

        try {
            // Appeler le service pour effectuer le retrait
            CompteDto updatedCompte = compteService.withdrawMoneyOnCompte(idCompte, montant, moyenDePaiement);
            return ResponseEntity.ok(updatedCompte); // Retourner le compte mis à jour
        } catch (IllegalArgumentException | ResourceNotFoundException e) {
            // Gestion des erreurs pour les cas comme solde insuffisant ou compte non trouvé
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Faire un transfert d'un compte à un compte
    @PutMapping("/{idCompteSource}/transfert/{idCompteDestination}")
    public ResponseEntity<CompteDto> transferMoney(
            @PathVariable String idCompteSource,
            @PathVariable String idCompteDestination,
            @RequestParam Double montant,
            @RequestParam MoyenDePaiement moyenDePaiement) {

        try {
            // Appeler le service pour effectuer le transfert
            CompteDto updatedCompteSource = compteService.transferMoneyToCompte(idCompteSource, idCompteDestination, montant, moyenDePaiement);
            return ResponseEntity.ok(updatedCompteSource); // Retourner le compte source mis à jour
        } catch (IllegalArgumentException | ResourceNotFoundException e) {
            // Gestion des erreurs pour les cas comme solde insuffisant ou comptes non trouvés
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Obtenir le solde d'un compte
    @GetMapping("/{idCompte}/solde")
    public ResponseEntity<Double> checkBalance(
            @PathVariable String idCompte) {

        try {
            // Appeler le service pour obtenir le solde du compte
            Double solde = compteService.checkBalanceCompte(idCompte);
            return ResponseEntity.ok(solde); // Retourner le solde du compte
        } catch (ResourceNotFoundException e) {
            // Gestion des erreurs pour le cas où le compte n'est pas trouvé
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Obtenir un compte grâce à son numero de compte
    @GetMapping("/numero/{numeroCompte}")
    public ResponseEntity<CompteDto> getCompteByNumero(
            @PathVariable String numeroCompte) {

        try {
            // Appeler le service pour obtenir le compte par son numéro
            CompteDto compteDto = compteService.getCompteByNumero(numeroCompte);
            return ResponseEntity.ok(compteDto); // Retourner le compte
        } catch (ResourceNotFoundException e) {
            // Gestion des erreurs pour le cas où le compte n'est pas trouvé
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            // Gestion des erreurs pour les entrées invalides
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
