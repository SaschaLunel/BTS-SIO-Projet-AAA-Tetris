/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import BlockFolder.AbstractBlock;
import Gameplay.GridGame;

/**
 *
 * @author SIO
 */
public class PanelGameUtils {

    //Function qui permets de vérifé si le block en cours peut descendre 
    protected static boolean canMoveBlock() {
        return true;
    }

    protected static void dropBlock(PanelGame panelGame, GridGame grid) {
        // Créer une copie de la grille
        int[][] copyGridGame = copyGrid(panelGame.gridGameInstance.gridGame);

        // Effacer l'ancien bloc (mettre les cases à "vide")
        copyGridGame = panelGame.gridGameInstance.clearBlockInGrid(copyGridGame);

        // Incrémenter la position pour descendre le bloc
        panelGame.gridGameInstance.setStart_x(panelGame.gridGameInstance.getStart_x() + 1);

        // Récupérer la forme du bloc avec sa rotation actuelle
        AbstractBlock currentBlock = panelGame.gridGameInstance.new_block;
        int[][][] blockShapes = currentBlock.getShape();
        int[][] rotatedBlock = blockShapes[panelGame.gridGameInstance.getIndex_rotation()];

        // Tenter d'ajouter le bloc à la nouvelle position
        boolean isAdded = CanAddBlockToGrid(
                copyGridGame,
                rotatedBlock,
                panelGame.gridGameInstance.index_x,
                panelGame.gridGameInstance.index_y
        );

        if (isAdded) {
            // Si le bloc peut être placé, mettre à jour la grille
            panelGame.gridGameInstance.gridGame = copyGridGame;
        } else {
            // Si le bloc ne peut pas descendre, annuler le déplacement et le verrouiller
            panelGame.gridGameInstance.setStart_x(panelGame.gridGameInstance.getStart_x() - 1);
            handleBlockLock(panelGame);
        }
    }

    private static void handleBlockLock(PanelGame panelGame) {
        // 1. Finalize the current Tetromino in the grid
        System.out.println("Block locked in place!");

        // Add the current block permanently to the grid
        AbstractBlock currentBlock = panelGame.gridGameInstance.new_block;
        int[][][] blockShapes = currentBlock.getShape();
        int[][] rotatedBlock = blockShapes[panelGame.gridGameInstance.getIndex_rotation()];
//        CanAddBlockToGrid(
//                panelGame.gridGameInstance.gridGame,
//                rotatedBlock,
//                panelGame.gridGameInstance.index_x,
//                panelGame.gridGameInstance.index_y
//        );
        convertBlockOnGrid(panelGame.gridGameInstance.gridGame);

        // 2. Check for and clear any completed rows
        clearCompletedRows(panelGame.gridGameInstance.gridGame);

        // 3. Prepare for the next Tetromino
        if (!spawnNewBlock(panelGame.gridGameInstance)) {
            // If unable to spawn a new block, the game is over
            System.out.println("Game Over!");
            endGame();
        }
    }

// Function to clear completed rows in the grid
    private static void clearCompletedRows(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            boolean isRowComplete = true;

            // Check if the row is completely filled
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == PanelGame.gridGameInstance.getBlockIndex("vide")) {
                    isRowComplete = false;
                    break;
                }
            }

            // If the row is complete, clear it and shift rows above down
            if (isRowComplete) {
                clearRow(grid, i);
                i--; // Re-check the same row index after shifting
            }
        }
    }

