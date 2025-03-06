package com.ing.accountmanager.init;

import com.github.javafaker.Faker;
import com.ing.accountmanager.model.Compte;
import com.ing.accountmanager.model.Membre;
import com.ing.accountmanager.repository.CompteRepository;
import com.ing.accountmanager.repository.MembreRepository;
import com.ing.accountmanager.enums.StatutCompte;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class MemberInit implements CommandLineRunner {

    private final MembreRepository membreRepository;
    private final CompteRepository compteRepository;

    public MemberInit(MembreRepository membreRepository, CompteRepository compteRepository) {
        this.membreRepository = membreRepository;
        this.compteRepository = compteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Liste de prénoms et noms réels issus des cultures africaines des 54 pays
        List<String> prenomsMasculins = Arrays.asList("Kwame", "Mamadou", "Abdul", "Tariq", "Sadio", "Oumar", "Demba", "Kofi", "Diallo", "Youssef");
        List<String> prenomsFeminin = Arrays.asList("Aissatou", "Fatoumata", "Nadia", "Yasmin", "Kadiatou", "Mariama", "Djeneba", "Mariam", "Aminata", "Rokia");
        List<String> noms = Arrays.asList(
                "Diallo", "Bamba", "Ouédraogo", "Moktar", "N'Guessan", "Toure", "Fofana", "Kone", "Sow", "Keita",
                "Ndiaye", "Lamine", "Sambou", "Diop", "Ba", "Cissé", "Camara", "Konaté", "Sarr", "Fayçal", "Tchouassi",
                "Traoré", "Sidibé", "Amadou", "Mbengue", "Cherif", "Zoumana", "Yaya", "Ibrahime", "Zaki", "Hakim",
                "Ali", "Omar", "Mohammed", "Amani", "Cissé", "Gassama", "Moussa", "Abou", "Souleymane", "Ousseynou", "Kader", "Issa"
        );

        // Ensemble pour assurer l'unicité des noms de famille
        Set<String> nomsUtilises = new HashSet<>();

        // Générer et sauvegarder 300 membres avec un compte unique et des âges différents
        for (int i = 0; i < 300; i++) {
            String sexe = random.nextBoolean() ? "Masculin" : "Feminin";
            String prenom = sexe.equals("Masculin") ? prenomsMasculins.get(random.nextInt(prenomsMasculins.size()))
                    : prenomsFeminin.get(random.nextInt(prenomsFeminin.size()));
            String nom;
            // Garantir l'unicité des noms de famille
            do {
                nom = noms.get(random.nextInt(noms.size())).toUpperCase();  // Nom en lettres capitales
            } while (nomsUtilises.contains(nom)); // Vérifier si le nom est déjà utilisé
            nomsUtilises.add(nom);

            prenom = prenom.substring(0, 1).toUpperCase() + prenom.substring(1).toLowerCase();  // Prénom avec première lettre en majuscule

            // Générer une date de naissance avec un âge entre 18 et 80 ans
            int age = random.nextInt(62) + 18; // L'âge est compris entre 18 et 80 ans
            LocalDate dateDeNaissance = LocalDate.now().minusYears(age);

            String email = faker.internet().emailAddress();
            String adresse = faker.address().fullAddress();
            String pathPhoto = "/images/photo_membre_" + (i + 1) + ".jpg";
            LocalDateTime dateInscription = LocalDateTime.now();

            // Créer et sauvegarder le membre
            Membre membre = new Membre(
                    null,
                    nom,
                    prenom,
                    sexe,
                    dateDeNaissance,
                    email,
                    adresse,
                    pathPhoto,
                    dateInscription
            );
            membreRepository.save(membre);

            // Générer un solde aléatoire entre 0 et 1 000 000
            double soldeInitial = random.nextDouble() * 1000000; // Solde entre 0 et 1 000 000

            // Création d'un compte unique pour chaque membre
            String numeroCompte = "CPT-" + faker.idNumber().valid();
            Compte compte = new Compte(
                    null,
                    numeroCompte,
                    soldeInitial, // Solde aléatoire
                    LocalDateTime.now(),
                    StatutCompte.values()[random.nextInt(StatutCompte.values().length)], // Statut aléatoire
                    membre,
                    null, // Transactions
                    null  // Transferts
            );
            compteRepository.save(compte);
        }

        System.out.println("300 membres et leurs comptes ont été initialisés avec succès !");
    }
}
