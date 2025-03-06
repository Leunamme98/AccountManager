package com.ing.accountmanager.model;

import com.ing.accountmanager.enums.StatutCompte;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String numero;

    private Double solde;
    private LocalDateTime dateCreation;

    @Enumerated(EnumType.STRING)
    private StatutCompte statut;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proprietaire_id", referencedColumnName = "id", nullable = false)
    private Membre proprietaireCompte;

    @OneToMany(mappedBy = "compteSource", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Transaction> transactionsEffectuees;

    @OneToMany(mappedBy = "compteDestination", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Transfert> transfertsRecus;

    // Constructeur par défaut
    public Compte() {}

    // Constructeur avec paramètres
    public Compte(String id, String numero, Double solde, LocalDateTime dateCreation, StatutCompte statut,
                  Membre proprietaireCompte, List<Transaction> transactionsEffectuees, List<Transfert> transfertsRecus) {
        this.id = id;
        this.numero = (numero != null) ? numero : "CPT-" + UUID.randomUUID().toString().substring(0, 8);
        this.solde = (solde != null) ? solde : 0.0;
        this.dateCreation = (dateCreation != null) ? dateCreation : LocalDateTime.now();
        this.statut = (statut != null) ? statut : StatutCompte.ACTIF;
        this.proprietaireCompte = proprietaireCompte;
        this.transactionsEffectuees = transactionsEffectuees;
        this.transfertsRecus = transfertsRecus;
    }

    @PrePersist
    public void generateDefaults() {

        if (this.numero == null) {
            this.numero = "CPT-" + UUID.randomUUID().toString().substring(0, 8);
        }

        if (this.solde == null) {
            this.solde = 0.0;
        }

        if (this.statut == null) {
            this.statut = StatutCompte.ACTIF;
        }

        if (this.dateCreation == null) {
            this.dateCreation = LocalDateTime.now();
        }
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public StatutCompte getStatut() {
        return statut;
    }

    public void setStatut(StatutCompte statut) {
        this.statut = statut;
    }

    public Membre getProprietaireCompte() {
        return proprietaireCompte;
    }

    public void setProprietaireCompte(Membre proprietaireCompte) {
        this.proprietaireCompte = proprietaireCompte;
    }

    public List<Transaction> getTransactionsEffectuees() {
        return transactionsEffectuees;
    }

    public void setTransactionsEffectuees(List<Transaction> transactionsEffectuees) {
        this.transactionsEffectuees = transactionsEffectuees;
    }

    public List<Transfert> getTransfertsRecus() {
        return transfertsRecus;
    }

    public void setTransfertsRecus(List<Transfert> transfertsRecus) {
        this.transfertsRecus = transfertsRecus;
    }
}
