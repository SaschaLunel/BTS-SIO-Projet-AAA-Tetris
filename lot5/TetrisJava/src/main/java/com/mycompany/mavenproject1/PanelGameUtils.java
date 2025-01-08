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
        if (canMoveBlock()){
        // Parcourir la grille de bas en haut pour éviter les collisions
        for (int i = PanelGame.grid_pattern.length - 2; i >= 0; i--) { // -2 car on commence à l'avant-dernier
            // Copier la ligne actuelle dans la ligne du dessous
            PanelGame.grid_pattern[i + 1] = PanelGame.grid_pattern[i];
        }
        // Vider la première ligne après le déplacement
        PanelGame.grid_pattern[0] = new int[PanelGame.column];
        
        PanelGame.grid_block.setStart_x(PanelGame.grid_block.getStart_x()+1);
        }
        else {
            
        }
    }
    
     protected static  void moveBlock(PanelGame PanelGame, int direction) {
            
            int block_length = PanelGame.grid_block.getNewBlock().length();
        
            if (direction > 0 && PanelGame.grid_block.getStart_y()< PanelGame.grid_pattern[0].length - (PanelGame.grid_block.getNewBlock().length())) {

                // Parcourir la grille de bas en haut pour éviter les collisions
                for (int i = PanelGame.grid_pattern.length - 2; i >= 0; i--) { // -2 car on commence à l'avant-dernier
                    // Copier la ligne actuelle dans la ligne du dessous
                    for (int j = PanelGame.grid_pattern[i].length - 2; j >= 0; j--) {
                        PanelGame.grid_pattern[i][j + 1] = PanelGame.grid_pattern[i][j];
                    }
                    // Vider la première ligne après le déplacement
                    PanelGame.grid_pattern[i][0] = 0; // 0 représente une case vide // Suppose qu'une ligne vide est un tableau d'entiers initialisés à 0
                }

                System.out.println(PanelGame.grid_block.getStart_y() + "<<" + (PanelGame.grid_pattern[0].length - PanelGame.grid_block.getNewBlock().length()));
                 PanelGame.grid_block.setStart_y(PanelGame.grid_block.getStart_y()+1);
                PanelGame.repaint();

            } 

            else if (direction < 0 && PanelGame.grid_block.getStart_y()> 0) { // Déplacement à gauche
            // Parcourir la grille de haut en bas
            for (int i = 0; i < PanelGame.grid_pattern.length; i++) {
                for (int j = 1; j < PanelGame.grid_pattern[i].length; j++) { // Parcourir de gauche à droite
                    PanelGame.grid_pattern[i][j - 1] = PanelGame.grid_pattern[i][j]; // Déplacer vers la gauche
                }
                PanelGame.grid_pattern[i][PanelGame.grid_pattern[i].length - 1] = 0; // Effacer la dernière colonne
            }
                PanelGame.grid_block.setStart_y(PanelGame.grid_block.getStart_y()-1);
                PanelGame.repaint();
            }

    }
}
