/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import BDD.CPlayer;
import BDD.CRequeteSql;
import BDD.baseDeDonnees;
import Components.ButtonMenu;
import Panel.PanelGameOpenAI;
import Panel.PanelGame;
import Panel.PanelGameAI;
import Panel.PanelMenu;
import Panel.PanelMenuLogin;
import Panel.PanelSignUp;
import Panel.PanelTransition;
import java.awt.GraphicsConfiguration;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author SIO
 */
public class CMyFrame extends JFrame {
    
    Mavenproject1 mainInstance;
    private JPanel currentPanel;
    private CPlayer player;
    
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
    
    public void addNewPanelAI() throws IOException, InterruptedException {
        
        currentPanel = new PanelGameAI(this);
        
        getContentPane().removeAll();
        add(currentPanel);
        revalidate();
        repaint();
        
        // Très important : donner le focus au nouveau panel
        currentPanel.setFocusable(true);
        currentPanel.requestFocusInWindow();
    }
    
        public void addNewPanelGame() {
        currentPanel = new PanelGame(this);
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
        currentPanel = new PanelTransition(this);
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
    
 public void createNewAccount(String email, String pseudo, String password, String prenom, String nom, String dBirth) throws SQLException, InterruptedException, ExecutionException {
    // Add the login panel
    


    try {
        // Launch asynchronous user creation and wait for it to finish
        CompletableFuture<Void> future = CRequeteSql.createUser(email, password, prenom, nom, pseudo, dBirth);
        future.get(); // blocks until the user is created
        System.out.println("Tâche terminée !");
    } catch (ExecutionException e) {
        throw new RuntimeException("Erreur lors de la création de l'utilisateur", e);
    }

    // Get user ID asynchronously and wait for the result
        CompletableFuture<Integer> futureId = CRequeteSql.getIdUser(pseudo);
        int id = futureId.get(); // wait for the user ID
    player = new CPlayer(prenom, nom, pseudo, email, id);
    
     addNewPanelLogin();
}

    public void initSession(String Pseudo){
        Map<String, String> infoPlayerList = CRequeteSql.getPlayerInfo(Pseudo);
        player = new CPlayer(infoPlayerList.get("prenom"), infoPlayerList.get("nom"), infoPlayerList.get("pseudo"), 
                infoPlayerList.get("email"), Integer.parseInt(infoPlayerList.get("iduser")));
        addNewPanelTransition();
    }


}
