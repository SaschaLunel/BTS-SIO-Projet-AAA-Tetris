/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;

import javax.swing.JFrame;

/**
 *
 * @author SIO
 */
public class Mavenproject1 {

    public static void main(String[] args) {
        // Création de la fenêtre principale
        JFrame frame = new JFrame("TETRISCRAFT");

        // Assurer que l'application se termine lorsque la fenêtre est fermée
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Créer une instance de ta classe Panel
        Panel myPanel = new Panel();
        
        
        // Ajouter le JPanel à la fenêtre
        frame.add(myPanel.getPanel());
        
        // Définir la taille de la fenêtre
        frame.setSize(1080, 720);
        
        // Rendre la fenêtre visible
        frame.setVisible(true);
        
        // Optionnel : centrer la fenêtre à l'écran
        frame.setLocationRelativeTo(null);
    }
}
