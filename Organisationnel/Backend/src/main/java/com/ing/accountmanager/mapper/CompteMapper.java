package com.ing.accountmanager.mapper;

import com.ing.accountmanager.dto.CompteDto;
import com.ing.accountmanager.model.Compte;
import com.ing.accountmanager.model.Membre;
import com.ing.accountmanager.model.Transaction;
import com.ing.accountmanager.model.Transfert;
import com.ing.accountmanager.enums.StatutCompte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompteMapper {

    // Conversion de l'entité vers le DTO
    @Mapping(source = "proprietaireCompte", target = "proprietaireCompteId", qualifiedByName = "mapMembreToId")
    @Mapping(source = "transactionsEffectuees", target = "transactionsEffectueesIds", qualifiedByName = "mapTransactionListToIds")
    @Mapping(source = "transfertsRecus", target = "transfertsRecusIds", qualifiedByName = "mapTransfertListToIds")
    CompteDto toDto(Compte compte);

    // Conversion du DTO vers l'entité
    @Mapping(source = "proprietaireCompteId", target = "proprietaireCompte", qualifiedByName = "mapIdToMembre")
    @Mapping(source = "transactionsEffectueesIds", target = "transactionsEffectuees", qualifiedByName = "mapIdsToTransactions")
    @Mapping(source = "transfertsRecusIds", target = "transfertsRecus", qualifiedByName = "mapIdsToTransferts")
    Compte toEntity(CompteDto compteDto);

    // Conversion de listes
    List<CompteDto> toDtoList(List<Compte> comptes);
    List<Compte> toEntityList(List<CompteDto> compteDtos);

    // Mapper un Membre vers son ID
    @Named("mapMembreToId")
    default String mapMembreToId(Membre membre) {
        return (membre != null) ? membre.getId() : null;
    }

    // Mapper un ID vers un Membre
    @Named("mapIdToMembre")
    default Membre mapIdToMembre(String id) {
        if (id == null) {
            return null;
        }
        Membre membre = new Membre();
        membre.setId(id);
        return membre;
    }

    // Convertir une liste de Transactions en une liste d'IDs
    @Named("mapTransactionListToIds")
    default List<String> mapTransactionListToIds(List<Transaction> transactions) {
        if (transactions == null) {
            return null;
        }
        return transactions.stream()
                .map(Transaction::getId)
                .collect(Collectors.toList());
    }

    // Convertir une liste de Transferts en une liste d'IDs
    @Named("mapTransfertListToIds")
    default List<String> mapTransfertListToIds(List<Transfert> transferts) {
        if (transferts == null) {
            return null;
        }
        return transferts.stream()
                .map(Transfert::getId)
                .collect(Collectors.toList());
    }

    //  Convertir une liste d'IDs en liste de Transactions
    @Named("mapIdsToTransactions")
    default List<Transaction> mapIdsToTransactions(List<String> ids) {
        if (ids == null) {
            return null;
        }
        return ids.stream().map(id -> {
            Transaction transaction = new Transaction();
            transaction.setId(id);
            return transaction;
        }).collect(Collectors.toList());
    }

    // Convertir une liste d'IDs en liste de Transferts
    @Named("mapIdsToTransferts")
    default List<Transfert> mapIdsToTransferts(List<String> ids) {
        if (ids == null) {
            return null;
        }
        return ids.stream().map(id -> {
            Transfert transfert = new Transfert();
            transfert.setId(id);
            return transfert;
        }).collect(Collectors.toList());
    }
}
