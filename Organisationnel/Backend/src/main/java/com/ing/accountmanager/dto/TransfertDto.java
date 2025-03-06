package com.ing.accountmanager.dto;

import com.ing.accountmanager.enums.StatutTransaction;

import java.time.LocalDateTime;

public class TransfertDto extends TransactionDto {

    private String compteDestinationId;

    public TransfertDto() {
        super();
    }

    public TransfertDto(String id,
                        Double montant,
                        LocalDateTime date,
                        String raison,
                        StatutTransaction statut,
                        String compteSourceId,
                        String compteDestinationId) {
        super(id, montant, date, statut, raison, compteSourceId);
        this.compteDestinationId = compteDestinationId;
    }

    public String getCompteDestinationId() {
        return compteDestinationId;
    }

    public void setCompteDestinationId(String compteDestinationId) {
        this.compteDestinationId = compteDestinationId;
    }

}