// Function to clear a specific row and shift rows above it down
    private static void clearRow(int[][] grid, int rowIndex) {
        for (int i = rowIndex; i > 0; i--) {
            grid[i] = grid[PanelGame.gridGameInstance.getBlockIndex("vide")]; // Shift the row above down
        }
        grid[0] = new int[grid[0].length]; // Clear the top row
    }

    private void SuccesBlock() {
        replaceOneAndTwoWithSeven(PanelGame.gridGameInstance.gridGame);
        PanelGame.gridGameInstance.CreateNewBlock();
    }

    public static boolean canMoveHorizontal(int[][] piece, int posX, int posY, int direction) {
        int height = piece.length;
        int width = piece[0].length;

        // Vérifier les limites de la grille
        int newPosX = posX + direction;
        if (newPosX < 0 || newPosX + width > PanelGame.gridGameInstance.gridGame[0].length) {
            return false;
        }

        // Vérifier les collisions avec les autres blocs
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (piece[y][x] == 3) {
                    int gridCell = PanelGame.gridGameInstance.gridGame[posY + y][newPosX + x];
                    if (gridCell == 4 || gridCell == 3) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static int movePiece(int[][] piece, int posX, int posY, int direction) {
        System.out.println(canMoveHorizontal(piece, posX, posY, direction));
        if (canMoveHorizontal(piece, posX, posY, direction)) {
            // Effacer l'ancienne position (remettre à VIDE)
            int height = piece.length;
            int width = piece[0].length;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (piece[y][x] == 3) {
                        PanelGame.gridGameInstance.gridGame[posY + y][posX + x] = 1;
                    }
                }
            }

            // Dessiner à la nouvelle position
            int newPosX = posX + direction;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (piece[y][x] == 3) {
                        PanelGame.gridGameInstance.gridGame[posY + y][newPosX + x] = 3;
                    }
                }
            }
            
            return newPosX;
        }
        
        return posX;
    }

    // Helper method to create a copy of the gridGame
    private static int[][] copyGrid(int[][] grid) {
        int[][] newGrid = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(grid[i], 0, newGrid[i], 0, grid[i].length);
        }
        return newGrid;
    }

    private static void convertBlockOnGrid(int[][] gridGame) {

        for (int i = 0; i < gridGame.length; i++) {
            for (int j = 0; j < gridGame[0].length; j++) {
                if (gridGame[i][j] == 3 || gridGame[i][j] == 2) {
                    gridGame[i][j] = 4;                                 // Si le slot = collision ou slot = block player alors il est transformer en block poser
                }
            }

        }

    }

    public static boolean CanAddBlockToGrid(int[][] largeGrid, int[][] smallGrid, int originX, int originY) {
        // Vérifier que le bloc peut être inséré dans la grille aux coordonnées données
        if (originX + smallGrid.length > largeGrid.length || originY + smallGrid[0].length > largeGrid[0].length) {
            System.out.println("Le bloc dépasse les limites de la grille.");
            return false;
        }

        // Première passe : vérification des collisions
        for (int i = 0; i < smallGrid.length; i++) {
            for (int j = 0; j < smallGrid[i].length; j++) {
                if (smallGrid[i][j] == 1) {
                    // Vérifier s'il y a déjà un bloc posé (valeur 4) à cet endroit
                    if (largeGrid[originX + i][originY + j] == PanelGame.gridGameInstance.getBlockIndex("block_poser")) {
                        System.out.println("Collision détectée avec un bloc existant");
                        return false;
                    }
                }
            }
        }

        // Deuxième passe : placement du bloc
        for (int i = 0; i < smallGrid.length; i++) {
            for (int j = 0; j < smallGrid[i].length; j++) {
                if (smallGrid[i][j] == 1) {
                    // Placer le bloc avec la valeur 2 (block_player) au lieu de 3 (collision)
                    largeGrid[originX + i][originY + j] = PanelGame.gridGameInstance.getBlockIndex("block_player");
                }
            }
        }

        return true;
    }

    // Function to spawn a new Tetromino
    private static boolean spawnNewBlock(GridGame gridGame) {
        // Create and set a new Tetromino
        AbstractBlock newBlock = PanelGame.gridGameInstance.getRandomBlock(); // Assume a BlockFactory exists
        gridGame.new_block = newBlock;
        gridGame.setStart_x(0); // Reset position at the top of the grid
        gridGame.setIndex_rotation(0);

        // Check if the new Tetromino can be placed
        int[][] initialBlockShape = newBlock.getShape()[0];
        return CanAddBlockToGrid(
                gridGame.gridGame,
                initialBlockShape,
                gridGame.index_x,
                gridGame.index_y
        );
    }

// Function to end the game (could include score handling, UI updates, etc.)
    private static void endGame() {
        System.out.println("Game Over! Thanks for playing.");
        // Add additional logic to reset or stop the game
    }

// Helper method to print the gridGame (for debugging purposes)
    private void printGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println(); // Nouvelle ligne après chaque ligne de la grille
        }
    }

// Fonction pour remplacer les 1 et les 2 par des 7 dans un tableau 2D
    public static void replaceOneAndTwoWithSeven(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1 || grid[i][j] == 2) {
                    grid[i][j] = 7; // Remplacer 1 ou 2 par 7
                }
            }
        }
    }

}
