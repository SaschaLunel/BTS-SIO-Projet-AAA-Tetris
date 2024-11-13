-- Création de la table 'utilisateurs'
CREATE TABLE utilisateurs (
    iduser INT AUTO_INCREMENT PRIMARY KEY,        -- Clé primaire auto-incrémentée
    email VARCHAR(100) NOT NULL,                  -- Colonne pour l'email
    mdp VARCHAR(255) NOT NULL,                    -- Colonne pour le mot de passe (hashé)
    verifmail TINYINT(1) DEFAULT 0,               -- Colonne pour savoir si l'email est vérifié (0 = non, 1 = oui)
    prenom VARCHAR(50) NOT NULL,                  -- Colonne pour le prénom
    nom VARCHAR(50) NOT NULL,                     -- Colonne pour le nom
    pseudo VARCHAR(50) NOT NULL UNIQUE,           -- Colonne pour le pseudo, unique
    Dbirth DATE,                                  -- Colonne pour la date de naissance
    Dinscr TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- Date d'inscription, avec une valeur par défaut
    Dderco TIMESTAMP NULL                         -- Date et heure de la dernière connexion
);

-- Création de la table 'scores' avec une clé étrangère vers 'utilisateurs'
CREATE TABLE scores (
    idscore INT AUTO_INCREMENT PRIMARY KEY,       -- Clé primaire auto-incrémentée pour les scores
    iduser INT,                                   -- Clé étrangère, référence l'utilisateur
    score INT NOT NULL,                           -- Colonne pour stocker le score
    date_score TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Date et heure du score
    FOREIGN KEY (iduser) REFERENCES utilisateurs(iduser) ON DELETE CASCADE  -- Clé étrangère avec suppression en cascade
);
