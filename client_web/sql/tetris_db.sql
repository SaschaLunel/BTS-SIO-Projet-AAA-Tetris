-- Supprime la base de données existante si elle existe déjà
DROP DATABASE IF EXISTS tetris_db;

-- Création de la base de données
CREATE DATABASE tetris_db;

-- Utilisation de la base de données
USE tetris_db;

-- Création de la table 'users'
CREATE TABLE IF NOT EXISTS users (
    iduser INT AUTO_INCREMENT PRIMARY KEY,  -- Identifiant unique pour l'utilisateur
    email VARCHAR(50) NOT NULL UNIQUE,     -- Email unique de l'utilisateur
    mdp VARCHAR(255) NOT NULL,              -- Mot de passe de l'utilisateur
    verifemail BOOLEAN DEFAULT FALSE,      -- Vérification de l'email
    prenom VARCHAR(50) NOT NULL,           -- Prénom de l'utilisateur
    nom VARCHAR(50) NOT NULL,              -- Nom de l'utilisateur
    pseudo VARCHAR(50) NOT NULL UNIQUE,    -- Pseudonyme de l'utilisateur
    dBirth DATE NOT NULL,                   -- Date de naissance de l'utilisateur
    dInscr DATETIME DEFAULT CURRENT_TIMESTAMP,  -- Date d'inscription
    dDeco DATETIME,                         -- Date de dernière connexion
    use_extra_pieces TINYINT(1) DEFAULT 0,  -- Option pour activer les pièces spéciales
    --ajout exam
    failed_attempts INT DEFAULT 0,          -- Compteur des tentatives de connexion échouées
    account_locked BOOLEAN DEFAULT FALSE,   -- Booléen indiquant si le compte est bloqué
    lockout_time DATETIME NULL              -- Timestamp du moment où le blocage a commencé
);

-- Création de la table 'score'
CREATE TABLE IF NOT EXISTS score (
    idscore INT AUTO_INCREMENT PRIMARY KEY,  -- Identifiant unique pour le score
    iduser INT,                              -- Identifiant de l'utilisateur (clé étrangère)
    score INT NOT NULL,                      -- Score de l'utilisateur
    dScore DATETIME DEFAULT CURRENT_TIMESTAMP,  -- Date du score
    FOREIGN KEY (iduser) REFERENCES users(iduser)  -- Relation avec la table 'users'
);
