/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gameplay;

/**
 *
 * @author SIO
 */
public class Grid {
    
    static int row= 10;
    static int column = 25; 
    static boolean[][] grid = new boolean [column][row];
    
   
    public static void main(String[] args) {
        // Création d'une matrice vide de 3x3
        

        // Affichage de la matrice vide
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " "); // Affiche false par défaut
            }
            System.out.println(); // Nouvelle ligne après chaque rangée
        }
    }
}
    

