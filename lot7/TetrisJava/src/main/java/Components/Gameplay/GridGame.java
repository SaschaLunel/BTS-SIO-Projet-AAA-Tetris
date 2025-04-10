/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Gameplay;

import BlockFolder.AbstractBlock;
import BlockFolder.LBlock;
import BlockFolder.OBlock;
import BlockFolder.ZBlock;
import BlockFolder.SBlock;
import BlockFolder.JBlock;
import BlockFolder.IBlock;
import BlockFolder.TBlock;
import Controllers.AIController;
import Interfaces.GameActions;

import Panel.PanelGame;
import Panel.PanelGameOpenAI;

import java.io.IOException;
import java.util.Random;


import javax.swing.Timer;

/**
 *
 * @author SIO
 */
public class GridGame {

    private GameActions gameActions;
    
    

    private int[][] blockShape;
    private int[][] grid;
    private int row;
    private int column;
    private int index_color;
    private int index_rotation;

    public int indexOffset_x;
    public int indexOffset_y;
    public AbstractBlock currentPiece;

    private boolean isGameOver = false;
    private Timer dropTimer; // Ajoutez cette variable pour stocker le Timer

    private long lastSoftDropTime = 0;
    private static final long SOFT_DROP_COOLDOWN = 100; // 0.1 seconde en millisecondes

    // Tableau contenant tous les types de blocs
    private static final AbstractBlock[] blocks = {
        new LBlock(),
        new OBlock(),
        new ZBlock(),
        new SBlock(),
        new JBlock(),
        new IBlock(),
        new TBlock()
    };

    final private int NONE = 0;
    final private int VIDE = 1;
    final private int BLOCK_PLAYER = 2;
    final private int COLLISION = 3;
    final private int BLOCK_POSER = 4;

    private PanelGame panel;

    public GridGame() {
    }

    public GridGame(PanelGame panel, int row, int column) {
        this.panel = panel;
        this.column = column;
        this.row = row;
        grid = new int[row][column];
        // Initialisation de chaque élément à 1
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                grid[i][j] = VIDE; // Set each element to 1
            }
        }
    }

    // Méthode pour récupérer un bloc aléatoire
    public static AbstractBlock getRandomBlock() {
        Random random = new Random();
        int randomIndex = random.nextInt(blocks.length); // Génère un index aléatoire
        return blocks[randomIndex]; // Retourne le bloc à cet index
    }

    public GridGame(int row, int column) {
        this.column = column;
        this.row = row;
        grid = new int[row][column];
        // Initialisation de chaque élément à 1
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                grid[i][j] = VIDE; // Set each element to 1
            }
        }
    }

    public void setIndex_rotation(int index_rotation) {
        this.index_rotation = index_rotation;
    }

    // Méthode qui renvoie une grille
    public int[][] CreateGrid() {

        // Récupérer un bloc aléatoire
        currentPiece = getRandomBlock();
        blockShape = currentPiece.getRotation(0);
       
        panel.IANewPiece(currentPiece); 


        // Définir la position de départ du bloc, centré dans la grille en haut
        indexOffset_y = (int) (grid[0].length * 0.5f - currentPiece.getRotation(0).length * 0.5f);
        indexOffset_x = 0;

        // Récupérer la forme du bloc pour l'orientation 0 (première orientation)
        int[][] blockShape = currentPiece.getRotation(0);

        // Placer le bloc dans la grille
        for (int i = 0; i < blockShape.length; i++) {
            for (int j = 0; j < blockShape[i].length; j++) {
                // Vérifier si la position du bloc est valide (dans les limites de la grille)
                if (indexOffset_x + i < grid.length && indexOffset_y + j < grid[0].length) {
                    if (blockShape[i][j] != 0) {
                        grid[indexOffset_x + i][indexOffset_y + j] = BLOCK_PLAYER;   // Placer le bloc dans la grille
                    }

                }
            }
        }

        return grid;
    }

    ////////////////////////////FONCTION PRIMAIRE//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void movePiece(int direction) {
        int newY = indexOffset_y + direction;
        int[][] currentShape;

        // Vérification que l'indice est dans les limites
