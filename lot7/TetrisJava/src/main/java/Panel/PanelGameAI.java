/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Panel;

/**
 *
 * @author SIO
 */

import BlockFolder.AbstractBlock;
import Controllers.AIController;
import java.awt.Graphics;
import javax.swing.JFrame;

public class PanelGameAI extends PanelGame {
    
    private AIController aiController;  // Instance de l'IA

    // Constructeur qui appelle celui de la classe parent PanelGame
    public PanelGameAI(JFrame frame) {
        System.err.println("l'erreur vient du super");
        super(frame);  // Appel du constructeur de PanelGame
        // Initialisation de l'IA avec la grille du jeu
        this.aiController = new AIController(gridGameInstance);
        
    }

    // Implémente les comportements spécifiques pour PanelGameAI ici
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  // Appelle la méthode de PanelGame pour garder le comportement de base

        // Logique spécifique à PanelGameAI, comme les dessins particuliers
    }

    @Override
    public void IANewPiece(AbstractBlock currentPiece) {
        super.IANewPiece(currentPiece); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        System.err.println("IANEWPEICE"+this.aiController);
        aiController.setCurrentPiece(currentPiece);
    }

    
    
}

