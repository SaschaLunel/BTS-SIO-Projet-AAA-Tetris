/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import Panel.PanelGameAI;
import Panel.PanelGame;
import Panel.PanelMenu;
import Panel.PanelMenuLogin;
import Panel.PanelSignUp;
import Panel.PanelTransition;
import java.awt.GraphicsConfiguration;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author SIO
 */
public class CMyFrame extends JFrame {
    
    Mavenproject1 mainInstance;
    private JPanel currentPanel;
    
    static int sizeWindowsWidth = 1080;
    static int sizeWindowsHeight = 720;
    static String nameTitle;

    public CMyFrame(String title, Mavenproject1 mainInstance) {
        super(title);
        this.nameTitle = title;
        this.mainInstance = mainInstance;
        initFrameGame();
    }
    
    void initFrameGame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Configurer la fenêtre avec une taille par défaut
        this.setSize(sizeWindowsWidth, sizeWindowsHeight);
        
        //Instance du panel de menu principal
        currentPanel = new PanelMenu(mainInstance, this);
        this.add(currentPanel);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    
    public void addNewPanelMenu() {
        currentPanel = new PanelMenu(mainInstance, this);
        getContentPane().removeAll();
        add(currentPanel);
        revalidate();
        repaint();
        
        // Très important : donner le focus au nouveau panel
        currentPanel.setFocusable(true);
        currentPanel.requestFocusInWindow();
    }
    
    public void addNewPanelAI() {
        currentPanel = new PanelGameAI();
        getContentPane().removeAll();
        add(currentPanel);
        revalidate();
        repaint();
        
        // Très important : donner le focus au nouveau panel
        currentPanel.setFocusable(true);
        currentPanel.requestFocusInWindow();
    }
    
        public void addNewPanelGame() {
        currentPanel = new PanelGame();
        getContentPane().removeAll();
        add(currentPanel);
        revalidate();
        repaint();
        
        // Très important : donner le focus au nouveau panel
        currentPanel.setFocusable(true);
        currentPanel.requestFocusInWindow();
    }

    public void addNewPanelLogin(){
        currentPanel = new PanelMenuLogin(mainInstance,  this);
        getContentPane().removeAll();
        add(currentPanel);
        revalidate();
        repaint();
        
        // Très important : donner le focus au nouveau panel
        currentPanel.setFocusable(true);
        currentPanel.requestFocusInWindow();
    }
    
    public void addNewPanelTransition(){
        currentPanel = new PanelTransition(mainInstance, this);
        getContentPane().removeAll();
        add(currentPanel);
        revalidate();
        repaint();
        
        // Très important : donner le focus au nouveau panel
        currentPanel.setFocusable(true);
        currentPanel.requestFocusInWindow();
    }
    
    public void addNewPanelSignUp(){
        currentPanel = new PanelSignUp(this);
        getContentPane().removeAll();
        add(currentPanel);
        revalidate();
        repaint();
        
        // Très important : donner le focus au nouveau panel
        currentPanel.setFocusable(true);
        currentPanel.requestFocusInWindow();
    }
    
    public boolean createNewAccount(String email, String pseudo, String password){
        
        return false;
        
    }
}
