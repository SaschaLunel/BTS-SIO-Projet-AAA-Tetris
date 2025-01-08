/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

/**
 *
 * @author SIO
 */

import javax.swing.JPanel;
import javax.swing.JFrame;
import btn.ButtonMenu;
public class Panel {
    // Création d'un JPanel
    private JPanel panel;

    public Panel() {
        // Initialisation du JPanel
        panel = new JPanel();
        
        
        
        // Création du bouton de connexion
        ButtonMenu buttonInstance = new ButtonMenu();
        
        
        
        
        // Ajout du bouton au JPanel
        panel.add(buttonInstance.getButtonMenu());
        panel.add(buttonInstance.getButtonMenu());
        
       
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        // Création de la fenêtre
        JFrame frame = new JFrame("Exemple de Panel");
        Panel myPanel = new Panel();
        
        // Ajout du JPanel à la fenêtre
        frame.add(myPanel.getPanel());
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}

