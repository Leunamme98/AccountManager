package com.ing.accountmanager.service.impl;

import com.ing.accountmanager.dto.CompteDto;
import com.ing.accountmanager.enums.MoyenDePaiement;
import com.ing.accountmanager.enums.StatutCompte;
import com.ing.accountmanager.enums.StatutTransaction;
import com.ing.accountmanager.enums.TypeDeMouvement;
import com.ing.accountmanager.mapper.CompteMapper;
import com.ing.accountmanager.model.Compte;
import com.ing.accountmanager.exception.ResourceNotFoundException;
import com.ing.accountmanager.model.Membre;
import com.ing.accountmanager.model.Mouvement;
import com.ing.accountmanager.model.Transfert;
import com.ing.accountmanager.repository.*;
import com.ing.accountmanager.service.CompteService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompteServiceImpl implements CompteService {

    private final  CompteRepository compteRepository;
    private final CompteMapper compteMapper;
    private final MembreRepository membreRepository;
    private final MouvementRepository mouvementRepository;
    private final TransfertRepository transfertRepository;

    public CompteServiceImpl(CompteRepository compteRepository, CompteMapper compteMapper, MembreRepository membreRepository, MouvementRepository mouvementRepository, TransfertRepository transfertRepository) {
        this.compteRepository = compteRepository;
        this.compteMapper = compteMapper;
        this.membreRepository = membreRepository;
        this.mouvementRepository = mouvementRepository;
        this.transfertRepository = transfertRepository;
    }


    // Créer un compte pour un membre de l'association
    @Override
    public CompteDto saveCompte(CompteDto compteDto) {
        // Vérifier si le membre existe avant de créer le compte
        Membre membre = membreRepository.findById(compteDto.getProprietaireCompteId())
                .orElseThrow(() -> new ResourceNotFoundException("Le membre avec l'ID " + compteDto.getProprietaireCompteId() + " n'existe pas."));

        // Mapper le DTO en entité
        Compte compte = compteMapper.toEntity(compteDto);
        compte.setProprietaireCompte(membre);

        // Sauvegarder le compte en base
        Compte savedCompte = compteRepository.save(compte);

        // Création automatique de la première transaction (Dépôt initial)
        Double initialDeposit = savedCompte.getSolde();
        if (initialDeposit > 0) {
            Mouvement firstDeposit = new Mouvement();
            firstDeposit.setMontant(initialDeposit);
            firstDeposit.setDate(LocalDateTime.now());
            firstDeposit.setMoyenDePaiement(MoyenDePaiement.CASH);
            firstDeposit.setType(TypeDeMouvement.DEPOT);
            firstDeposit.setRaison("1er Dépôt");
            firstDeposit.setCompteSource(savedCompte);
            firstDeposit.setStatut(StatutTransaction.VALIDEE);

            mouvementRepository.save(firstDeposit);
        }

        // Retourner le DTO du compte sauvegardé
        return compteMapper.toDto(savedCompte);
    }

    // Obtenir un compte via l'ID du compte
    @Override
    public CompteDto getCompteById(String idCompte) {
        return compteRepository.findById(idCompte)
                .map(compteMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Le compte avec l'ID " + idCompte + " n'existe pas."));
    }


    // Obtenir tous les comptes des membres
    @Override
    public List<CompteDto> getAllComptes() {
        return compteRepository.findAll()
                .stream()
                .map(compteMapper::toDto)
                .collect(Collectors.toList());
    }


    // Obtenir les comptes par pagination
    @Override
    public Page<CompteDto> getAllComptesPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return compteRepository.findAll(pageable).map(compteMapper::toDto);
    }

    // Modifier les informations d'un compte
    @Override
    public CompteDto updateCompte(CompteDto compteDto) {
        // Vérifier si le compte existe en base
        Compte existingCompte = compteRepository.findById(compteDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Compte avec l'ID " + compteDto.getId() + " non trouvé."));


        // Mise à jour du numéro de compte (unique et non-null)
        if (compteDto.getNumero() != null && !compteDto.getNumero().isBlank()) {
            boolean numeroExists = compteRepository.existsByNumero(compteDto.getNumero());
            if (numeroExists) {
                throw new IllegalArgumentException("Le numéro de compte " + compteDto.getNumero() + " est déjà utilisé.");
            }
            existingCompte.setNumero(compteDto.getNumero());
        }

        // Mise à jour du solde (ne peut pas être négatif)
        if (compteDto.getSolde() != null && compteDto.getSolde() >= 0) {
            existingCompte.setSolde(compteDto.getSolde());
        } else if (compteDto.getSolde() != null) {
            throw new IllegalArgumentException("Le solde ne peut pas être négatif.");
        }


        // Mise à jour du statut du compte
        if (compteDto.getStatut() != null) {
            existingCompte.setStatut(compteDto.getStatut());
        }

        // Mise à jour du propriétaire du compte (Vérifier si le membre existe)
        if (compteDto.getProprietaireCompteId() != null) {
            Membre proprietaire = membreRepository.findById(compteDto.getProprietaireCompteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Le membre avec l'ID " + compteDto.getProprietaireCompteId() + " n'existe pas."));
            existingCompte.setProprietaireCompte(proprietaire);
        }

        // Sauvegarder les modifications
        Compte updatedCompte = compteRepository.save(existingCompte);

        // Retourner le compte mis à jour sous forme de DTO
        return compteMapper.toDto(updatedCompte);
    }


    // Supprimer un compte relier à un membre
    @Override
    public void deleteCompte(String id) {
        if (!compteRepository.existsById(id)) {
            throw  new ResourceNotFoundException("Le compte avec l'ID : " + id + " n'existe pas.");
        }
        compteRepository.deleteById(id);
    }

    // Activer un compte
    @Override
    public CompteDto activateCompte(String idCompte) {
        // Trouver le compte via l'ID
        Compte compte = compteRepository.findById(idCompte)
                .orElseThrow(() -> new ResourceNotFoundException("Compte avec ID " + idCompte + " n'existe pas."));

        if (compte.getStatut() == StatutCompte.ACTIF) {
            throw new RuntimeException("Le compte est déjà actif.");
        }

        compte.setStatut(StatutCompte.ACTIF);
        return compteMapper.toDto(compteRepository.save(compte));
    }

    // Désactiver un compte
    @Override
    public CompteDto deactivateCompte(String idCompte) {
        // Trouver le compte via l'ID
        Compte compte = compteRepository.findById(idCompte)
                .orElseThrow(() -> new ResourceNotFoundException("Compte avec ID " + idCompte + " n'existe pas."));

        if (compte.getStatut() == StatutCompte.INACTIF) {
            throw new RuntimeException("Le compte est déjà inactif.");
        }

        compte.setStatut(StatutCompte.INACTIF);
        return compteMapper.toDto(compteRepository.save(compte));
    }

    // Suspendre un compte
    @Override
    public CompteDto suspendreCompte(String idCompte) {
        // Trouver le compte via l'ID
        Compte compte = compteRepository.findById(idCompte)
                .orElseThrow(() -> new ResourceNotFoundException("Compte avec ID " + idCompte + " n'existe pas."));

        if (compte.getStatut() == StatutCompte.SUSPENDU) {
            throw new RuntimeException("Le compte est déjà suspendu.");
        }

        compte.setStatut(StatutCompte.SUSPENDU);
        return compteMapper.toDto(compteRepository.save(compte));
    }


    // Faire un dépot sur le compte
    @Override
    public CompteDto depositMoneyOnCompte(String idCompte,
                                          Double montant,
                                          MoyenDePaiement moyenDePaiement) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif.");
        }

        // Trouver le compte via l'ID
        Compte compte = compteRepository.findById(idCompte)
                .orElseThrow(() -> new ResourceNotFoundException("Compte avec ID " + idCompte + " n'existe pas."));

        // Créer un mouvement pour le dépôt
        Mouvement mouvement = new Mouvement();
        mouvement.setMontant(montant);
        mouvement.setDate(LocalDateTime.now());
        mouvement.setMoyenDePaiement(moyenDePaiement);
        mouvement.setType(TypeDeMouvement.DEPOT);
        mouvement.setRaison("Dépôt sur le compte");
        mouvement.setCompteSource(compte);

        // Vérification du statut du compte
        if (compte.getStatut() == StatutCompte.ACTIF) {
            // Si le compte est actif, procéder au dépôt et valider le mouvement
            compte.setSolde(compte.getSolde() + montant);
            mouvement.setStatut(StatutTransaction.VALIDEE);
        } else {
            // Si le compte est inactif ou suspendu, ne pas modifier le solde et annuler le mouvement
            mouvement.setStatut(StatutTransaction.ANNULEE);
        }

        // Sauvegarder le mouvement dans la base de données
        mouvementRepository.save(mouvement);

        // Si le compte est actif, on met à jour le solde du compte
        if (compte.getStatut() == StatutCompte.ACTIF) {
            compteRepository.save(compte);
        }

        // Retourner le compte mis à jour sous forme de DTO
        return compteMapper.toDto(compte);
    }

    // Faire un retrait d'un compte
    @Override
    public CompteDto withdrawMoneyOnCompte(String idCompte, Double montant, MoyenDePaiement moyenDePaiement) {

        // Trouver le compte via l'ID
        Compte compte = compteRepository.findById(idCompte)
                .orElseThrow(() -> new ResourceNotFoundException("Compte avec ID " + idCompte + " n'existe pas."));

        // Vérification que le montant est positif et inférieur au solde du compte
        if (montant <= 0 ) {
            throw new IllegalArgumentException("Le montant doit être positif et inférieur au solde du compte");
        }

        // Vérification du solde disponible pour le retrait
        if (compte.getSolde() < montant) {
            throw new IllegalArgumentException("Solde insuffisant pour effectuer ce retrait.");
        }

        // Créer un mouvement pour le retrait
        Mouvement mouvement = new Mouvement();
        mouvement.setMontant(montant);
        mouvement.setDate(LocalDateTime.now());
        mouvement.setMoyenDePaiement(moyenDePaiement);
        mouvement.setType(TypeDeMouvement.RETRAIT);
        mouvement.setRaison("Retrait du compte");
        mouvement.setCompteSource(compte);

        // Vérification du statut du compte
        if (compte.getStatut() == StatutCompte.ACTIF) {
            // Si le compte est actif, procéder au retrait et valider le mouvement
            compte.setSolde(compte.getSolde() - montant);
            mouvement.setStatut(StatutTransaction.VALIDEE);
        } else {
            // Si le compte est inactif ou suspendu, ne pas modifier le solde et annuler le mouvement
            mouvement.setStatut(StatutTransaction.ANNULEE);
        }

        // Sauvegarder le mouvement dans la base de données
        mouvementRepository.save(mouvement);

        // Si le compte est actif, on met à jour le solde du compte
        if (compte.getStatut() == StatutCompte.ACTIF) {
            compteRepository.save(compte);
        }

        // Retourner le compte mis à jour sous forme de DTO
        return compteMapper.toDto(compte);
    }


    // Effectuer un transfert d'argent vers un autre
    @Override
    public CompteDto transferMoneyToCompte(String idCompteSource, String idCompteDestination, Double montant, MoyenDePaiement moyenDePaiement) {

        // Vérification que le montant est positif
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif.");
        }

        // Trouver les comptes source et destination via leurs IDs
        Compte compteSource = compteRepository.findById(idCompteSource)
                .orElseThrow(() -> new ResourceNotFoundException("Compte source avec ID " + idCompteSource + " n'existe pas."));

        Compte compteDestination = compteRepository.findById(idCompteDestination)
                .orElseThrow(() -> new ResourceNotFoundException("Compte destination avec ID " + idCompteDestination + " n'existe pas."));

        // Vérification du statut des comptes
        if (compteSource.getStatut() != StatutCompte.ACTIF) {
            // Enregistrer le transfert avec le statut annulé et ne pas débiter le compte source
            createTransferRecord(compteSource, compteDestination, montant, "Compte source non actif", StatutTransaction.ANNULEE);
            throw new IllegalArgumentException("Le compte source doit être actif pour effectuer le transfert.");
        }

        if (compteDestination.getStatut() != StatutCompte.ACTIF) {
            // Enregistrer le transfert avec le statut annulé et ne pas créditer le compte destination
            createTransferRecord(compteSource, compteDestination, montant, "Compte destination non actif", StatutTransaction.ANNULEE);
            throw new IllegalArgumentException("Le compte destination doit être actif pour recevoir le transfert.");
        }

        // Vérification que le compte source a suffisamment de fonds
        if (compteSource.getSolde() < montant) {
            // Enregistrer le transfert avec le statut annulé et ne pas débiter le compte source
            createTransferRecord(compteSource, compteDestination, montant, "Solde insuffisant sur le compte source", StatutTransaction.ANNULEE);
            throw new IllegalArgumentException("Solde insuffisant sur le compte source.");
        }

        // Diminuer le solde du compte source
        compteSource.setSolde(compteSource.getSolde() - montant);

        // Créer le transfert (mouvement de type Transfert)
        Transfert transfert = new Transfert();
        transfert.setMontant(montant);
        transfert.setDate(LocalDateTime.now());
        transfert.setRaison("Transfert vers le compte : " + compteDestination.getNumero());
        transfert.setCompteSource(compteSource);
        transfert.setStatut(StatutTransaction.VALIDEE);
        transfert.setCompteDestination(compteDestination);

        // Sauvegarder le transfert dans la base de données
        transfertRepository.save(transfert);

        // Créer un dépôt pour le compte destination (mouvement de type Dépôt)
        Mouvement depot = new Mouvement();
        depot.setMontant(montant);
        depot.setDate(LocalDateTime.now());
        depot.setMoyenDePaiement(moyenDePaiement);
        depot.setType(TypeDeMouvement.DEPOT);
        depot.setRaison("Dépôt suite à un transfert du compte : " + compteSource.getNumero());
        depot.setCompteSource(compteDestination);
        depot.setStatut(StatutTransaction.VALIDEE);

        // Augmenter le solde du compte destination
        compteDestination.setSolde(compteDestination.getSolde() + montant);

        // Sauvegarder le dépôt dans la base de données
        mouvementRepository.save(depot);

        // Sauvegarder les deux comptes mis à jour dans la base de données
        compteRepository.save(compteSource);
        compteRepository.save(compteDestination);

        // Retourner le compte source mis à jour sous forme de DTO
        return compteMapper.toDto(compteSource);
    }

    // Méthode pour enregistrer un transfert avec un statut spécifique
    private void createTransferRecord(Compte compteSource, Compte compteDestination, Double montant, String raison, StatutTransaction statut) {
        Transfert transfert = new Transfert();
        transfert.setMontant(montant);
        transfert.setDate(LocalDateTime.now());
        transfert.setRaison(raison);
        transfert.setCompteSource(compteSource);
        transfert.setStatut(statut);
        transfert.setCompteDestination(compteDestination);

        // Sauvegarder le transfert avec le statut approprié
        transfertRepository.save(transfert);
    }


    // Obtenir le solde d'un compte via son ID
    @Override
    public Double checkBalanceCompte(String idCompte) {
        // Trouver le compte via l'ID
        Compte compte = compteRepository.findById(idCompte)
                .orElseThrow(() -> new ResourceNotFoundException("Compte avec ID " + idCompte + " n'existe pas."));

        // Retourner le solde du compte
        return compte.getSolde();
    }

    // Obtenir un compte grâce à son numero de compte
    @Override
    public CompteDto getCompteByNumero(String numeroCompte) {
        if (numeroCompte == null || numeroCompte.isBlank()) {
            throw new IllegalArgumentException("Le numéro de compte ne peut pas être vide ou null.");
        }

        return compteRepository.findByNumero(numeroCompte)
                .map(compteMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Le compte avec le numéro " + numeroCompte + " n'existe pas."));
    }

}
