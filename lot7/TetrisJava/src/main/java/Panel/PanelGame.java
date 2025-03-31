/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Panel;

import Components.Gameplay.GridGame;
import Components.Gameplay.ScoreWidget;
import Components.Gameplay.TimerWidget;
import Scripts.PlayerController;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

import events.EventListener;    //event dispatcher
import events.EventDispatcher;   //event dispatcher
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author SIO
 */
public class PanelGame extends JPanel implements EventListener {

    //Chemin absolu du projet 
    private String directoryProject = System.getProperty("user.dir");

    //Variables Globales des Tailles des grilles 
    private static int row;
    private static int column;

    //Taille Ecran voulu 
    private static int sizeHeight;
    private static int sizeWidth;

    //Instances des grille de jeux 
    protected static GridGame gridGameInstance;
    protected static int[][] gridGame;

    //Gestion du temps
    private int secondes = 0;
    private Timer swingTimer;
    private TimerWidget timer = new TimerWidget();
    
    //Gestion du score 
    private ScoreWidget scoreWB = new ScoreWidget();

    //image 
    private static BufferedImage img_background;
    private static BufferedImage img_slot;
    private static BufferedImage img_block;
    private static BufferedImage img_block7;

    //Event Dispacher
    private EventDispatcher dispatcher;  // Déclaration de l'instance d'EventDispatcher
    //Player COntroller
    PlayerController pcGame;

    //COnstructeur
    public PanelGame() {

        row = 15;
        column = 15;
        sizeHeight = 720;
        sizeWidth = 1080;
        
        gridGameInstance = new GridGame(row, column);
        gridGameInstance.CreateGrid();
        gridGame = gridGameInstance.getGridGame();

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
                if (gridGameInstance.getIsGameOver()){
                    swingTimer.stop();
                }
                secondes++;
                try {
                    gridGameInstance.dropBlock();
                } catch (IOException ex) {
                    Logger.getLogger(PanelGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                timer.removeTime();
                repaint(); // Rafraîchir l'affichage
            }
        });
        swingTimer.start(); // Démarrer le timer

        try {
            img_background = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\Ressources\\background\\background.png")));
            img_slot = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\Ressources\\img_slot.png")));
            img_block = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\Ressources\\sprite_block.png")));
            img_block7 = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\Ressources\\sprite_block_7.png")));

        } catch (IOException e) {
            // Log the error or handle it appropriately
            System.err.println("Error loading background: " + e.getMessage());

        }
    }
    
    
    ////////////////////////////FONCTION REPAINT //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("Sa peint !!");
        gridGame = gridGameInstance.getGridGame();
        super.paintComponent(g);

        float ratiocell = 0.035f;
        sizeWidth = getWidth();
        sizeHeight = getHeight();

        float cellWidth = sizeHeight * ratiocell; // Largeur d'une case
        float cellHeight = sizeHeight * ratiocell;// Hauteur d'une case

        int cellSize = Math.round(sizeHeight * ratiocell);
        int gridOffsetX = Math.round(sizeWidth * 0.5f - (row * 0.5f * cellWidth));

        //Ajout du fond 
        g.drawImage(img_background, 0, 0, sizeWidth, sizeHeight, this);

        // Définir la couleur du texte
        g.setColor(Color.WHITE);

        //Définir la police 
        g.setFont(new Font("Arial", Font.BOLD, 40)); // Police Arial, taille 48, style gras

        //Dessiner le temps restant
        g.drawString(GetTimer(), 50, 50);
        
        //dessiner le Score
        g.drawString(scoreWB.getTextScore(), sizeWidth - 300, 50);

        //Dessiner la grille  vide
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                int x = Math.round(cellWidth * j);
                int y = Math.round(cellHeight * i);
                g.drawImage(img_slot, x + gridOffsetX, y + 1, cellSize, cellSize, this);

            }
        }

        //peindre la grille du tetrominos
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                int x = Math.round(cellWidth * j);
                int y = Math.round(cellHeight * i);
                if (gridGame[i][j] == 2) {
                    g.drawImage(img_block, x + gridOffsetX, y + 1, cellSize, cellSize, this);

                }
                if (gridGame[i][j] == 4) {
                    g.drawImage(img_block7, x + gridOffsetX, y + 1, cellSize, cellSize, this);

                }

            }
        }
    }

   

    
     ////////////////////////////FONCTION TIMER //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
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

    //Renvoie une chaine de caractere de la forme "MM : SS"
    private String GetTimer() {
        String bMinutes = Integer.toString(timer.minutes);
        String bSecondes = Integer.toString(timer.secondes);
        String zero = "0";
        if (timer.minutes < 10) {
            bMinutes = zero.concat(bMinutes);
        }
        if (timer.secondes < 10) {
            bSecondes = zero.concat(bSecondes);
        }
        return (bMinutes + " : " + bSecondes);
    }
    
     ////////////////////////////FONCTION TRIGGER INPUT //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Implémentation de la méthode onEvent de l'interface EventListener
    @Override
    public void onEvent(String eventName, Object data) {
        if ("KEY_PRESS".equals(eventName) && ! gridGameInstance.getIsGameOver()) {
            // Vérifiez quel événement a été déclenché et affichez la touche pressée
            String key = (String) data;
            switch (key) {
                case "Gauche" -> {
                    
                    gridGameInstance.movePiece(-1);
                    
                    repaint();
                }
                case "Droite" -> {
                    gridGameInstance.movePiece(1);
                    
                    repaint();
                    
                }
                case "Tourner" ->{
                    gridGameInstance.rotationGrid();
                    repaint();}
                
                case "Bas" -> {
                    gridGameInstance.softDropGrid();
                    repaint();
                }
                default -> {
                }
            }

        }
    }

    // Méthode pour déclencher un événement de test
    public void triggerEvent() {
        dispatcher.dispatchEvent("SOME_EVENT", "Données d'exemple");
    }

     ////////////////////////////FONCTION GETTER ET SETTER //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static int getRow() {
        return row;
    }

    public static int getColumn() {
        return column;
    }

    public int getSizeHeight() {
        return sizeHeight;
    }

    public int getSizeWidth() {
        return sizeWidth;
    }

}
