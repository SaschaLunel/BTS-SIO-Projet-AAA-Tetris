-- Création de la base de données
CREATE DATABASE IF NOT EXISTS tetrisdb;

-- Utilisation de la base de données
USE tetrisdb;

-- Création de la table 'utilisateurs'
CREATE TABLE utilisateurs (
    iduser INT AUTO_INCREMENT PRIMARY KEY,  -- Clé primaire auto-incrémentée
    email VARCHAR(50) NOT NULL,             -- Colonne pour l'email
    mdp VARCHAR(50) NOT NULL,             -- Colonne pour le mot de passe
    verifmail TINYINT(1) DEFAULT 0,        -- Colonne pour savoir si l'email est vérifié (0 = non, 1 = oui)
    prenom VARCHAR(50) NOT NULL,             -- Colonne pour le prénom
    nom VARCHAR(50) NOT NULL,                -- Colonne pour le nom
    pseudo VARCHAR(50) NOT NULL UNIQUE,      -- Colonne pour le pseudo, unique
    Dbirth DATE,                             -- Colonne pour la date de naissance
    Dinscr TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Date d'inscription, avec une valeur par défaut à la création de l'enregistrement
    Dderco TIMESTAMP NULL,                    -- Date et heure de la dernière connexion
    FOREIGN KEY (idrole) REFERENCES roles(idrole),  -- Définition de la clé étrangère vers la table 'roles'
    idrole INT                               -- Colonne pour le rôle (clé étrangère vers la table 'roles')
);

-- Création de la table 'scores' avec une clé étrangère vers 'utilisateurs'
CREATE TABLE scores (
    idscore INT AUTO_INCREMENT PRIMARY KEY,  -- Clé primaire auto-incrémentée pour les scores
    iduser INT,                              -- Clé étrangère, référence l'utilisateur
    score INT NOT NULL,                      -- Colonne pour stocker le score
    date_score TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Date et heure du score
    FOREIGN KEY (iduser) REFERENCES utilisateurs(iduser)  -- Définition de la clé étrangère
);

-- Création de la table 'roles'
CREATE TABLE roles (
    idrole INT AUTO_INCREMENT PRIMARY KEY,    -- Clé primaire auto-incrémentée pour les rôles
    nom_role VARCHAR(50) NOT NULL UNIQUE      -- Nom du rôle (ex: 'admin', 'invité', 'utilisateur'), unique
);

-- Insertion des rôles 'admin', 'invité', et 'utilisateur'
INSERT INTO roles (nom_role) VALUES ('admin'), ('invité'), ('utilisateur');
