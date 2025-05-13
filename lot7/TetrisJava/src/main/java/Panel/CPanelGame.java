/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Panel;

import Tetrominos.AbstractBlock;
import Components.Gameplay.CGameGrid;
import Components.Gameplay.ScoreWidget;
import Components.Gameplay.TimerWidget;
import Controllers.PlayerController;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

import events.EventListener;    //event dispatcher
import events.EventDispatcher;   //event dispatcher
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 *
 * @author SIO
 */
public class CPanelGame extends JPanel implements EventListener, IPanel {
    
    private JFrame frame;

    //Chemin absolu du projet 
    private String directoryProject = System.getProperty("user.dir");

    //Variables Globales des Tailles des grilles 
    private static int row;
    private static int column;

    //Taille Ecran voulu 
    private static int sizeHeight;
    private static int sizeWidth;

    //Instances des grille de jeux 
    protected static CGameGrid gridGameInstance;
    protected static int[][] gridGame;

    //Gestion du temps
    private int secondes = 0;
    private Timer swingTimer;
    private TimerWidget timer = new TimerWidget();
    
    //Gestion du score 
     ScoreWidget scoreWB = new ScoreWidget();

    //image 
    private static BufferedImage img_background;
    private static BufferedImage img_slot;
    private static BufferedImage img_block;
    private static BufferedImage img_block7;

    //Event Dispacher
    private EventDispatcher dispatcher;  // Déclaration de l'instance d'EventDispatcher
    //Player COntroller
    PlayerController pcGame;

    //COnstructeur
    public CPanelGame(JFrame frame) {

        this.frame = frame;
        
        row = 15;
        column = 15;
        sizeHeight = 720;
        sizeWidth = 1080;
        
        gridGameInstance = new CGameGrid(this, row, column);
        gridGameInstance.CreateGrid();
        gridGame = gridGameInstance.getGridGame();

        //event DIspatcher
        dispatcher = new EventDispatcher();
        dispatcher.addListener(this); // S'inscrire en tant qu'écouteur d'événements

        pcGame = new PlayerController(dispatcher);

        this.addKeyListener(pcGame);
        this.setFocusable(true);
        this.requestFocusInWindow();

        //Timer: Définir la chaine de caractere écrivant l'heure 
        JTextArea textArea = new JTextArea(GetTimer());

        swingTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gridGameInstance.getIsGameOver()){
                    swingTimer.stop();
                }
                secondes++;
                try {
                    gridGameInstance.dropBlock();
                } catch (IOException ex) {
                    Logger.getLogger(CPanelGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                timer.removeTime();
                repaint(); // Rafraîchir l'affichage
            }
        });
        swingTimer.start(); // Démarrer le timer

        try {
            img_background = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\Ressources\\background\\background.png")));
            img_slot = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\Ressources\\img_slot.png")));
            img_block = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\Ressources\\sprite_block.png")));
            img_block7 = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\Ressources\\sprite_block_7.png")));

        } catch (IOException e) {
            // Log the error or handle it appropriately
            System.err.println("Error loading background: " + e.getMessage());

        }
    }
    
    
    ////////////////////////////FONCTION REPAINT //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Redéfinit la méthode {@code paintComponent} pour dessiner dynamiquement le fond,
 * le score, le timer, la grille de jeu vide, ainsi que les blocs du tétrominos en cours.
 *
 * @param g l'objet {@code Graphics} utilisé pour effectuer les opérations de dessin
 */
@Override
protected void paintComponent(Graphics g) {
    System.out.println("Sa peint !!");

    // Récupération de l'état actuel de la grille de jeu
    gridGame = gridGameInstance.getGridGame();

    // Appel à la méthode de la superclasse pour un comportement de base
    super.paintComponent(g);

    // Ratio pour calculer la taille des cases en fonction de la hauteur de la fenêtre
    float ratiocell = 0.035f;

    // Récupération des dimensions de la fenêtre
    sizeWidth = getWidth();
    sizeHeight = getHeight();

    float cellWidth = sizeHeight * ratiocell; // Largeur d'une case
    float cellHeight = sizeHeight * ratiocell; // Hauteur d'une case

    int cellSize = Math.round(sizeHeight * ratiocell); // Taille arrondie de la case
    int gridOffsetX = Math.round(sizeWidth * 0.5f - (row * 0.5f * cellWidth)); // Décalage horizontal pour centrer la grille

    // Ajout de l'image de fond
    g.drawImage(img_background, 0, 0, sizeWidth, sizeHeight, this);

    // Définir la couleur du texte
    g.setColor(Color.WHITE);

    // Définir la police d'écriture
    g.setFont(new Font("Arial", Font.BOLD, 40));

    // Dessiner le temps restant
    g.drawString(GetTimer(), 50, 50);

    // Dessiner le score
    g.drawString(scoreWB.getTextScore(), sizeWidth - 300, 50);

    // Dessiner la grille vide avec les emplacements
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            int x = Math.round(cellWidth * j);
            int y = Math.round(cellHeight * i);
            g.drawImage(img_slot, x + gridOffsetX, y + 1, cellSize, cellSize, this);
        }
    }

    // Dessiner les blocs du tétrominos (valeurs 2 et 4 dans la grille)
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            int x = Math.round(cellWidth * j);
            int y = Math.round(cellHeight * i);

            if (gridGame[i][j] == 2) {
                g.drawImage(img_block, x + gridOffsetX, y + 1, cellSize, cellSize, this);
            }

            if (gridGame[i][j] == 4) {
                g.drawImage(img_block7, x + gridOffsetX, y + 1, cellSize, cellSize, this);
            }
        }
    }
}


   

    
     ////////////////////////////FONCTION TIMER //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // Pour arrêter le timer
    public void stopTimer() {
        if (swingTimer != null && swingTimer.isRunning()) {
            swingTimer.stop();
        }
    }

    // Pour redémarrer le timer
    public void restartTimer() {
        secondes = 0;
        if (swingTimer != null) {
            swingTimer.restart();
        }
    }

    /**
 * Renvoie une chaîne représentant le temps restant sous le format "MM : SS".
 * <p>
 * Ajoute un zéro devant les minutes ou secondes si elles sont inférieures à 10.
 *
 * @return une chaîne formatée représentant le minuteur actuel
 */
