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
