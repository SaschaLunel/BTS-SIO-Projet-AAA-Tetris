/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gameplay;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 *
 * @author SIO
 */
public class Grid {
    
    static int row= 10;
    static int column = 25; 
    static GridSlot[][] grid = new GridSlot [column][row];
    public static ArrayList<BufferedImage> images = new ArrayList<>();
    
    
   
    public static void main(String[] args) {
        // Création d'une matrice vide de 3x3
        

        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
                BufferedImage imageSet = grid[i][j].getSlot(false);
                images.add(imageSet);
                

            }
            System.out.println(); // Nouvelle ligne après chaque rangée
        }
    }
}
    

