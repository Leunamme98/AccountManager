package com.ing.accountmanager.service;

import com.ing.accountmanager.dto.CompteDto;
import com.ing.accountmanager.dto.MembreDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MembreService {

    // Enregistrer un membre dans l'association
    MembreDto saveMembre(MembreDto membre);

    // Modifier un membre enregistrer
    MembreDto updateMembre(MembreDto membre);

    // Supprimer un membre de l'association par son id
    void deleteMembre(String idMembre);

    // Obtenir le compte d'un membre par son Id :
    CompteDto getMembreCompte(String idMembre);

    // Obtenir tous les membres
    List<MembreDto> getAllMembre();

    // Obtenir un membre par son id
    MembreDto getMembre(String idMembre);

    // Obtenir la liste des membres par pagination
    Page<MembreDto> getMembreByPagination(int page, int limit);
}
