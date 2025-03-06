package com.ing.accountmanager.model;

import com.ing.accountmanager.enums.StatutTransaction;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Transfert extends Transaction {

    @ManyToOne
    private Compte compteDestination;

    public Transfert() {
        super();
    }

    public Transfert(String id,
                     Double montant,
                     LocalDateTime date,
                     String raison,
                     Compte compteSource,
                     StatutTransaction statut,
                     Compte compteDestination) {
        super(id, montant, date, raison, compteSource, statut);
        this.compteDestination = compteDestination;
    }

    public Compte getCompteDestination() {
        return compteDestination;
    }

    public void setCompteDestination(Compte compteDestination) {
        this.compteDestination = compteDestination;
    }
}
