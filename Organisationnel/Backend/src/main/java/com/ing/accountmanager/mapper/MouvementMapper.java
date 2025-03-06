package com.ing.accountmanager.mapper;

import com.ing.accountmanager.dto.MouvementDto;
import com.ing.accountmanager.model.Compte;
import com.ing.accountmanager.model.Mouvement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MouvementMapper {

    @Mapping(source = "compteSource", target = "compteSourceId", qualifiedByName = "mapCompteToId")
    MouvementDto toDto(Mouvement mouvement);

    @Mapping(source = "compteSourceId", target = "compteSource", qualifiedByName = "mapIdToCompte")
    Mouvement toEntity(MouvementDto mouvementDto);

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
