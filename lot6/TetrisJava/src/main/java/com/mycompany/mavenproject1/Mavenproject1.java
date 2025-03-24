/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.mavenproject1;

import javax.swing.JFrame;
import BDD.baseDeDonnees;
import Menu.PanelMenu;
import Menu.PanelTransition;
import events.InterfaceMain;
import javax.swing.JPanel;

/**
 *
 * @author SIO
 */
public class Mavenproject1 implements InterfaceMain {
    private static int sizeWidth;
    private static int sizeHeight;
    private baseDeDonnees bdd;
    private static PanelGame myPanel;
    private static JFrame frame;

    public Mavenproject1() {
        // Constructeur vide
    }

    public static void main(String[] args) {
        // Créer UNE SEULE instance de Mavenproject1
        Mavenproject1 mainInstance = new Mavenproject1();
        
        // Création de la fenêtre principale
        frame = new JFrame("TETRISCRAFT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Créer le panel de transition avec notre instance unique
        PanelTransition panel = new PanelTransition(mainInstance);
        
        //Instance du panel de menu principal
        PanelMenu panelMenu = new PanelMenu(frame);
        
        // Configurer la fenêtre avec une taille par défaut
        frame.setSize(800, 600);
        frame.add(panelMenu);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        
        // S'assurer que le panel a le focus pour les touches
        panel.setFocusable(true);
        panel.requestFocusInWindow();
    }

    @Override
    public void SeconnecterBDD(String login, String mdp) {
        bdd = new baseDeDonnees(login, mdp);
    }

    @Override
    public void addNewPanelGame() {
        myPanel = new PanelGame();
        frame.getContentPane().removeAll();
        frame.add(myPanel);
        frame.revalidate();
        frame.repaint();
        
        // Très important : donner le focus au nouveau panel
        myPanel.setFocusable(true);
        myPanel.requestFocusInWindow();
    }

    public static int getSizeWidth() {
        return sizeWidth;
    }

    public static int getSizeHeight() {
        return sizeHeight;
    }
    
}
