package com.ing.accountmanager.dto;

import com.ing.accountmanager.enums.StatutCompte;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;
import java.util.List;

public class CompteDto {

    private String id;
    private String numero;

    @PositiveOrZero(message = "Le solde ne peut pas être négatif")
    private Double solde;

    private LocalDateTime dateCreation;

    @NotNull(message = "Le statut du compte est obligatoire")
    @Enumerated(EnumType.STRING)
    private StatutCompte statut;

    @NotBlank(message = "L'ID du propriétaire du compte est obligatoire")
    private String proprietaireCompteId;

    private List<String> transactionsEffectueesIds;
    private List<String> transfertsRecusIds;


    public CompteDto() {
    }

    public CompteDto(String id,
                     String numero,
                     Double solde,
                     LocalDateTime dateCreation,
                     StatutCompte statut,
                     String proprietaireCompteId,
                     List<String> transactionsEffectueesIds,
                     List<String> transfertsRecusIds) {
        this.id = id;
        this.numero = numero;
        this.solde = solde;
        this.dateCreation = dateCreation;
        this.statut = statut;
        this.proprietaireCompteId = proprietaireCompteId;
        this.transactionsEffectueesIds = transactionsEffectueesIds;
        this.transfertsRecusIds = transfertsRecusIds;
    }

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

    public String getProprietaireCompteId() {
        return proprietaireCompteId;
    }

    public void setProprietaireCompteId(String proprietaireCompteId) {
        this.proprietaireCompteId = proprietaireCompteId;
    }

    public List<String> getTransactionsEffectueesIds() {
        return transactionsEffectueesIds;
    }

    public void setTransactionsEffectueesIds(List<String> transactionsEffectueesIds) {
        this.transactionsEffectueesIds = transactionsEffectueesIds;
    }

    public List<String> getTransfertsRecusIds() {
        return transfertsRecusIds;
    }

    public void setTransfertsRecusIds(List<String> transfertsRecusIds) {
        this.transfertsRecusIds = transfertsRecusIds;
    }
}
