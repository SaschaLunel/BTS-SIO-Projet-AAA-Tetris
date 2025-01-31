/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gameplay;

import BlockFolder.AbstractBlock;
import BlockFolder.LBlock;
import BlockFolder.OBlock;
import BlockFolder.ZBlock;
import BlockFolder.SBlock;
import BlockFolder.JBlock;
import BlockFolder.IBlock;
import BlockFolder.TBlock;
import java.util.Random;

/**
 *
 * @author SIO
 */
public class GridGame {

    private int[][][] blockShape;
    private int[][] gridGame;
    private int row;
    private int column;
    private int index_color;
    private int index_rotation;

    public int indexOffset_x;
    public int indexOffset_y;
    public AbstractBlock new_block;

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

    private static final String[] ArrayNomenclatureBlock = {
        "none",
        "vide",
        "block_player",
        "collision",
        "block_poser"
    };

    public int getBlockIndex(String blockName) {
        for (int i = 0; i < ArrayNomenclatureBlock.length; i++) {
            if (ArrayNomenclatureBlock[i].equals(blockName)) {
                return i; // Retourne l'indice si le nom correspond
            }
        }
        return -1; // Retourne -1 si le nom n'est pas trouvé
    }

    public GridGame() {
    }

    public GridGame(int row, int column) {
        this.column = column;
        this.row = row;
        gridGame = new int[row][column];
        // Initialisation de chaque élément à 1
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                gridGame[i][j] = getBlockIndex("vide"); // Set each element to 1
            }
        }
    }

    // Méthode pour récupérer un bloc aléatoire
    public static AbstractBlock getRandomBlock() {
        Random random = new Random();
        int randomIndex = random.nextInt(blocks.length); // Génère un index aléatoire
        return blocks[randomIndex]; // Retourne le bloc à cet index
    }

    public void setIndex_rotation(int index_rotation) {
        this.index_rotation = index_rotation;
    }

    // Méthode qui renvoie une grille
    public int[][] CreateGrid() {

        // Récupérer un bloc aléatoire
        new_block = getRandomBlock();
        blockShape = new_block.getShape();

        // Définir la position de départ du bloc, centré dans la grille en haut
        indexOffset_y = (int) (gridGame[0].length * 0.5f - new_block.getShape()[0].length * 0.5f);
        indexOffset_x = 0;

        // Récupérer la forme du bloc pour l'orientation 0 (première orientation)
        int[][] blockShape = new_block.getShape()[0];

        // Placer le bloc dans la grille
        for (int i = 0; i < blockShape.length; i++) {
            for (int j = 0; j < blockShape[i].length; j++) {
                // Vérifier si la position du bloc est valide (dans les limites de la grille)
                if (indexOffset_x + i < gridGame.length && indexOffset_y + j < gridGame[0].length) {
                    if (blockShape[i][j] != 0) {
                        gridGame[indexOffset_x + i][indexOffset_y + j] = getBlockIndex("block_player");   // Placer le bloc dans la grille
                    }

                }
            }
        }

        return gridGame;
    }

    ////////////////////////////FONCTION PRIMAIRE//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void movePiece(int direction) {
        // Vérifier d'abord si le mouvement est possible
        int newY = indexOffset_y + direction;

        // Obtenir la largeur du bloc actuel
        int[][] currentShape = new_block.getShape()[index_rotation];
        int blockWidth = currentShape[0].length;

        // Vérifier les limites horizontales
        if (newY < 0 || newY + blockWidth > column) {
            return; // Ne pas permettre le mouvement si cela causerait un débordement
        }

        int[][] copyGridGame = copyGrid(gridGame);
        copyGridGame = clearBlockInGrid(copyGridGame);

        // Mettre à jour temporairement la position pour le test
        indexOffset_y = newY;

        // Vérifier si le bloc peut être placé à la nouvelle position
        if (CanAddBlockToGrid(copyGridGame)) {
            gridGame = addBlockToGrid(copyGridGame);
        } else {
            // Si le mouvement n'est pas possible, restaurer l'ancienne position
            indexOffset_y = newY - direction;
        }
    }

    public void dropBlock() {
        // Créer une copie de la grille
        int[][] copyGridGame = copyGrid(gridGame);

        // Effacer l'ancien bloc (mettre les cases à "vide")
        copyGridGame = clearBlockInGrid(copyGridGame);

        // Incrémenter la position pour descendre le bloc
        indexOffset_x += 1;

        if (CanAddBlockToGrid(copyGridGame)) {
            // Si le bloc peut être placé, mettre à jour la grille
            gridGame = addBlockToGrid(copyGridGame);
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
        int[][] gridCopy = copyGrid(gridGame);
        gridCopy = clearBlockInGrid(gridCopy);

        indexOffset_x += 1;

        if (CanAddBlockToGrid(gridCopy)) {
            gridGame = addBlockToGrid(gridCopy);
            return true; // Le mouvement a été effectué
        } else {
            indexOffset_x -= 1;
            return false; // Le mouvement n'a pas été effectué
        }
    }

    public int[][] rotationGrid() {

        index_rotation = (index_rotation + 1) % 4;

        int[][] NewBlockShape = new_block.getShape()[index_rotation];

        int[][] copyGridGame = copyGrid(gridGame);

        copyGridGame = clearBlockInGrid(copyGridGame);

        for (int i = 0; i < NewBlockShape.length; i++) {
            for (int j = 0; j < NewBlockShape[i].length; j++) {
                // Vérifier si la position du bloc est valide (dans les limites de la grille)
                if (indexOffset_x + i < copyGridGame.length && indexOffset_y + j < gridGame[0].length) {
                    copyGridGame[indexOffset_x + i][indexOffset_y + j] = NewBlockShape[i][j] + copyGridGame[indexOffset_x + i][indexOffset_y + j]; // Placer le bloc dans la grille
                }
            }
        }

        if (hasCollision(gridGame)) {
            System.out.println("collision detected");
            return gridGame;
        }

        return copyGridGame;
    }

    ////////////////////////////FONCTION SECONDAIRE//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void handleBlockLock() {
        
        ScoreWidget.addScore(20);
        
        System.out.println("Block locked in place!");

        // Vérifier que le bloc actuel existe
        if (new_block == null) {
            System.err.println("Erreur: Aucun bloc actif à verrouiller.");
            return;
        }

        // Convertir le bloc en position verrouillée
        convertBlockOnGrid(gridGame);

        // Vérifier et supprimer les lignes complètes
        clearCompletedRows(gridGame);

        // Tenter de générer un nouveau bloc
        new_block = getRandomBlock();
        indexOffset_x = 0;
        indexOffset_y = (gridGame[0].length - new_block.getShape()[0].length) / 2;
        index_rotation = 0;

        // Vérifier si le nouveau bloc peut être placé, sinon fin du jeu
        if (!CanAddBlockToGrid(gridGame)) {
            System.out.println("Game Over!");
            endGame();
        }
    }

    public boolean canMoveHorizontal(int[][] piece, int direction) {
        int height = piece.length;
        int width = piece[0].length;

        // Vérifier les limites de la grille
        int newPosX = indexOffset_x + direction;
        if (newPosX < 0 || newPosX + width > gridGame[0].length) {
            return false;
        }

        // Vérifier les collisions avec les autres blocs
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (piece[y][x] == 3) {
                    int gridCell = gridGame[indexOffset_y + y][newPosX + x];
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
        
        // Parcourir la grille du bas vers le haut
        for (int i = grid.length - 1; i >= 0; i--) {
            if (isRowComplete(grid[i])) {
                ScoreWidget.addScore(1000);
                // Si une ligne est complète, déplacer toutes les lignes au-dessus vers le bas
                shiftRowsDown(grid, i);
            }
        }
    }

// Vérifier si une ligne est complète
    private boolean isRowComplete(int[] row) {
        for (int cell : row) {
            if (cell != getBlockIndex("block_poser")) {
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
            grid[0][j] = getBlockIndex("vide");
        }
    }

    // Function to clear a specific row and shift rows above it down
    private void clearRow(int[][] grid, int rowIndex) {
        for (int i = rowIndex; i > 0; i--) {
            grid[i] = grid[getBlockIndex("vide")]; // Shift the row above down
        }
        grid[0] = new int[grid[0].length]; // Clear the top row
    }

    public boolean CanAddBlockToGrid(int[][] copyGrid) {
        int[][] currentShape = new_block.getShape()[index_rotation];
        int blockWidth = currentShape[0].length;
        int blockHeight = currentShape.length;

        // Vérifier d'abord les limites globales
        if (indexOffset_y < 0
                || indexOffset_y + blockWidth > column
                || indexOffset_x < 0
                || indexOffset_x + blockHeight > row) {
            return false;
        }

        // Parcourir la forme du bloc actuel
        for (int i = 0; i < blockHeight; i++) {
            for (int j = 0; j < blockWidth; j++) {
                // Ignorer les cellules vides du bloc
                if (currentShape[i][j] == 0) {
                    continue;
                }

                int gridY = indexOffset_x + i;
                int gridX = indexOffset_y + j;

                // Vérifier la collision avec des blocs existants
                if (copyGrid[gridY][gridX] == getBlockIndex("block_poser")) {
                    return false;
                }
            }
        }

        return true;
    }

// Function to end the game (could include score handling, UI updates, etc.)
    private static void endGame() {
        System.out.println("Game Over! Thanks for playing.");
        // Add additional logic to reset or stop the game
    }

//    // Function to spawn a new Tetromino
//    private boolean spawnNewBlock(GridGame gridGame) {
//        // Create and set a new Tetromino
//        AbstractBlock newBlock = getRandomBlock(); // Assume a BlockFactory exists
//        gridGame.new_block = newBlock;
//        gridGame.setStart_x(0); // Reset position at the top of the grid
//        gridGame.setIndex_rotation(0);
//
//        // Check if the new Tetromino can be placed
//        int[][] initialBlockShape = newBlock.getShape()[0];
//        return CanAddBlockToGrid(this.gridGame);
//    }
    private void convertBlockOnGrid(int[][] gridGame) {

        for (int i = 0; i < gridGame.length; i++) {
            for (int j = 0; j < gridGame[0].length; j++) {
                if (gridGame[i][j] == 3 || gridGame[i][j] == 2) {
                    gridGame[i][j] = 4;                                 // Si le slot = collision ou slot = block player alors il est transformer en block poser
                }
            }

        }

    }

    private boolean hasCollision(int[][] grid) {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 4) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[][] clearBlockInGrid(int[][] grid) {
        for (int[] grid1 : grid) {
            for (int j = 0; j < grid1.length; j++) {
                if (grid1[j] == getBlockIndex("block_player")) {
                    grid1[j] = getBlockIndex("vide");
                }
            }
        }
        return grid;
    }

// Helper method to print the gridGame (for debugging purposes)
    public void printGrid(int[][] grid) {
        for (int[] grid1 : grid) {
            for (int j = 0; j < grid1.length; j++) {
                System.out.print(grid1[j] + " ");
            }
            System.out.println(); // Nouvelle ligne après chaque ligne de la grille
        }
    }

// Helper method to create a copy of the gridGame
    private int[][] copyGrid(int[][] grid) {
        int[][] newGrid = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(grid[i], 0, newGrid[i], 0, grid[i].length);
        }
        return newGrid;
    }

    public void CreateNewBlock() {
        new_block = getRandomBlock();
        // Définir la position de départ du bloc, centré dans la grille en haut
        indexOffset_y = (int) (gridGame[0].length * 0.5f - new_block.getShape()[0].length * 0.5f);
        indexOffset_x = 0;
        // Récupérer la forme du bloc pour l'orientation 0 (première orientation)
        int[][] blockShape = new_block.getShape()[0];

        // Placer le bloc dans la grille
        for (int i = 0; i < blockShape.length; i++) {
            for (int j = 0; j < blockShape[i].length; j++) {
                // Vérifier si la position du bloc est valide (dans les limites de la grille)
                if (indexOffset_x + i < gridGame.length && indexOffset_y + j < gridGame[0].length) {
                    if (blockShape[i][j] == 1) {
                        gridGame[indexOffset_x + i][indexOffset_y + j] = getBlockIndex("block_player"); // Placer le bloc dans la grille
                    }

                }
            }
        }
    }

    private int[][] addBlockToGrid(int[][] copyGrid) {
        int[][] currentShape = new_block.getShape()[index_rotation];

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
                        copyGrid[gridY][gridX] = getBlockIndex("block_player");
                    }
                }
            }
        }
        return copyGrid;
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
        return new_block;
    }

    public int getIndex_rotation() {
        return index_rotation;
    }

    public int[][] getGridGame() {
        return gridGame;
    }

}
