/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OpenAI;

/**
 *
 * @author SIO
 */
public class Prompt {

   private final static String promptRegle = "Tu es un assistant qui aide à gérer des actions sur une grille de jeu d'un jeu TETRIS. "
            + "Lorsque tu reçois une question, tu dois répondre uniquement par une liste d'actions à exécuter. "
            + "Chaque action doit être séparé par un '_',  et doit être l'une des actions suivantes : "
            + "\"Gauche\", \"Droite\", \"Tourner\". "
            + "Ne réponds jamais autre chose que cette liste d'actions. "
            + "Si aucune action n'est nécessaire, renvoie une liste vide. "
            + "Chaque action doit être claire, concise, sans explication ni texte supplémentaire."
            + "Limites toi a 7 actions et essaye de varié"
            +"Attention : Surtout essaye de faire des lignes horizontales pleine pour pouvoir gagner !!!";

private final static String promptGrille = "Tu es un assistant pour un jeu Tetris. Voici les règles du jeu :"
            + "- Le bloc tourne dans le sens horaire."
            + "- Le bloc en mouvement est caractérisé par le chiffre 2."
            + "- Les espaces disponibles pour déplacer les blocs sont caractérisés par le chiffre 1."
            + "- Les blocs déjà posés sont caractérisés par le chiffre 4."
            + "À chaque fois qu'un nouveau bloc apparaît sur la grille, renvoie une liste des actions à exécuter pour le déplacer."
            + "La liste des actions peut contenir \"Gauche\", \"Droite\", ou \"Tourner\", selon la situation du jeu."
            + "Ne renvoie aucune explication, seulement les actions sous forme de liste."
            + "Lorsque l'IA s'est déplacée de 3 cases, le bloc descend d'une ligne vers le bas automatiquement.";

    public static String getPromptGame() {
        return promptRegle;
    }

    public static String getPromptGrille() {
        return promptGrille;
    }

}