if (index_rotation >= 0 && index_rotation < currentPiece.getRotationCount()) {
    currentShape = currentPiece.getRotation(index_rotation);
} else {
    throw new IllegalArgumentException("Index de rotation invalide : " + index_rotation);
}


        // Vérifier les limites horizontales de manière plus permissive
        if (newY < 0 || newY + currentShape[0].length > column) {
            return; // Ne pas permettre le mouvement si cela causerait un débordement
        }

        int[][] copyGridGame = copyGrid(grid);
        copyGridGame = clearBlockInGrid(copyGridGame);

        // Mettre à jour temporairement la position pour le test
        indexOffset_y = newY;

        // Vérifier si le bloc peut être placé à la nouvelle position
        if (CanAddBlockToGrid(copyGridGame)) {
            grid = addBlockToGrid(copyGridGame);
        } else {
            // Si le mouvement n'est pas possible, restaurer l'ancienne position
            indexOffset_y = newY - direction;
        }
    }

    public void dropBlock() throws IOException {
        // Créer une copie de la grille
        int[][] copyGridGame = copyGrid(grid);

        // Effacer l'ancien bloc (mettre les cases à "vide")
        copyGridGame = clearBlockInGrid(copyGridGame);

        // Incrémenter la position pour descendre le bloc
        indexOffset_x += 1;

        if (CanAddBlockToGrid(copyGridGame)) {
            // Si le bloc peut être placé, mettre à jour la grille
            grid = addBlockToGrid(copyGridGame);
            System.err.println("oui");
        } else {
            System.err.println("non");
            // Si le bloc ne peut pas descendre, annuler le déplacement et le verrouiller
            indexOffset_x -= 1;
            handleBlockLock();
        }
    }

    public boolean softDropGrid() {
        long currentTime = System.currentTimeMillis();

        // Vérifier si suffisamment de temps s'est écoulé depuis le dernier soft drop
        if (currentTime - lastSoftDropTime < SOFT_DROP_COOLDOWN) {
            return false; // Retourner false si le cooldown n'est pas terminé
        }

        // Mettre à jour le temps du dernier soft drop
        lastSoftDropTime = currentTime;

        // Créer une copie de la grille et tester le mouvement
        int[][] gridCopy = copyGrid(grid);
        gridCopy = clearBlockInGrid(gridCopy);

        indexOffset_x += 1;

        if (CanAddBlockToGrid(gridCopy)) {
            grid = addBlockToGrid(gridCopy);
            return true; // Le mouvement a été effectué
        } else {
            indexOffset_x -= 1;
            return false; // Le mouvement n'a pas été effectué
        }
    }

    /**
     * Rotates the current block in the grid.
     * <p>
     * This method updates the block's rotation by computing its new shape and
     * checking for collisions. If the rotation is valid, the grid is updated;
     * otherwise, the rotation is canceled.
     * </p>

 Preconditions: - `currentPiece` must contain valid block shape rotations. -
 `grid` must be initialized and contain the current game state.

 Postconditions: - `grid` is updated with the new block rotation if no
 collision occurs. - `index_rotation` is updated accordingly.

 Collision Handling: - If a collision is detected, the rotation is not
 applied.

 Grid Boundaries: - Ensures that the rotated block remains within the
 grid's bounds.
     */
    public void rotationGrid() {

        // Vérifier si le bloc existe
        if (currentPiece == null) {
            return;
        }

        // Obtenir le nombre réel de rotations pour ce bloc
        int maxRotations = currentPiece.getRotationCount();


        // Si le bloc n'a qu'une seule rotation, ne rien faire
        if (maxRotations <= 1) {
            return;
        }

        // Calculer la prochaine rotation de manière sûre
        int copy_index_rotation = (index_rotation + 1) % maxRotations;

        int[][] newBlockShape = copyGrid(currentPiece.getRotation(copy_index_rotation));


        int[][] copyGridGame = copyGrid(grid);

        copyGridGame = clearBlockInGrid(copyGridGame);

        for (int i = 0; i < newBlockShape.length; i++) {
            for (int j = 0; j < newBlockShape[i].length; j++) {
                // Vérifier si la position du bloc est valide (dans les limites de la grille)
                if (indexOffset_x + i < copyGridGame.length && indexOffset_y + j < grid[0].length) {
                    copyGridGame[indexOffset_x + i][indexOffset_y + j] = newBlockShape[i][j] + copyGridGame[indexOffset_x + i][indexOffset_y + j]; // Placer le bloc dans la grille
                }
            }
        }

        if (hasCollision(copyGridGame) && isBlockOutOfBounds(newBlockShape, indexOffset_x, indexOffset_y, copyGridGame[0].length, copyGridGame.length)) {
            System.out.println("collision detected");
            return;
        } else {
        }
        index_rotation = copy_index_rotation;
        grid = copyGridGame;
    }
    
     // Fonction pour vérifier si une pièce peut être placée à une position donnée
    public boolean canPlace(AbstractBlock piece, int rotation, int col) {
        int[][] rotationGrid = piece.getRotation(rotation);
        
        // Vérifie si la pièce peut être placée dans la grille
        for (int row = 0; row < rotationGrid.length; row++) {
            for (int c = 0; c < rotationGrid[row].length; c++) {
                if (rotationGrid[row][c] != 0) {
                    if (row >= grid.length || col + c >= grid[row].length || grid[row][col + c] != VIDE) {
                        return false;  // Impossible de placer la pièce (collision ou dépassement de la grille)
                    }
                }
            }
        }
        return true;
    }
    
    // Fonction pour placer la pièce dans la grille
    public void place(AbstractBlock piece, int rotation, int col) {
        int[][] rotationGrid = piece.getRotation(rotation);
        
        // Place la pièce dans la grille
        for (int row = 0; row < rotationGrid.length; row++) {
            for (int c = 0; c < rotationGrid[row].length; c++) {
                if (rotationGrid[row][c] != 0) {
                    grid[row][col + c] = BLOCK_PLAYER;  // Bloc du joueur
                }
            }
        }
    }
    
    // Clone la grille pour ne pas modifier la grille originale lors des simulations
    public GridGame clone() {
        GridGame clonedGrid = new GridGame(grid[0].length, grid.length);
        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(grid[i], 0, clonedGrid.grid[i], 0, grid[i].length);
        }
        return clonedGrid;
    }
    
    // Calculer le nombre de trous
    public int getHoles() {
        int holes = 0;
        for (int col = 0; col < grid[0].length; col++) {
            boolean blockFound = false;
            for (int row = 0; row < grid.length; row++) {
                if (grid[row][col] != NONE) {
                    blockFound = true;
                } else if (blockFound) {
                    holes++;  // Trou trouvé
                }
            }
        }
        return holes;
    }
    
     // Nombre de lignes complètes
    public int getCompletedLines() {
        int lines = 0;
        for (int i = 0; i < grid.length; i++) {
            boolean isComplete = true;
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == NONE) {
                    isComplete = false;
                    break;
                }
            }
            if (isComplete) {
                lines++;
            }
        }
        return lines;
    }


    ////////////////////////////FONCTION SECONDAIRE//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Ajoutez ces attributs pour gérer l'état du jeu
    ////////////////////////////FONCTION SECONDAIRE//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Ajoutez ces attributs pour gérer l'état du jeu
    // Méthode pour initialiser le Timer
    public void setDropTimer(Timer timer) {
        this.dropTimer = timer;
    }
    // Modifiez la méthode endGame pour arrêter le Timer
    // Ajoutez un getter pour vérifier l'état du jeu
    // Modifiez handleBlockLock pour utiliser la nouvelle logique
    // Modifiez la méthode endGame pour arrêter le Timer
    // Ajoutez un getter pour vérifier l'état du jeu
    // Modifiez handleBlockLock pour utiliser la nouvelle logique

    // Renommez l'ancienne méthode isGameOver en checkGameOver pour plus de clarté
    private boolean checkGameOver() {
        int initialX = 0;
        int initialY = (int) (grid[0].length * 0.5f - currentPiece.getRotation(index_rotation).length * 0.5f);

        int savedX = indexOffset_x;
        int savedY = indexOffset_y;

        indexOffset_x = initialX;
        indexOffset_y = initialY;

        int[][] testGrid = copyGrid(grid);
        boolean canPlace = CanAddBlockToGrid(testGrid);

        indexOffset_x = savedX;
        indexOffset_y = savedY;

        return !canPlace;
    }

    // Méthode pour réinitialiser le jeu si nécessaire
    public void resetGame() {
        isGameOver = false;
        // Réinitialiser la grille
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                grid[i][j] = VIDE;
            }
        }
        // Réinitialiser les autres variables
        indexOffset_x = 0;
        indexOffset_y = 0;
        index_rotation = 0;

        // Créer un nouveau bloc
        CreateNewBlock();
    }

    public int[] calculateBlockWidth(int[][] blockShape) {
        int startColumn = blockShape[0].length;
        int endColumn = -1;

        // Parcourir chaque ligne du bloc
        for (int[] row : blockShape) {
            for (int j = 0; j < row.length; j++) {
                if (row[j] != 0) {
                    // Mettre à jour la colonne de début
                    startColumn = Math.min(startColumn, j);

                    // Mettre à jour la colonne de fin
                    endColumn = Math.max(endColumn, j);
                }
            }
        }

        // Si aucun bloc n'est trouvé, retourner des valeurs par défaut
        if (startColumn > endColumn) {
            return new int[]{0, blockShape[0].length - 1};
        }

        return new int[]{startColumn, endColumn};
    }

    // Modifiez la méthode handleBlockLock pour utiliser la nouvelle vérification
    private void handleBlockLock() throws IOException {
        ScoreWidget.addScore(20);
        System.out.println("Block locked in place!");

        if (currentPiece == null) {
            System.err.println("Erreur: Aucun bloc actif à verrouiller.");
            return;
        }

        // Convertir le bloc en position verrouillée
        convertBlockOnGrid(grid);

        // Vérifier et supprimer les lignes complètes
        clearCompletedRows(grid);

//        // Générer un nouveau bloc
//        currentPiece = getRandomBlock();
//        indexOffset_x = 0;
//        indexOffset_y = (grid[0].length - currentPiece.getShape()[0].length) / 2;
//        index_rotation = 0;
        CreateNewBlock();

        if (panel instanceof PanelGameOpenAI) {
            PanelGameOpenAI panelAI = (PanelGameOpenAI) panel;
            if (panelAI != null && panelAI.getBot() != null) {
                panelAI.getBot().createNewInstructions(grid);
            }
        }

        // Vérifier le game over avec la nouvelle méthode
        if (isGameOver()) {
            System.out.println("Game Over! Le nouveau bloc ne peut pas être placé!");
            endGame();
        }
    }

    public boolean canMoveHorizontal(int[][] piece, int direction) {
        int height = piece.length;
        int width = piece[0].length;

        // Vérifier les limites de la grille
        int newPosX = indexOffset_x + direction;
        if (newPosX < 0 || newPosX + width > grid[0].length) {
            return false;
        }

        // Vérifier les collisions avec les autres blocs
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (piece[y][x] == 3) {
                    int gridCell = grid[indexOffset_y + y][newPosX + x];
                    if (gridCell == 4 || gridCell == 3) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Vérifier et effacer les lignes complètes
    private void clearCompletedRows(int[][] grid) {

        int i = grid.length - 1;

        // Parcourir la grille du bas vers le haut
        while (i >= 0) {
            if (isRowComplete(grid[i])) {
                ScoreWidget.addScore(1000);
                // Si une ligne est complète, déplacer toutes les lignes au-dessus vers le bas
                shiftRowsDown(grid, i);

            } else {
                i -= 1;
            }
        }
    }

//    // Vérifier et effacer les lignes complètes
//    private void clearCompletedRows(int[][] grid) {
//
//        // Parcourir la grille du bas vers le haut
//        for (int i = grid.length - 1; i >= 0; i--) {
//            if (isRowComplete(grid[i])) {
//                ScoreWidget.addScore(1000);
//                // Si une ligne est complète, déplacer toutes les lignes au-dessus vers le bas
//                shiftRowsDown(grid, i);
//                i = i - 1;  // ILLEGAL décrémentation de la boucle for 
//            }
//        }
//    }
// Vérifier si une ligne est complète
    private boolean isRowComplete(int[] row) {
        for (int cell : row) {
            if (cell != BLOCK_POSER) {
                return false;
            }
        }
        return true;
    }

// Déplacer les lignes vers le bas à partir d'une ligne donnée
    private void shiftRowsDown(int[][] grid, int startRow) {

        // Déplacer chaque ligne au-dessus de startRow vers le bas
        for (int i = startRow; i > 0; i--) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = grid[i - 1][j];
            }
        }

        // Créer une nouvelle ligne vide en haut
        for (int j = 0; j < grid[0].length; j++) {
            grid[0][j] = VIDE;
        }
    }

    // Function to clear a specific row and shift rows above it down
    private void clearRow(int[][] grid, int rowIndex) {
        for (int i = rowIndex; i > 0; i--) {
            grid[i] = grid[VIDE]; // Shift the row above down
        }
        grid[0] = new int[grid[0].length]; // Clear the top row
    }

    public boolean CanAddBlockToGrid(int[][] copyGrid) {
        // Vérifier si currentPiece est null
        if (currentPiece == null) {
            return false;
        }

        // Vérifier si index_rotation est valide
        int rotationCount = currentPiece.getRotation(index_rotation).length;
        if (index_rotation < 0 || index_rotation >= rotationCount) {
            index_rotation = 0; // Réinitialiser à une valeur valide
        }
        int[][] currentShape = currentPiece.getRotation(index_rotation);

        // Parcourir la forme du bloc actuel
        for (int i = 0; i < currentShape.length; i++) {
            for (int j = 0; j < currentShape[i].length; j++) {
                // Ignorer les cellules vides du bloc
                if (currentShape[i][j] == 0) {
                    continue;
                }

                int gridY = indexOffset_x + i;
                int gridX = indexOffset_y + j;

                // Vérifier les limites de la grille avec plus de souplesse
                if (gridY < 0 || gridY >= row || gridX < 0 || gridX >= column) {
                    return false;
                }

                // Vérifier la collision avec des blocs existants
                if (copyGrid[gridY][gridX] == BLOCK_POSER) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean canSpawn(int[][] blockShape, int[][] gridGame, int indexOffset_x, int indexOffset_y) {
        // Check if the block can be placed at the initial spawn position
        for (int i = 0; i < blockShape.length; i++) {
            for (int j = 0; j < blockShape[i].length; j++) {
                if (blockShape[i][j] == 1) {
                    int x = indexOffset_x + i;
                    int y = indexOffset_y + j;
                    // Check if the position is inside the grid
                    if (x >= gridGame.length || y >= gridGame[0].length || y < 0) {
                        return false; // Outside grid bounds
                    }
                    // Check for collision with existing blocks
                    if (gridGame[x][y] != VIDE) {
                        return false; // Block already present, cannot spawn
                    }
                }
            }
        }
        return true;
    }

    private boolean isGameOver() {
        // Position initiale pour un nouveau bloc
        int initialX = 0;
        int initialY = (int) (grid[0].length * 0.5f - currentPiece.getRotation(index_rotation).length * 0.5f);

        // Sauvegarder la position actuelle
        int savedX = indexOffset_x;
        int savedY = indexOffset_y;

        // Temporairement mettre le bloc à la position initiale
        indexOffset_x = initialX;
        indexOffset_y = initialY;

        // Créer une copie de la grille pour le test
        int[][] testGrid = copyGrid(grid);

        // Vérifier si le bloc peut être placé à la position initiale
        boolean canPlace = CanAddBlockToGrid(testGrid);

        // Restaurer la position originale
        indexOffset_x = savedX;
        indexOffset_y = savedY;

        return !canPlace;
    }

// Modifiez la méthode endGame pour la rendre non-statique et plus complète
    private void endGame() {
        isGameOver = true;
        panel.endGame();

    }

    private void convertBlockOnGrid(int[][] gridGame) {

        for (int i = 0; i < gridGame.length; i++) {
            for (int j = 0; j < gridGame[0].length; j++) {
                if (gridGame[i][j] == COLLISION || gridGame[i][j] == BLOCK_PLAYER) {
                    gridGame[i][j] = BLOCK_POSER;                                 // Si le slot = collision ou slot = block player alors il est transformer en block poser
                }
            }

        }

    }

    private boolean hasCollision(int[][] grid) {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == COLLISION) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[][] clearBlockInGrid(int[][] grid) {
        for (int[] grid1 : grid) {
            for (int j = 0; j < grid1.length; j++) {
                if (grid1[j] == BLOCK_PLAYER) {
                    grid1[j] = VIDE;
                }
            }
        }
        return grid;
    }

// Helper method to print the grid (for debugging purposes)
    public void printGrid(int[][] grid) {
        for (int[] grid1 : grid) {
            for (int j = 0; j < grid1.length; j++) {
                System.out.print(grid1[j] + " ");
            }
            System.out.println(); // Nouvelle ligne après chaque ligne de la grille
        }
    }

// Helper method to create a copy of the grid
    private int[][] copyGrid(int[][] grid) {
        int[][] newGrid = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                newGrid[i][j] = grid[i][j];
            }
        }
        return newGrid;
    }

    public void CreateNewBlock() {
        currentPiece = getRandomBlock();
        // Définir la position de départ du bloc, centré dans la grille en haut
        indexOffset_y = (int) (grid[0].length * 0.5f - currentPiece.getRotation(index_rotation).length * 0.5f);
        indexOffset_x = 0;
        // Récupérer la forme du blocc pour l'orientation 0 (première orientation)
        int[][] blockShape = currentPiece.getRotation(index_rotation);

        if (canSpawn(blockShape, grid, indexOffset_x, indexOffset_y)) {
            for (int i = 0; i < blockShape.length; i++) {
                for (int j = 0; j < blockShape[i].length; j++) {
                    if (blockShape[i][j] == 1) {
                        grid[indexOffset_x + i][indexOffset_y + j] = BLOCK_PLAYER;
                    }
                }
            }
        } else {
            // GAME OVER
            System.out.println("Game Over !");
            panel.endGame();
        }
    }

    private int[][] addBlockToGrid(int[][] copyGrid) {
        int[][] currentShape = currentPiece.getRotation(index_rotation);

        // Parcourir la forme du bloc actuel
        for (int i = 0; i < currentShape.length; i++) {
            for (int j = 0; j < currentShape[i].length; j++) {
                // Dans la grille du jeu, y représente les lignes (vertical) et x les colonnes (horizontal)
                int gridY = indexOffset_x + i;    // Position verticale dans la grille
                int gridX = indexOffset_y + j;    // Position horizontale dans la grille

                // Vérifier que les indices sont dans les limites de la grille
                if (gridY >= 0 && gridY < copyGrid.length
                        && gridX >= 0 && gridX < copyGrid[0].length) {
                    // Ne placer que les parties non vides du bloc
                    if (currentShape[i][j] != 0) {
                        copyGrid[gridY][gridX] = BLOCK_PLAYER;
                    }
                }
            }
        }
        return copyGrid;
    }

    /**
     * Vérifie si un bloc dépasse de la grille de jeu
     *
     * @param blockShape La forme du bloc (tableau 2D)
     * @param posX Position X du bloc dans la grille
     * @param posY Position Y du bloc dans la grille
     * @param gridWidth Largeur de la grille
     * @param gridHeight Hauteur de la grille
     * @return true si le bloc dépasse de la grille, false sinon
     */
    public boolean isBlockOutOfBounds(int[][] blockShape, int posX, int posY, int gridWidth, int gridHeight) {
        // Parcourir chaque cellule du bloc
        for (int y = 0; y < blockShape.length; y++) {
            for (int x = 0; x < blockShape[y].length; x++) {
                // Vérifier uniquement les cellules non vides (valeur différente de 0)
                if (blockShape[y][x] != 0) {
                    // Calculer la position absolue dans la grille
                    int gridX = posX + x;
                    int gridY = posY + y;

                    // Vérifier si cette position est en dehors de la grille
                    if (gridX < 0 || gridX >= gridWidth || gridY < 0 || gridY >= gridHeight) {
                        return true; // Le bloc dépasse de la grille
                    }
                }
            }
        }

        // Si on arrive ici, aucune cellule ne dépasse
        return false;
    }

    ////////////////////////////FONCTION GETTER ET SETTER //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int getStart_x() {
        return indexOffset_x;
    }

    public void setStart_x(int start_x) {
        this.indexOffset_x = start_x;
    }

    public int getStart_y() {
        return indexOffset_y;
    }

    public void setStart_y(int start_y) {
        this.indexOffset_y = start_y;
    }

    public AbstractBlock getNewBlock() {
        return currentPiece;
    }

    public int getIndex_rotation() {
        return index_rotation;
    }

    public int[][] getGridGame() {
        return grid;
    }

    public boolean getIsGameOver() {
        return isGameOver;
    }

    public int getWidth() {
        return grid[0].length;
    }
    
    // Getters pour évaluer la grille
    public int getHeight() {
        int height = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != NONE) {
                    height = Math.max(height, i + 1);
                }
            }
        }
        return height;
    }
}
