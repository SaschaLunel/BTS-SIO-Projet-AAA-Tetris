package Controllers;

import BlockFolder.AbstractBlock;
import Components.Gameplay.GridGame;
import java.util.Random;

public class AIController {
    // Déclaration des constantes
    private final int NONE = 0;
    private final int VIDE = 1;
    private final int BLOCK_PLAYER = 2;
    private final int COLLISION = 3;
    private final int BLOCK_POSER = 4;

    private GridGame grid;  // Référence à la grille de jeu
    private AbstractBlock currentPiece;  // La pièce active

    public AIController(GridGame grid) {
        this.grid = grid;
    }

    // Fonction pour évaluer l'état de la grille
    private int evaluateGrid(GridGame g) {
        return -g.getHeight() - 5 * g.getHoles() + 10 * g.getCompletedLines();
    }

    // Fonction pour tester tous les mouvements possibles d'une pièce
    public Move getBestMove(AbstractBlock piece) {
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = null;

        // Tester toutes les rotations et positions possibles de la pièce
        for (int rotation = 0; rotation < piece.getRotationCount(); rotation++) {
            for (int col = 0; col < grid.getWidth(); col++) {
                GridGame newGrid = grid.clone();  // Cloner la grille pour éviter de la modifier directement
                if (newGrid.canPlace(piece, rotation, col)) {
                    newGrid.place(piece, rotation, col);
                    int score = evaluateGrid(newGrid);
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new Move(rotation, col);  // Enregistrer le meilleur mouvement
                    }
                }
            }
        }

        return bestMove;
    }

    // Fonction pour appliquer le meilleur mouvement à la pièce active
    public void makeMove() {
        Move bestMove = getBestMove(currentPiece);
        if (bestMove != null) {
            grid.place(currentPiece, bestMove.getRotation(), bestMove.getColumn());
        }
    }

    // Setter pour la pièce active
    public void setCurrentPiece(AbstractBlock piece) {
        this.currentPiece = piece;
    }

    // Exemple de méthode qui décide du mouvement à faire (ici, un mouvement aléatoire)
    public void play() {
        Random rand = new Random();

        // Logique simple : mouvement aléatoire
        int action = rand.nextInt(4);  // 0 = Gauche, 1 = Droite, 2 = Rotation, 3 = Bas

        switch (action) {
            case 0:
                grid.movePiece(-1);  // Déplacer à gauche
                break;
            case 1:
                grid.movePiece(1);  // Déplacer à droite
                break;
            case 2:
                grid.rotationGrid();  // Rotation
                break;
            case 3:
                grid.softDropGrid();  // Descente
                break;
        }
    }
}

