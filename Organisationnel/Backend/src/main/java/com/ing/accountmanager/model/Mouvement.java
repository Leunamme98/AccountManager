package com.ing.accountmanager.model;

import com.ing.accountmanager.enums.MoyenDePaiement;
import com.ing.accountmanager.enums.StatutTransaction;
import com.ing.accountmanager.enums.TypeDeMouvement;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

@Entity
public class Mouvement extends Transaction {

    @Enumerated(EnumType.STRING)
    private TypeDeMouvement type;

    @Enumerated(EnumType.STRING)
    private MoyenDePaiement moyenDePaiement;


    public Mouvement() {
        super();
    }

    public Mouvement(String id,
                     Double montant,
                     MoyenDePaiement moyenDePaiement,
                     LocalDateTime date,
                     String raison,
                     Compte compteSource,
                     StatutTransaction statut,
                     TypeDeMouvement type) {
        super(id, montant, date, raison, compteSource, statut);
        this.moyenDePaiement = moyenDePaiement;
        this.type = type;
    }


    // Getters and setters
    public TypeDeMouvement getType() {
        return type;
    }

    public void setType(TypeDeMouvement type) {
        this.type = type;
    }

    public MoyenDePaiement getMoyenDePaiement() {
        return moyenDePaiement;
    }

    public void setMoyenDePaiement(MoyenDePaiement moyenDePaiement) {
        this.moyenDePaiement = moyenDePaiement;
    }


}
