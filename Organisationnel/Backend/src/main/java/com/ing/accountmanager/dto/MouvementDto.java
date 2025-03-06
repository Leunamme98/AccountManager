package com.ing.accountmanager.dto;

import com.ing.accountmanager.enums.MoyenDePaiement;
import com.ing.accountmanager.enums.StatutTransaction;
import com.ing.accountmanager.enums.TypeDeMouvement;

import java.time.LocalDateTime;

public class MouvementDto extends TransactionDto {

    private TypeDeMouvement type;
    private MoyenDePaiement moyenDePaiement;

    public MouvementDto() {
        super();
    }

    public MouvementDto(String id,
                        Double montant,
                        MoyenDePaiement moyenDePaiement,
                        LocalDateTime date,
                        String raison,
                        StatutTransaction statut,
                        String compteSourceId,
                        TypeDeMouvement type) {
        super(id, montant, date, statut, raison, compteSourceId);
        this.type = type;
        this.moyenDePaiement = moyenDePaiement;
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
