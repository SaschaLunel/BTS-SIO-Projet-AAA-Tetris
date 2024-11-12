/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Scripts;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 *
 * @author SIO
 */
public class PlayerController implements KeyListener {

    public PlayerController() {
    }
    
    
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                // Code pour la touche "Bas"
                System.out.println("Touche Bas pressée");
                break;
            case KeyEvent.VK_UP:
                // Code pour la touche "Haut"
                System.out.println("Touche Haut pressée");
                break;
            case KeyEvent.VK_LEFT:
                // Code pour la touche "Gauche"
                System.out.println("Touche Gauche pressée");
                break;
            case KeyEvent.VK_RIGHT:
                // Code pour la touche "Droite"
                System.out.println("Touche Droite pressée");
                break;
            case KeyEvent.VK_SPACE:
                break;
            case KeyEvent.VK_CONTROL:
                break;
            default:
                // Code par défaut si aucune des touches n'est détectée
                System.out.println("Autre touche pressée");
                break;
        }
    }
    

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    
    
}
