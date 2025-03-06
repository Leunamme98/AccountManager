package com.ing.accountmanager.model;

import com.ing.accountmanager.enums.StatutTransaction;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Transaction {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    private Double montant;
    private LocalDateTime date;


    private String raison;

    @JoinColumn(name = "compte_source_id", nullable = false)
    @ManyToOne
    private Compte compteSource;

    @Enumerated(EnumType.STRING)
    private StatutTransaction statut;

    public Transaction() {
    }

    public Transaction(String id,
                       Double montant,
                       LocalDateTime date,
                       String raison,
                       Compte compteSource,
                       StatutTransaction statut) {
        this.id = id;
        this.montant = montant;
        this.date = date;
        this.raison = raison;
        this.compteSource = compteSource;
        this.statut = statut;
    }

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }

        if (this.date == null) {
            this.date = LocalDateTime.now();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRaison(){return raison;}

    public void setRaison(String reason){this.raison = reason;}

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }


    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Compte getCompteSource() {
        return compteSource;
    }

    public void setCompteSource(Compte compteSource) {
        this.compteSource = compteSource;
    }

    public StatutTransaction getStatut() {
        return statut;
    }

    public void setStatut(StatutTransaction statut) {
        this.statut = statut;
    }
}
