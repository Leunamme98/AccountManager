package com.ing.accountmanager.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Membre {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    private String sexe;

    @Temporal(TemporalType.DATE)
    private LocalDate dateDeNaissance;

    @Column(unique = true, nullable = false)
    private String email;

    private String adresse;
    private String pathPhoto;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateInscription;

        // Constructeur sans argument
    public Membre() {}

    // Constructeur avec tous les arguments
    public Membre(String id, String nom, String prenom, String sexe, LocalDate dateDeNaissance,
                  String email, String adresse, String pathPhoto, LocalDateTime dateInscription) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.dateDeNaissance = dateDeNaissance;
        this.email = email;
        this.adresse = adresse;
        this.pathPhoto = pathPhoto;
        this.dateInscription = (dateInscription != null) ? dateInscription : LocalDateTime.now();
    }


    @PrePersist
    public void prePersist() {
        this.dateInscription = (this.dateInscription == null) ? LocalDateTime.now() : this.dateInscription;
    }


    // Getters et Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public LocalDate getDateDeNaissance() { return dateDeNaissance; }
    public void setDateDeNaissance(LocalDate dateDeNaissance) { this.dateDeNaissance = dateDeNaissance; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getPathPhoto() { return pathPhoto; }
    public void setPathPhoto(String pathPhoto) { this.pathPhoto = pathPhoto; }

    public LocalDateTime getDateInscription() { return dateInscription; }
    public void setDateInscription(LocalDateTime dateInscription) { this.dateInscription = dateInscription; }
}
