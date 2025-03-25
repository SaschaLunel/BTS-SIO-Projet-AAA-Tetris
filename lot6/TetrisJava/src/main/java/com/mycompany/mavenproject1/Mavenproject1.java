/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.mavenproject1;

import javax.swing.JFrame;
import BDD.baseDeDonnees;
import Menu.PanelMenu;
import Menu.PanelMenuLogin;
import Menu.PanelTransition;
import events.InterfaceMain;
import javax.swing.JPanel;

/**
 *
 * @author SIO
 */
public class Mavenproject1 implements InterfaceMain {
    private static int sizeWidth = 1080;
    private static int sizeHeight = 720;
    private baseDeDonnees bdd;
    private static PanelGame myPanel;
   

    public Mavenproject1() {
        // Constructeur vide
    }

    public static void main(String[] args) {
        // Créer UNE SEULE instance de Mavenproject1
        Mavenproject1 mainInstance = new Mavenproject1();
        
        // Création de la fenêtre principale
        JFrame frame = new JFrame("TETRISCRAFT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
//        // Créer le panel de transition avec notre instance unique
//        PanelTransition panel = new PanelTransition(mainInstance);
        
        //Instance du panel de menu principal
        PanelMenu panelMenu = new PanelMenu(frame);
        
        // Configurer la fenêtre avec une taille par défaut
        frame.setSize(sizeWidth, sizeHeight);
        frame.add(panelMenu);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        
        // S'assurer que le panel a le focus pour les touches
        panelMenu.setFocusable(true);
        panelMenu.requestFocusInWindow();
    }

    @Override
    public void SeconnecterBDD(String login, String mdp) {
        bdd = new baseDeDonnees(login, mdp);
    }

    
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

    public void addNewPanelLogin(){
        PanelMenuLogin myPanelLogin = new PanelMenuLogin(frame);
        frame.getContentPane().removeAll();
        frame.add(myPanelLogin);
        frame.revalidate();
        frame.repaint();
        
        // Très important : donner le focus au nouveau panel
        myPanel.setFocusable(true);
        myPanel.requestFocusInWindow();
    }
    
    public void addNewPanelTransition(){
        PanelTransition myPanelTrans = new PanelTransition(frame);
        frame.getContentPane().removeAll();
        frame.add(myPanelTrans);
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
