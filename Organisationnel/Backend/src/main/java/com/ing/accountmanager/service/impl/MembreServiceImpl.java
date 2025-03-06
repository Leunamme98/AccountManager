package com.ing.accountmanager.service.impl;

import com.ing.accountmanager.dto.CompteDto;
import com.ing.accountmanager.dto.MembreDto;
import com.ing.accountmanager.exception.ResourceNotFoundException;
import com.ing.accountmanager.mapper.CompteMapper;
import com.ing.accountmanager.mapper.MembreMapper;
import com.ing.accountmanager.model.Compte;
import com.ing.accountmanager.model.Membre;
import com.ing.accountmanager.repository.MembreRepository;
import com.ing.accountmanager.repository.CompteRepository;
import com.ing.accountmanager.service.MembreService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class MembreServiceImpl implements MembreService {

    private final MembreRepository membreRepository;
    private final CompteRepository compteRepository;
    private final MembreMapper membreMapper;
    private final CompteMapper compteMapper;

    public MembreServiceImpl(MembreRepository membreRepository,
                             CompteRepository compteRepository,
                             MembreMapper membreMapper,
                             CompteMapper compteMapper) {
        this.membreRepository = membreRepository;
        this.compteRepository = compteRepository;
        this.membreMapper = membreMapper;
        this.compteMapper = compteMapper;
    }

    // Enregistrer un membre dans l'association
    @Override
    public MembreDto saveMembre(MembreDto membreDto) {
        Membre membre = membreMapper.toEntity(membreDto);
        return membreMapper.toDto(membreRepository.save(membre));
    }

    // Modifier un membre existant
    @Override
    public MembreDto updateMembre(MembreDto membreDto) {
        Membre existingMembre = membreRepository.findById(membreDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Membre avec ID " + membreDto.getId() + " n'existe pas"));

        // Mettre à jour les champs nécessaires
        existingMembre.setNom(membreDto.getNom());
        existingMembre.setPrenom(membreDto.getPrenom());
        existingMembre.setSexe(membreDto.getSexe());
        existingMembre.setDateDeNaissance(membreDto.getDateDeNaissance());
        existingMembre.setEmail(membreDto.getEmail());
        existingMembre.setAdresse(membreDto.getAdresse());
        existingMembre.setPathPhoto(membreDto.getPathPhoto());

        return membreMapper.toDto(membreRepository.save(existingMembre));
    }

    // Supprimer un membre
    @Override
    public void deleteMembre(String idMembre) {
        if (!membreRepository.existsById(idMembre)) {
            throw new ResourceNotFoundException("Membre avec ID " + idMembre + " n'existe pas");
        }
        membreRepository.deleteById(idMembre);
    }

    // Obtenir les informations de compte d'un membre
    @Override
    public CompteDto getMembreCompte(String idMembre) {
        Membre membreExist = membreRepository.findById(idMembre)
                .orElseThrow(() -> new ResourceNotFoundException("Membre avec ID " + idMembre + " n'existe pas"));

        Compte compte = compteRepository.findByProprietaireCompte(membreExist)
                .orElseThrow(() -> new ResourceNotFoundException("Le compte pour le membre avec ID " + idMembre + " n'existe pas"));

        return compteMapper.toDto(compte);
    }

    // Obtenir la liste de tous les membres
    @Override
    public List<MembreDto> getAllMembre() {
        return membreMapper.toDtoList(membreRepository.findAll());
    }

    // Obtenir un membre par son ID
    @Override
    public MembreDto getMembre(String idMembre) {
        return membreMapper.toDto(
                membreRepository.findById(idMembre)
                        .orElseThrow(() -> new ResourceNotFoundException("Membre avec ID " + idMembre + " n'existe pas"))
        );
    }

    // Obtenir la liste des membres avec pagination
    @Override
    public Page<MembreDto> getMembreByPagination(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return membreRepository.findAll(pageable).map(membreMapper::toDto);
    }
}
