/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import Gameplay.Grid;                 //Import du pactage Gameplay
import Gameplay.GridTetrominos;
import Gameplay.TimerWidget;
import Scripts.PlayerController;

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
import java.util.Set;

/**
 *
 * @author SIO
 */
public class PanelGame extends JPanel implements EventListener {

    //Chemin absolu du projet 
    String directoryProject = System.getProperty("user.dir");

    //Variables Globales des Tailles des grilles 
    public static int row = 25;
    public static int column = 15;

    //Taille Ecran
    public int sizeHeight = 720;
    public int sizeWidth = 1080;

    //Instances des grille de jeux 
    private Grid grid;
    private GridTetrominos grid_block;
    private int[][] grid_pattern;

    //Gestion du temps
    private int secondes = 0;
    private Timer swingTimer;
    private TimerWidget timer = new TimerWidget();

    private static BufferedImage img_background;
    private static BufferedImage img_slot;
    private static BufferedImage img_block;

    //Event Dispacher
    private EventDispatcher dispatcher;  // Déclaration de l'instance d'EventDispatcher
    //Player COntroller
    PlayerController pcGame;

    //COnstructeur
    public PanelGame() {
        grid = new Grid();
        grid_block = new GridTetrominos(row, column);
        grid_pattern = grid_block.getGrid();

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

        try {
            img_background = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\Ressources\\background.png")));
            img_slot = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\Ressources\\img_slot.png")));
            img_block = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\Ressources\\sprite_block.png")));

        } catch (IOException e) {
            // Log the error or handle it appropriately
            System.err.println("Error loading background: " + e.getMessage());

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
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
        g.setFont(new Font("Arial", Font.BOLD, 48)); // Police Arial, taille 48, style gras

        //Dessiner le temps restant
        g.drawString(GetTimer(), 50, 50);

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
                if (grid_pattern[i][j] == 1) {
                    g.drawImage(img_block, x + gridOffsetX, y + 1, cellSize, cellSize, this);

                }

            }
        }
    }

    

    // Fonction pour faire descendre le Tetromino
    void dropBlock() {
        // Parcourir la grille de bas en haut pour éviter les collisions
        for (int i = grid_pattern.length - 2; i >= 0; i--) { // -2 car on commence à l'avant-dernier
            // Copier la ligne actuelle dans la ligne du dessous
            grid_pattern[i + 1] = grid_pattern[i];
        }
        // Vider la première ligne après le déplacement
        grid_pattern[0] = new int[column];

    }

    private void moveBlock(int direction) {
            
            int block_length = grid_block.getNewBlock().length();
        
            if (direction > 0 && grid_block.getStart_y()< grid_pattern.length-block_length) {

                // Parcourir la grille de bas en haut pour éviter les collisions
                for (int i = grid_pattern.length - 2; i >= 0; i--) { // -2 car on commence à l'avant-dernier
                    // Copier la ligne actuelle dans la ligne du dessous
                    for (int j = grid_pattern[i].length - 2; j >= 0; j--) {
                        grid_pattern[i][j + 1] = grid_pattern[i][j];
                    }
                    // Vider la première ligne après le déplacement
                    grid_pattern[i][0] = 0; // 0 représente une case vide // Suppose qu'une ligne vide est un tableau d'entiers initialisés à 0
                }

                 grid_block.setStart_y(grid_block.getStart_y()+1);
                repaint();

            } 

            else if (direction < 0 && grid_block.getStart_y()> 0) { // Déplacement à gauche
            // Parcourir la grille de haut en bas
            for (int i = 0; i < grid_pattern.length; i++) {
                for (int j = 1; j < grid_pattern[i].length; j++) { // Parcourir de gauche à droite
                    grid_pattern[i][j - 1] = grid_pattern[i][j]; // Déplacer vers la gauche
                }
                grid_pattern[i][grid_pattern[i].length - 1] = 0; // Effacer la dernière colonne
            }
                grid_block.setStart_y(grid_block.getStart_y()-1);
                repaint();
            }

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

    // Implémentation de la méthode onEvent de l'interface EventListener
    @Override
    public void onEvent(String eventName, Object data) {
        if ("KEY_PRESS".equals(eventName)) {
            // Vérifiez quel événement a été déclenché et affichez la touche pressée
            String key = (String) data;
            if (key == "Gauche") {
                moveBlock(-1);
            } else if (key == "Droite") {
                moveBlock(1);
            }

        }
    }

    // Méthode pour déclencher un événement de test
    public void triggerEvent() {
        dispatcher.dispatchEvent("SOME_EVENT", "Données d'exemple");
    }
}
