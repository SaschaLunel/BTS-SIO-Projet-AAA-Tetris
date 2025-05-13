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
    use_extra_pieces TINYINT(1) DEFAULT 0   -- Option pour activer les pièces spéciales
);

-- Création de la table 'score'
CREATE TABLE IF NOT EXISTS score (
    idscore INT AUTO_INCREMENT PRIMARY KEY,  -- Identifiant unique pour le score
    iduser INT,                              -- Identifiant de l'utilisateur (clé étrangère)
    score INT NOT NULL,                      -- Score de l'utilisateur
    dScore DATETIME DEFAULT CURRENT_TIMESTAMP,  -- Date du score
    FOREIGN KEY (iduser) REFERENCES users(iduser)  -- Relation avec la table 'users'
);

-- Insertion d'un compte admin pour tests 
INSERT INTO users (email, mdp, verifemail, prenom, nom, pseudo, dBirth, use_extra_pieces)
VALUES ('thomashenry354@gmail.com', 'Jowo7455', TRUE, 'thomas', 'henry', 'Tonton', '1996-07-05', 0);

-- Insertion de scores de test pour l'utilisateur admin
INSERT INTO score (iduser, score, dScore) VALUES
(1, 1000, NOW()), -- Score de 1000 pour l'utilisateur admin
(1, 1500, NOW()), -- Score de 1500 pour l'utilisateur admin
(1, 2000, NOW()); -- Score de 2000 pour l'utilisateur admin