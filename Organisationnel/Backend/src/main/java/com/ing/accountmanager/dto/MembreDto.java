package com.ing.accountmanager.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MembreDto {

    private String id;
    private String nom;
    private String prenom;
    private String sexe;
    private LocalDate dateDeNaissance;
    private String email;
    private String adresse;
    private String pathPhoto;
    private LocalDateTime dateInscription;

    public MembreDto() {}

    public MembreDto(String id,
                     String nom,
                     String prenom,
                     String sexe,
                     LocalDate dateDeNaissance,
                     String email,
                     String adresse,
                     String pathPhoto,
                     LocalDateTime dateInscription) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.dateDeNaissance = dateDeNaissance;
        this.email = email;
        this.adresse = adresse;
        this.pathPhoto = pathPhoto;
        this.dateInscription = dateInscription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public LocalDate getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPathPhoto() {
        return pathPhoto;
    }

    public void setPathPhoto(String pathPhoto) {
        this.pathPhoto = pathPhoto;
    }

    public LocalDateTime getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }
}
