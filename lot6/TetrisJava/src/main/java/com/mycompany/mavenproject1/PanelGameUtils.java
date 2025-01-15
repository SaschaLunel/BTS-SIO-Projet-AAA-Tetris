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

    // Function to drop the Tetromino one step down
    protected static void dropBlock(PanelGame panelGame, GridGame grid) {

        // Increment the origin of the block to move it down
        panelGame.gridGameInstance.setStart_x(panelGame.gridGameInstance.getStart_x() + 1);

        // Create a copy of the current grid
        int[][] copyGridGame = copyGrid(panelGame.gridGameInstance.gridGame);

        // Retrieve the current block's shape based on its rotation index
        AbstractBlock currentBlock = panelGame.gridGameInstance.new_block;
        int[][][] blockShapes = currentBlock.getShape();
        int[][] rotatedBlock = blockShapes[panelGame.gridGameInstance.getIndex_rotation()];

        // Attempt to add the block to the new position in the grid
        boolean isAdded = addBlockToGrid(
                copyGridGame,
                rotatedBlock,
                panelGame.gridGameInstance.index_x,
                panelGame.gridGameInstance.index_y
        );

        if (isAdded) {
            // Clear the current block's position in the copied grid
            panelGame.gridGameInstance.clearBlockInGrid(copyGridGame);
            // Update the grid with the new block position
            panelGame.gridGameInstance.gridGame = copyGridGame;

        } else {
            // Handle the case where the block can no longer move down
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
        addBlockToGrid(
                panelGame.gridGameInstance.gridGame,
                rotatedBlock,
                panelGame.gridGameInstance.index_x,
                panelGame.gridGameInstance.index_y
        );

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
                if (grid[i][j] == 0) {
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
            grid[i] = grid[i - 1]; // Shift the row above down
        }
        grid[0] = new int[grid[0].length]; // Clear the top row
    }

    private static void SuccesBlock() {
        replaceOneAndTwoWithSeven(PanelGame.gridGameInstance.gridGame);
        PanelGame.gridGameInstance.CreateNewBlock();
    }

    protected static void moveBlock(PanelGame PanelGame, int direction) {

        int block_length = PanelGame.gridGameInstance.getNewBlock().length();

        if (!canMoveBlock()) {
            return;
        }

        if (direction > 0 && PanelGame.gridGameInstance.getStart_y() < PanelGame.gridGameInstance.gridGame[0].length - (PanelGame.gridGameInstance.getNewBlock().length())) {

            // Parcourir la grille de bas en haut pour éviter les collisions
            for (int i = PanelGame.gridGameInstance.gridGame.length - 2; i >= 0; i--) { // -2 car on commence à l'avant-dernier
                // Copier la ligne actuelle dans la ligne du dessous
                for (int j = PanelGame.gridGameInstance.gridGame[i].length - 2; j >= 0; j--) {
                    PanelGame.gridGameInstance.gridGame[i][j + 1] = PanelGame.gridGameInstance.gridGame[i][j];
                }
                // Vider la première ligne après le déplacement
                PanelGame.gridGameInstance.gridGame[i][0] = 0; // 0 représente une case vide // Suppose qu'une ligne vide est un tableau d'entiers initialisés à 0
            }

            System.out.println(PanelGame.gridGameInstance.getStart_y() + "<<" + (PanelGame.gridGameInstance.gridGame[0].length - PanelGame.gridGameInstance.getNewBlock().length()));
            PanelGame.gridGameInstance.setStart_y(PanelGame.gridGameInstance.getStart_y() + 1);
            PanelGame.repaint();

        } else if (direction < 0 && PanelGame.gridGameInstance.getStart_y() > 0) { // Déplacement à gauche
            // Parcourir la grille de haut en bas
            for (int i = 0; i < PanelGame.gridGameInstance.gridGame.length; i++) {
                for (int j = 1; j < PanelGame.gridGameInstance.gridGame[i].length; j++) { // Parcourir de gauche à droite
                    PanelGame.gridGameInstance.gridGame[i][j - 1] = PanelGame.gridGameInstance.gridGame[i][j]; // Déplacer vers la gauche
                }
                PanelGame.gridGameInstance.gridGame[i][PanelGame.gridGameInstance.gridGame[i].length - 1] = 0; // Effacer la dernière colonne
            }
            PanelGame.gridGameInstance.setStart_y(PanelGame.gridGameInstance.getStart_y() - 1);
            PanelGame.repaint();
        }

    }

    // Helper method to create a copy of the gridGame
    private static int[][] copyGrid(int[][] grid) {
        int[][] newGrid = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(grid[i], 0, newGrid[i], 0, grid[i].length);
        }
        return newGrid;
    }

    // Fonction pour ajouter une grille 4x4 dans une grille 20x20 à une position d'origine variable
    public static boolean addBlockToGrid(int[][] largeGrid, int[][] smallGrid, int originX, int originY) {
        // Vérifier que la grille 4x4 peut être insérée dans la grille 20x20 à la position donnée
        if (originX + smallGrid.length <= largeGrid.length && originY + smallGrid[0].length <= largeGrid[0].length) {
            // Ajouter la grille 4x4 dans la grille 20x20 en additionnant les valeurs
            for (int i = 0; i < smallGrid.length; i++) {
                for (int j = 0; j < smallGrid[i].length; j++) {
                    largeGrid[originX + i][originY + j] += smallGrid[i][j]; // Additionner les valeurs
                    if (largeGrid[originX + i][originY + j] % 2 == 0 && largeGrid[originX + i][originY + j] != 0) {
                        System.out.println("Il y a un nombre pair >0");
                        return false;
                    }
                }

            }
        } else {
            System.out.println("La grille 4x4 dépasse les limites de la grille 20x20.");
            return false;
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
        return addBlockToGrid(
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
