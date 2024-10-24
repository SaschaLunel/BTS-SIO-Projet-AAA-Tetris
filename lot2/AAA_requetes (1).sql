/* récupere le top 1*/
SELECT MAX(score_Score) AS meilleur_score FROM Score;


/* Recupere le high score des joueurs */
SELECT id_User, name_User, MAX(value_Score) as highscore FROM user
NATURAL JOIN score GROUP BY id_User, name_User ORDER BY highscore DESC;

/* Recupere le high Score d'un joueur */

SELECT id_User, name_User, MAX(value_Score) AS highscore 
FROM User 
NATURAL JOIN Score 
WHERE id_User = :id
GROUP BY id_User, name_User 
ORDER BY highscore DESC;

/* récupere les 10 meilleurs partie */
SELECT id_User, value_Score, date_Score 
FROM Score 
WHERE id_User = :id 
ORDER BY value_Score DESC 
LIMIT 10;

/* recupere toutes les partie d'un joueur dans l'ordre des dates*/

SELECT id_User, value_Score, date_Score 
FROM Score 
WHERE id_User = :id 
ORDER BY date_Score;

/* récupere les 10 dernieres parties */

SELECT id_User, value_Score, date_Score 
FROM Score 
WHERE id_User = :id 
ORDER BY date_Score DESC 
LIMIT 10;

/* récupere le nombres de partie d'un joeur */

SELECT COUNT(*) AS parties_jouees 
FROM Score 
WHERE id_User = :id;

/* récupere le nb de partie joueur en total */
SELECT COUNT(*) AS parties_jouees 
FROM Score 

/* récupere la moyenne des score d'un joueur */
SELECT AVG(score_Score) AS moyenne_score 
FROM Score 
WHERE id_User = :id;


/* requetes inscription _____________________________________________________________________________________________________________________________ */
INSERT INTO User (email_User, mdp_User, prenom_User, nom_User, pseudo_User, dBirth_User, dInscrip_USer) 
VALUES ('email@exemple.com', 'password123', 'prenom', 'nom', 'pseudo', '07/05/1996', CURDATE());

INSERT INTO User (email_User, mdp_User, prenom_User, nom_User, pseudo_User, dBirth_User, dInscrip_USer) 
VALUES ('toto@exemple.com', 'password456', 'rara', 'lulu', 'laura35', '07/05/1996', CURDATE());

INSERT INTO User (email_User, mdp_User, prenom_User, nom_User, pseudo_User, dBirth_User, dInscrip_USer) 
VALUES ('tutu@exemple.com', 'password789', 'tintin', 'monnom', 'tintin', '07/05/1996', CURDATE());


/* requetes new scores____________________________________________________________________________________________________________________________ */

INSERT INTO Score(scoreValue_Score, idUser_User)
VALUES (3600, 1);

INSERT INTO Score(scoreValue_Score, idUser_User)
VALUES (3640, 1);

INSERT INTO Score(scoreValue_Score, idUser_User)
VALUES (600, 1);

INSERT INTO Score(scoreValue_Score, idUser_User)
VALUES (300, 1);

INSERT INTO Score(scoreValue_Score, idUser_User)
VALUES (3680, 1);

INSERT INTO Score(scoreValue_Score, idUser_User)
VALUES (3999, 1);

/* Joueur 2 ________________________________________________*/

INSERT INTO Score(scoreValue_Score, idUser_User)
VALUES (3601, 2);

INSERT INTO Score(scoreValue_Score, idUser_User)
VALUES (3610, 2);

INSERT INTO Score(scoreValue_Score, idUser_User)
VALUES (601, 2);

INSERT INTO Score(scoreValue_Score, idUser_User)
VALUES (301, 2);

INSERT INTO Score(scoreValue_Score, idUser_User)
VALUES (3681, 2);

INSERT INTO Score(scoreValue_Score, idUser_User)
VALUES (3999, 2);

/* verif mail ok _______________________________________________________*/

UPDATE user SET BEmailVerif_User = True WHERE idUser_User = 1;

UPDATE user SET BEmailVerif_User = True WHERE idUser_User = 3;

/* Ajouter des droit a des utilisateurs */

UPDATE user SET Droit_User = 3 Where idUser_User = 2;

/* récupérer la derniere connection */

SELECT dDecoLast_User FROM user WHERE idUser_User = 2;

/* savoir si c'est l'aniversaire */

SELECT idUser_User FROM user WHERE dBirth_User = CURDATE();

/*  changer le mode de passe */

UPDATE user SET mdp_User = NewMDP Where idUser_User = 2;

/* supression du compte */
DELETE FROM user WHERE id = 2;

/*changer l'email*/

UPDATE user SET email_User = couscous@gmail.com Where idUser_User = 2;

/* changer le speudo */

UPDATE user SET pseudo_User = couscousDU35 Where idUser_User = 1;

/* changer la derniere déconnection */

UPDATE user SET dDecoLast_User = CURDATE(); Where idUser_User = 3;