/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;


import javax.swing.JFrame;

/**
 *
 * @author SIO
 */
public class Mavenproject1  {

    int sizeWidth;
    int sizeHeight;
    PanelGame myPanel;
    public Mavenproject1() {
        
        // Créer une instance de ta classe Panel
        myPanel = new PanelGame();
        int sizeWidth = myPanel.getWidth();
        int sizeHeight = myPanel.getHeight();
        
    }

    
    
    public static void main(String[] args) {
        // Création de la fenêtre principale
        JFrame frame = new JFrame("TETRISCRAFT");

        // Assurer que l'application se termine lorsque la fenêtre est fermée
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
        
        
        // Ajouter le JPanel à la fenêtre
        frame.add(myPanel);
        
        // Définir la taille de la fenêtre
        frame.setSize(sizeWidth, sizeHeight);
        
        // Rendre la fenêtre visible
        frame.setVisible(true);
        
        // Optionnel : centrer la fenêtre à l'écran
        frame.setLocationRelativeTo(null);
    }
}
