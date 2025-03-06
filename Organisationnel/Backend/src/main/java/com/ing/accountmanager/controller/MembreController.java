package com.ing.accountmanager.controller;

import com.ing.accountmanager.dto.CompteDto;
import com.ing.accountmanager.dto.MembreDto;
import com.ing.accountmanager.service.MembreService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account-manager/api/membres")
@CrossOrigin(origins = "*")
public class MembreController {

    private final MembreService membreService;

    public MembreController(MembreService membreService) {
        this.membreService = membreService;
    }

    // Enregistrer un nouveau membre
    @PostMapping
    public ResponseEntity<MembreDto> saveMembre(@Valid @RequestBody MembreDto membreDto) {
        MembreDto savedMembre = membreService.saveMembre(membreDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMembre);
    }

    // Modifier un membre existant
    @PutMapping()
    public ResponseEntity<MembreDto> updateMembre(@Valid @RequestBody MembreDto membreDto) {
        MembreDto updatedMembre = membreService.updateMembre(membreDto);
        return ResponseEntity.ok(updatedMembre);
    }

    // Supprimer un membre
    @DeleteMapping("/{idMembre}")
    public ResponseEntity<Void> deleteMembre(@PathVariable String idMembre) {
        membreService.deleteMembre(idMembre);
        return ResponseEntity.noContent().build();
    }

    // Récupérer un membre par son ID
    @GetMapping("/{idMembre}")
    public ResponseEntity<MembreDto> getMembre(@PathVariable String idMembre) {
        MembreDto membreDto = membreService.getMembre(idMembre);
        return ResponseEntity.ok(membreDto);
    }

    // Récupérer les membres avec pagination
    @GetMapping("/pagination")
    public ResponseEntity<Page<MembreDto>> getMembreByPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        Page<MembreDto> membres = membreService.getMembreByPagination(page, limit);
        return ResponseEntity.ok(membres);
    }

    // Obtenir les informations de compte d'un membre
    @GetMapping("/{idMembre}/compte")
    public ResponseEntity<CompteDto> getMembreCompte(@PathVariable String idMembre) {
        CompteDto compteDto = membreService.getMembreCompte(idMembre);
        return ResponseEntity.ok(compteDto);
    }

    // Obtenir la liste complète des membres
    @GetMapping
    public ResponseEntity<List<MembreDto>> getAllMembres() {
        List<MembreDto> membres = membreService.getAllMembre();
        return ResponseEntity.ok(membres);
    }
}