private String GetTimer() {
    // Convertir les minutes et secondes en chaînes
    String bMinutes = Integer.toString(timer.minutes);
    String bSecondes = Integer.toString(timer.secondes);
    String zero = "0";

    // Ajouter un zéro devant les minutes si nécessaire
    if (timer.minutes < 10) {
        bMinutes = zero.concat(bMinutes);
    }

    // Ajouter un zéro devant les secondes si nécessaire
    if (timer.secondes < 10) {
        bSecondes = zero.concat(bSecondes);
    }

    // Retourner la chaîne au format "MM : SS"
    return (bMinutes + " : " + bSecondes);
}

    
     ////////////////////////////FONCTION TRIGGER INPUT //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

   /**
 * Implémentation de la méthode {@code onEvent} de l'interface {@code EventListener}.
 * Gère les événements clavier lorsque la partie n'est pas terminée.
 * <p>
 * Selon la touche pressée (Gauche, Droite, Tourner, Bas), effectue l'action correspondante
 * sur la pièce de jeu, puis redessine la grille.
 *
 * @param eventName le nom de l'événement déclenché (ex. : "KEY_PRESS")
 * @param data données associées à l'événement (ici, la touche pressée en tant que {@code String})
 */
@Override
public void onEvent(String eventName, Object data) {
    // Vérifie si l'événement est une pression de touche et que la partie n'est pas terminée
    if ("KEY_PRESS".equals(eventName) && !gridGameInstance.getIsGameOver()) {

        // Récupérer la touche pressée depuis les données de l'événement
        String key = (String) data;

        // Gérer l'action en fonction de la touche
        switch (key) {
            case "Gauche" -> {
                // Déplacement vers la gauche
                gridGameInstance.movePiece(-1);
                repaint(); // Redessine la grille
            }
            case "Droite" -> {
                // Déplacement vers la droite
                gridGameInstance.movePiece(1);
                repaint(); // Redessine la grille
            }
            case "Tourner" -> {
                // Rotation de la pièce
                gridGameInstance.rotationGrid();
                repaint(); // Redessine la grille
            }
            case "Bas" -> {
                // Descente douce
                gridGameInstance.softDropGrid();
                repaint(); // Redessine la grille
            }
            default -> {
                // Aucun comportement pour les autres touches
            }
        }
    }
}

    // Méthode pour déclencher un événement de test
    public void triggerEvent() {
        dispatcher.dispatchEvent("SOME_EVENT", "Données d'exemple");
    }

     ////////////////////////////FONCTION GETTER ET SETTER //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 /**
 * Retourne le nombre de lignes de la grille de jeu.
 *
 * @return le nombre de lignes (row)
 */
public static int getRow() {
    return row;
}

/**
 * Retourne le nombre de colonnes de la grille de jeu.
 *
 * @return le nombre de colonnes (column)
 */
public static int getColumn() {
    return column;
}

/**
 * Retourne la hauteur actuelle de la fenêtre (panel).
 *
 * @return la hauteur en pixels
 */
public int getSizeHeight() {
    return sizeHeight;
}

/**
 * Retourne la largeur actuelle de la fenêtre (panel).
 *
 * @return la largeur en pixels
 */
public int getSizeWidth() {
    return sizeWidth;
}

/**
 * Retourne l'objet ScoreWidget associé à ce panel.
 *
 * @return le widget de score
 */
public ScoreWidget getScoreWB() {
    return scoreWB;
}

    
    

    @Override
    public void endGame() {
        stopTimer();
        int choice = JOptionPane.showConfirmDialog(frame, "GameOver ! Rejouer ?", "Fin de jeu", JOptionPane.YES_NO_OPTION);

if (choice == JOptionPane.YES_OPTION) {
    gridGameInstance = new CGameGrid(this, row, column); // Ta fonction pour redémarrer
    gridGameInstance.CreateGrid();
    swingTimer.restart();
    scoreWB.setCurrentScore(0);
    restartTimer();
} else {
    
}
        
        
    }

    public void IANewPiece(AbstractBlock currentPiece) {
    }

}
