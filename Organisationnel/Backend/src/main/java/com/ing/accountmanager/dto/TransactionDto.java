package com.ing.accountmanager.dto;

import com.ing.accountmanager.enums.StatutTransaction;
import java.time.LocalDateTime;

public class TransactionDto {

    private String id;
    private Double montant;
    private LocalDateTime date;
    private StatutTransaction statut;
    private String raison;
    private String compteSourceId;

    public TransactionDto() {
    }

    public TransactionDto(String id,
                          Double montant,
                          LocalDateTime date,
                          StatutTransaction statut,
                          String raison,
                          String compteSourceId) {
        this.id = id;
        this.montant = montant;
        this.date = date;
        this.raison = raison;
        this.statut = statut;
        this.compteSourceId = compteSourceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }


    public String getRaison(){
        return raison;
    }

    public void setRaison(String raison){
        this.raison = raison;
    }


    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public StatutTransaction getStatut() {
        return statut;
    }

    public void setStatut(StatutTransaction statut) {
        this.statut = statut;
    }

    public String getCompteSourceId() {
        return compteSourceId;
    }

    public void setCompteSourceId(String compteSourceId) {
        this.compteSourceId = compteSourceId;
    }
}


