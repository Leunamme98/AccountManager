# AccountManager

## Description
**AccountManager** est une application de gestion de transactions financières conçue pour les membres d'une association. Elle permet de gérer les dépôts, les retraits et les transferts d'argent entre les comptes des membres. Le projet est divisé en deux parties principales :
- **Organisationnel** : Une application web complète avec un backend en Spring Boot et un frontend en Angular.
- **Decisionnel** : Un environnement décisionnel pour l'analyse des données financières.

---

## Structure du projet
Le projet est organisé comme suit :
AccountManager/
├── Organisationnel/ # Partie organisationnelle
│ ├── Backend/ # Backend en Spring Boot
│ └── Frontend/ # Frontend en Angular
├── Decisionnel/ # Partie décisionnelle
│ ├── Data/ # Données pour l'analyse
│ ├── Scripts/ # Scripts d'analyse (Python, SQL, etc.)
│ └── Reports/ # Rapports générés
├── .gitignore # Fichiers et dossiers ignorés par Git
├── LICENSE # Licence du projet
└── README.md # Documentation du projet


---

## Prérequis
Avant de commencer, assurez-vous d'avoir installé les outils suivants :
- **Java JDK 11** (ou supérieur) pour le backend Spring Boot.
- **Node.js** et **Angular CLI** pour le frontend Angular.
- **Git** pour la gestion du versionnement.
- **MySQL** ou tout autre SGBD pour la base de données.

---

## Installation

### 1. Cloner le dépôt
Clonez le dépôt sur votre machine locale :
```bash
git clone https://github.com/Leunamme98/AccountManager.git
cd AccountManager 