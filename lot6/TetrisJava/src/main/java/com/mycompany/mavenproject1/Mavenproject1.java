/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;


import javax.swing.JFrame;
import BDD.baseDeDonnees;
import events.InterfaceMain;

/**
 *
 * @author SIO
 */
public class Mavenproject1 implements InterfaceMain  {

    private static int sizeWidth;
    private static int sizeHeight;
    private static baseDeDonnees bdd;
    
    public Mavenproject1() {
        
    }

    
    
    public static void main(String[] args) {
        // Création de la fenêtre principale
        JFrame frame = new JFrame("TETRISCRAFT");

        // Assurer que l'application se termine lorsque la fenêtre est fermée
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Créer une instance de ta classe Panel
        PanelGame myPanel = new PanelGame();
        
        int sizeWidth = myPanel.getSizeWidth();
        int sizeHeight = myPanel.getSizeHeight();
        
        
        
        // Ajouter le JPanel à la fenêtre
        frame.add(myPanel);
        
        // Définir la taille de la fenêtre
        frame.setSize(sizeWidth, sizeHeight);
        
        // Rendre la fenêtre visible
        frame.setVisible(true);
        
        // Optionnel : centrer la fenêtre à l'écran
        frame.setLocationRelativeTo(null);
    }
    
    /**
     *
     * @param login
     * @param mdp
     */
    @Override
    public void SeconnecterBDD(String login, String mdp){
        bdd = new baseDeDonnees(login, mdp);
    }
    
}
