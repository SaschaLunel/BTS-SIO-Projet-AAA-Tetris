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
    
    public static int row= 10;
    public static int column = 20; 
    static GridSlot[][] grid = new GridSlot [column][row];
    static BufferedImage[][] images = new BufferedImage [column][row];
    static GridSlot slot = new GridSlot();
   
    
    public Grid() {
        
        System.out.println("truc");
        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                images[i][j]=slot.getImage(true) ;
            }
            
        }
        
    }
    
    public int getLenght(){
        return grid.length;
    }
    
    public int getILenght(){
        return grid[0].length;
    }
    
    public BufferedImage getImage(int i, int j){
        return images[i][j];
    }
    
   
}
    

