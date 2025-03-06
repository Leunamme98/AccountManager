package com.ing.accountmanager.mapper;


import com.ing.accountmanager.dto.MembreDto;
import com.ing.accountmanager.model.Membre;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MembreMapper {

    MembreMapper INSTANCE = Mappers.getMapper(MembreMapper.class);

    MembreDto toDto(Membre membre);
    Membre toEntity(MembreDto membreDto);

    List<MembreDto> toDtoList(List<Membre> membres);
    List<Membre> toEntityList(List<MembreDto> membresDto);

}