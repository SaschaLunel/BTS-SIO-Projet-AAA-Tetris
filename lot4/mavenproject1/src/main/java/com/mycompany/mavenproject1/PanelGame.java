/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import Gameplay.Grid;
import BlockFolder.Block;
import static Gameplay.GridSlot.image;
import Scripts.PlayerController;
import Gameplay.TimerWidget;
import java.awt.Color;
import java.awt.Font;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 *
 * @author SIO
 */
public class PanelGame extends JPanel {
    
    private int secondes = 0;
    private Timer swingTimer;
    private TimerWidget timer = new TimerWidget();
    
    public int sizeHeight = 720;
    public int sizeWidth = 1080;
    
    static Grid grid = new Grid();
    public static BufferedImage background;
    static BufferedImage blockImage;
    
    static PlayerController pcGame = new PlayerController();
    static Block block = new Block();
    static int blockX = Math.round(grid.getLenghtRow()*0.5f);
    static int blockY = 0;
    
    //Variable Timer
    JTextArea textArea = new JTextArea("Vous pouvez modifier ce texte.");
    
    
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
        
        // Définir la couleur du texte
        g.setColor(Color.WHITE);
        
        g.setFont(new Font("Arial", Font.BOLD, 48)); // Police Arial, taille 48, style gras
    
        g.drawString(GetTimer(), 50, 50);
        
        g.draw3DRect(100, 300, 30, 30, true);
        
        

        for (int i = 0; i < grid.getLenghtColumn(); i++) {
            for (int j = 0; j < grid.getLenghtRow(); j++) {
                if (grid.grid[i][j] != null){
                    if (grid.isValidSlot(i, j)){ // Si C'est un block
                        int x = Math.round(cellWidth * j);
                        int y = Math.round(cellHeight * i);
                        g.drawImage(blockImage, x+gridOffsetX, y+1, cellSize, cellSize, this);
                        System.out.println("pat");

                    }
                    else { // SI c'est vide !
                        int x = Math.round(cellWidth * j);
                        int y = Math.round(cellHeight * i);
                        BufferedImage image = grid.getImage(i, j);
                        g.drawImage(image, x+gridOffsetX, y+1, cellSize, cellSize, this);
                        
                }
                }
            }
            
        }    
        
        
        
    }
    
   
    
    public PanelGame() {
        grid = new Grid();
        
        //Timer: Définir la chaine de caractere écrivant l'heure 
        JTextArea textArea = new JTextArea(GetTimer());
        
         swingTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondes++;
                System.out.println("Temps écoulé : " + secondes + " secondes");
                timer.removeTime();
                repaint(); // Rafraîchir l'affichage
            }
        });
        swingTimer.start(); // Démarrer le timer
        
        
        String directoryProject = System.getProperty("user.dir");
        
        try {
            background = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\ImageFolder\\background.png")));
            blockImage = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\ImageFolder\\spriteBlock.png")));
            
        } catch (IOException e) {
            // Log the error or handle it appropriately
            System.err.println("Error loading background: " + e.getMessage());
            // Provide default images or set to null
            
        }
        
        
        
    }
    
    
    
static void initBlock(){
    
    System.out.println(blockX + blockY);
    grid.grid[blockX][blockY].SetUse(true);
    
}
 
  // Pour arrêter le timer
    public void stopTimer() {
        if (swingTimer != null && swingTimer.isRunning()) {
            swingTimer.stop();
        }
    }
    
    // Pour redémarrer le timer
    public void restartTimer() {
        secondes = 0;
        if (swingTimer != null) {
            swingTimer.restart();
        }
    }
   
    private String GetTimer(){
        String bMinutes = Integer.toString(timer.minutes);
        String bSecondes = Integer.toString(timer.secondes);
        String zero = "0";
        if (timer.minutes<10){
            bMinutes = zero.concat(bMinutes);
        }
        if (timer.secondes<10){
            bSecondes = zero.concat(bSecondes);
        }
        return  (bMinutes+" : "+bSecondes);
    }
    
}
