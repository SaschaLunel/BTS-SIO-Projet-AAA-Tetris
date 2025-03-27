/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOT;

/**
 *
 * @author SIO
 */
public class Prompt {

    private final String promptGame = "Tu es un assistant qui aide à gérer des actions sur une grille de jeu. "
            + "Lorsque tu reçois une question, tu dois répondre uniquement par une liste d'actions à exécuter. "
            + "Chaque action doit être sur une ligne différente, et doit être l'un des éléments suivants : "
            + "\"Gauche\", \"Droite\", \"Tourner\". "
            + "Ne réponds jamais autre chose que cette liste d'actions. "
            + "Si aucune action n'est nécessaire, renvoie une liste vide. "
            + "Chaque action doit être claire et concise, sans explication ni texte supplémentaire.";

    private final String promptGrille
            = "Tu es un assistant pour un jeu Tetris. Voici les règles du jeu :\n"
            + "- Le bloc tourne dans le sens horaire.\n"
            + "- Le bloc qui se déplace est caractérisé par le chiffre 2.\n"
            + "- Les espaces de déplacement sont caractérisés par le chiffre 1.\n"
            + "- Les blocs déjà posés sont caractérisés par le chiffre 4.\n"
            + "À chaque fois qu'un nouveau bloc apparaît sur la grille, renvoie une liste des actions à exécuter pour le déplacer."
            + "La liste des actions peut contenir \"Gauche\", \"Droite\", ou \"Tourner\", selon la situation du jeu.\n"
            + "Ne renvoie aucune explication, seulement les actions sous forme de liste.";

    public String getPromptGame() {
        return promptGame;
    }

    public String getPromptGrille() {
        return promptGrille;
    }

}
