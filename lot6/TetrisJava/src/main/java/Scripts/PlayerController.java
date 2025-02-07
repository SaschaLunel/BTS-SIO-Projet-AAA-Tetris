/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Scripts;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import events.EventDispatcher; // AJouter le packtage EventDIspacher


/**
 *
 * @author SIO
 */
public class PlayerController implements KeyListener {
    
    private EventDispatcher dispatcher;  // Déclaration de l'EventDispatcher

    public PlayerController(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;  // Initialisation du dispatcher
    }
    
    
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                // Code pour la touche "Bas"
                dispatcher.dispatchEvent("KEY_PRESS", "Bas");
                break;
            case KeyEvent.VK_UP:
                dispatcher.dispatchEvent("KEY_PRESS", "Tourner");
                
                break;
            case KeyEvent.VK_LEFT:
                // Code pour la touche "Gauche"
                dispatcher.dispatchEvent("KEY_PRESS", "Gauche");
                break;
            case KeyEvent.VK_RIGHT:
                // Code pour la touche "Droite"
                dispatcher.dispatchEvent("KEY_PRESS", "Droite");
                break;
            case KeyEvent.VK_SPACE:
                break;
            case KeyEvent.VK_CONTROL:
                dispatcher.dispatchEvent("KEY_PRESS", "Tourner");
                break;
                
            default:
                // Code par défaut si aucune des touches n'est détectée
                
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
