package com.ing.accountmanager.mapper;

import com.ing.accountmanager.dto.TransfertDto;
import com.ing.accountmanager.model.Compte;
import com.ing.accountmanager.model.Transfert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.boot.context.properties.bind.Name;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransfertMapper {

    @Mapping(source = "compteDestination", target = "compteDestinationId", qualifiedByName = "mapCompteToId")
    TransfertDto toDto(Transfert transfert);

    @Mapping(source = "compteDestinationId", target = "compteDestination", qualifiedByName = "mapIdToCompte" )
    Transfert toEntity(TransfertDto transfertDto);

    TransfertDto toDtoList(Transfert transfert);
    Transfert toEntityList(TransfertDto transfertDto);

    // Mapper un Compte en ID
    @Named("mapCompteToId")
    default String mapCompteToId(Compte compte) {
        return (compte != null) ? compte.getId() : null;
    }

    // Mapper un ID en Compte
    @Named("mapIdToCompte")
    default Compte mapIdToCompte(String id) {
        if (id == null) {
            return null;
        }
        Compte compte = new Compte();
        compte.setId(id);
        return compte;
    }
}
