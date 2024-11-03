/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import Gameplay.Grid;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author SIO
 */
public class PanelGame extends JPanel {
    
    public int sizeHeight = 720;
    public int sizeWidth = 1080;
    
    Grid grid = new Grid();
    public static BufferedImage background;
    
    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("paintCOmponent");;
        super.paintComponent(g);
        
        
        
        float ratiocell = 0.035f;
        sizeWidth = getWidth();
        sizeHeight = getHeight();
       
        float cellWidth = sizeHeight*ratiocell; // Largeur d'une case
        float cellHeight = sizeHeight*ratiocell;// Hauteur d'une case
        
        int cellSize = Math.round(sizeHeight*ratiocell);
        
        int gridOffsetX = Math.round(sizeWidth*0.5f-(Grid.row*0.5f*cellWidth));
        
        g.drawImage(background, 0, 0, sizeWidth, sizeHeight,  this);

        for (int i = 0; i < grid.getLenght(); i++) {
            for (int j = 0; j < grid.getILenght(); j++) {
                int x = Math.round(cellWidth * j);
                int y = Math.round(cellHeight * i);
                BufferedImage image = grid.getImage(i, j);
                g.drawImage(image, x+gridOffsetX, y+1, cellSize, cellSize, this);
            }
            
        }    
        
        
    }
    
   
    
    public PanelGame() {
        grid = new Grid();
        
        
        String directoryProject = System.getProperty("user.dir");
        
        try {
            background = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\ImageFolder\\background.png")));
            
        } catch (IOException e) {
            // Log the error or handle it appropriately
            System.err.println("Error loading background: " + e.getMessage());
            // Provide default images or set to null
            
        }
        
        
        
    }
    
    
    
    
    
    
    
}
