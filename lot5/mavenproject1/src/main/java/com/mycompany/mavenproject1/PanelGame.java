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

import events.EventListener;    //event dispatcher
import events.EventDispatcher;   //event dispatcher

/**
 *
 * @author SIO
 */
public class PanelGame extends JPanel implements EventListener {
    static Grid grid = new Grid();
    
    private int secondes = 0;
    private Timer swingTimer;
    private TimerWidget timer = new TimerWidget();
    
    public int sizeHeight = 720;
    public int sizeWidth = 1080;
    
    
    public static BufferedImage background;
    static BufferedImage blockImage;
    //Event Dispacher
    private EventDispatcher dispatcher;  // Déclaration de l'instance d'EventDispatcher
    //Player COntroller
    PlayerController pcGame;
    
    
    static Block block = new Block();
    static int blockX = 0;
    static int blockY = Math.round(grid.getLenghtRow()*0.5f)-1;
    
    //Variable Timer
    JTextArea textArea = new JTextArea("Vous pouvez modifier ce texte.");
    
    
    @Override
    protected void paintComponent(Graphics g) {
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
                    if (grid.grid[i][j].block != null){ // Si C'est un block
                        int x = Math.round(cellWidth * j);
                        int y = Math.round(cellHeight * i);
                        g.drawImage(blockImage, x+gridOffsetX, y+1, cellSize, cellSize, this);
                        

                    }
                    else { // SI c'est vide !
                        System.out.println("pat");
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
        
        //event DIspatcher
        dispatcher = new EventDispatcher();
        dispatcher.addListener(this); // S'inscrire en tant qu'écouteur d'événements

        pcGame = new PlayerController(dispatcher);
        
        this.addKeyListener(pcGame);
        this.setFocusable(true);
        this.requestFocusInWindow();
        
        //Timer: Définir la chaine de caractere écrivant l'heure 
        JTextArea textArea = new JTextArea(GetTimer());
        
         swingTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondes++;
                dropBlock();
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
    
static void dropBlock(){
    int x = blockX;
    int y = blockY;
        
    Block block = grid.grid[x][y].block;
    grid.grid[x][y].block = null;
    x = x+1;
    grid.grid[x][y].block = block;
    blockX = x;
}
    
    
static void initBlock(){
    
    System.out.println(grid.grid[blockX][blockY]+"toto");
    grid.grid[blockX][blockY].block= new Block();
    
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
    
    
   private void moveBlock(int direction){
       int futurY = blockY + direction; 
       if (grid.grid[blockX][futurY] != null){
            Block bloc = grid.grid[blockX][blockY].block;
            grid.grid[blockX][blockY].block= null;
            blockY = blockY + direction;
            grid.grid[blockX][blockY].block= bloc;
            repaint();
       }
   }
    
   // Implémentation de la méthode onEvent de l'interface EventListener
    @Override
    public void onEvent(String eventName, Object data) {
        if ("KEY_PRESS".equals(eventName)) {
            // Vérifiez quel événement a été déclenché et affichez la touche pressée
            String key = (String) data;
            if (key == "Gauche"){moveBlock(-1);}
            else if (key == "Droite"){moveBlock(1);}
        }
    }

    
     // Méthode pour déclencher un événement de test
    public void triggerEvent() {
        dispatcher.dispatchEvent("SOME_EVENT", "Données d'exemple");
    }
}


