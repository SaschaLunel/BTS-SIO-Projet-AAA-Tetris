/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;


/**
 *
 * @author SIO
 */
public class PanelGameUtils {

    
    //Function qui permets de vérifé si le block en cours peut descendre 
    
    protected static boolean canMoveBlock(){
        return true;
    }
    
    // Fonction pour faire descendre le Tetromino
    protected static void dropBlock(PanelGame PanelGame) {
        
        PanelGame.gridGameInstance.setStart_x(PanelGame.gridGameInstance.getStart_x() + 1);
        
        int[][] copyGridGame = copyGrid(PanelGame.gridGameInstance.gridGame);
        int[] localRow = copyGridGame[-1];
        
        PanelGame.gridGameInstance.clearBlockInGrid(copyGridGame);
        
        // Parcourir la grille de bas en haut pour éviter les collisions
        for (int i = copyGridGame.length - 2; i >= 0; i--) { // -2 car on commence à l'avant-dernier
            // Copier la ligne actuelle dans la ligne du dessous
            copyGridGame[i-1] = copyGridGame[i];
        }
        // Vider la première ligne après le déplacement
        copyGridGame[0] = new int[PanelGame.column];
        
        PanelGame.gridGameInstance.setStart_x(PanelGame.gridGameInstance.getStart_x()+1);
        
        }

    
    
     protected static  void moveBlock(PanelGame PanelGame, int direction) {
            
            int block_length = PanelGame.gridGameInstance.getNewBlock().length();
            
            if (!canMoveBlock()){
            return;
            }


        
            if (direction > 0 && PanelGame.gridGameInstance.getStart_y()< PanelGame.gridGameInstance.gridGame[0].length - (PanelGame.gridGameInstance.getNewBlock().length())) {

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
                 PanelGame.gridGameInstance.setStart_y(PanelGame.gridGameInstance.getStart_y()+1);
                PanelGame.repaint();

            } 

            else if (direction < 0 && PanelGame.gridGameInstance.getStart_y()> 0) { // Déplacement à gauche
            // Parcourir la grille de haut en bas
            for (int i = 0; i < PanelGame.gridGameInstance.gridGame.length; i++) {
                for (int j = 1; j < PanelGame.gridGameInstance.gridGame[i].length; j++) { // Parcourir de gauche à droite
                    PanelGame.gridGameInstance.gridGame[i][j - 1] = PanelGame.gridGameInstance.gridGame[i][j]; // Déplacer vers la gauche
                }
                PanelGame.gridGameInstance.gridGame[i][PanelGame.gridGameInstance.gridGame[i].length - 1] = 0; // Effacer la dernière colonne
            }
                PanelGame.gridGameInstance.setStart_y(PanelGame.gridGameInstance.getStart_y()-1);
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
}
