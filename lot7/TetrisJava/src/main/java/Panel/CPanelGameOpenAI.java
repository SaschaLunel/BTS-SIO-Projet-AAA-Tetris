
package Panel;

import OpenAI.Config;
import OpenAI.OpenAIBot;
import Components.Gameplay.CGameGrid;
import Components.Gameplay.ScoreWidget;
import Components.Gameplay.TimerWidget;
import Interfaces.GameActions;

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

import events.EventListener;
import events.EventDispatcher;
import java.awt.Color;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Classe principale du panneau de jeu intégrant le bot OpenAI pour automatiser les mouvements.
 * Elle hérite de CPanelGame et implémente l'interface GameActions.
 */
public class CPanelGameOpenAI extends CPanelGame implements GameActions {

    // Chemin absolu vers le projet
    private String directoryProject = System.getProperty("user.dir");

    // Variables globales pour la taille de la grille
    private static int row;
    private static int column;

    // Taille souhaitée de l'écran
    private static int sizeHeight;
    private static int sizeWidth;

    // Instances de la grille de jeu
    protected static CGameGrid gridGameInstance;
    protected static int[][] gridGame;

    // Gestion du temps
    private int secondes = 0;
    private Timer swingTimer;
    private TimerWidget timer = new TimerWidget();
    
    // Gestion du score
    private ScoreWidget scoreWB = new ScoreWidget();

    // Ressources images
    private static BufferedImage img_background;
    private static BufferedImage img_slot;
    private static BufferedImage img_block;
    private static BufferedImage img_block7;

    // Dispatcher pour les événements
    private EventDispatcher dispatcher;

    // Contrôleur du joueur
    PlayerController pcGame;

    // Bot OpenAI pour générer des instructions de jeu
    private OpenAIBot bot;
    
    private int indexInstruction = 0;

    /**
     * Constructeur principal du panneau de jeu avec OpenAI.
     * Initialise les composants du jeu, le timer et charge les images.
     *
     * @param frame La fenêtre principale du jeu
     * @throws IOException En cas d'erreur lors du chargement des ressources
     * @throws InterruptedException En cas d'interruption pendant l'initialisation
     */
    public CPanelGameOpenAI(JFrame frame) throws IOException, InterruptedException {
        super(frame);

        String token = new Config().getTokenOpenAI();
        System.err.println(token);

        bot = new OpenAIBot(token);

        // Initialisation de la grille
        row = 15;
        column = 15;
        sizeHeight = 720;
        sizeWidth = 1080;

        gridGameInstance = new CGameGrid(this, row, column);
        System.err.println("coc" + gridGame);

        gridGame = gridGameInstance.CreateGrid();
        createBlockPlayer(); // Génère les instructions initiales via OpenAI

        System.err.println("coca" + gridGame);

        this.addKeyListener(pcGame);
        this.setFocusable(true);
        this.requestFocusInWindow();

        // Zone de texte pour le timer (non affichée ici mais utile pour l’extension future)
        JTextArea textArea = new JTextArea(GetTimer());

        // Timer principal du jeu (descente automatique des blocs)
        swingTimer = new Timer(700, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gridGameInstance.getIsGameOver()) {
                    swingTimer.stop(); // Arrêter si le jeu est terminé
                }

                secondes++;

                MoveBlockByIA(); // Mouvement automatique généré par le bot

                try {
                    gridGameInstance.dropBlock(); // Laisse tomber le bloc actif
                } catch (IOException ex) {
                    Logger.getLogger(CPanelGameOpenAI.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.err.println("ça descend !");
                timer.removeTime(); // Mise à jour du temps restant
                repaint(); // Rafraîchit l'affichage
            }
        });
        swingTimer.start(); // Démarrage du timer

        // Chargement des ressources graphiques
        try {
            img_background = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\Ressources\\background\\background.png")));
            img_slot = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\Ressources\\img_slot.png")));
            img_block = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\Ressources\\sprite_block.png")));
            img_block7 = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\Ressources\\sprite_block_7.png")));

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des images : " + e.getMessage());
        }
    }

    /**
     * Dessine tous les éléments du jeu sur le panneau.
     *
     * @param g Contexte graphique
     */
    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("Ça peint !");
        gridGame = gridGameInstance.getGridGame();
        super.paintComponent(g);

        float ratiocell = 0.035f;
        sizeWidth = getWidth();
        sizeHeight = getHeight();

        float cellWidth = sizeHeight * ratiocell;
        float cellHeight = sizeHeight * ratiocell;
        int cellSize = Math.round(sizeHeight * ratiocell);
        int gridOffsetX = Math.round(sizeWidth * 0.5f - (row * 0.5f * cellWidth));

        g.drawImage(img_background, 0, 0, sizeWidth, sizeHeight, this);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString(GetTimer(), 50, 50);
        g.drawString(scoreWB.getTextScore(), sizeWidth - 300, 50);

        // Dessin de la grille de fond
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                int x = Math.round(cellWidth * j);
                int y = Math.round(cellHeight * i);
                g.drawImage(img_slot, x + gridOffsetX, y + 1, cellSize, cellSize, this);
            }
        }

        // Dessin des blocs actifs et fixés
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

    /**
     * Arrête le timer de jeu.
     */
    public void stopTimer() {
        if (swingTimer != null && swingTimer.isRunning()) {
            swingTimer.stop();
        }
    }

    /**
     * Redémarre le timer et remet le compteur à zéro.
     */
    @Override
    public void restartTimer() {
        secondes = 0;
        if (swingTimer != null) {
            swingTimer.restart();
        }
    }

    /**
     * Retourne le temps restant au format "MM : SS".
     *
     * @return Une chaîne formatée
     */
    private String GetTimer() {
        String bMinutes = Integer.toString(timer.minutes);
        String bSecondes = Integer.toString(timer.secondes);
        String zero = "0";
        if (timer.minutes < 10) {
            bMinutes = zero.concat(bMinutes);
        }
        if (timer.secondes < 10) {
            bSecondes = zero.concat(bSecondes);
        }
        return (bMinutes + " : " + bSecondes);
    }

    /**
     * Gère les actions clavier simulées par le bot ou un utilisateur.
     *
     * @param eventName Le nom de l'action ("Gauche", "Droite", etc.)
     */
    public void onEventInput(String eventName) {
        if (!gridGameInstance.getIsGameOver()) {
            System.err.println(eventName);
            switch (eventName) {
                case "Gauche" -> {
                    gridGameInstance.movePiece(-1);
                    repaint();
                }
                case "Droite" -> {
                    gridGameInstance.movePiece(1);
                    repaint();
                }
                case "Tourner" -> {
                    gridGameInstance.rotationGrid();
                    repaint();
                }
                case "Bas" -> {
                    gridGameInstance.softDropGrid();
                    repaint();
                }
                default -> {
                }
            }
        }
    }

    

    /**
     * Fait appel au bot pour obtenir une instruction et la traiter.
     */
    private void MoveBlockByIA() {
        System.err.print(bot.getInstruction());
        if (bot.getInstruction() == null) return;

        onEventInput(bot.getInstruction());
        bot.removeInstruction();
    }

    ///////////// GETTERS / SETTERS /////////////

  /**
 * Retourne le nombre de lignes de la grille.
 * @return le nombre de lignes
 */
public static int getRow() {
    return row; // Retourne la valeur statique représentant le nombre de lignes
}

/**
 * Retourne le nombre de colonnes de la grille.
 * @return le nombre de colonnes
 */
public static int getColumn() {
    return column; // Retourne la valeur statique représentant le nombre de colonnes
}

/**
 * {@inheritDoc}
 * Retourne la hauteur d’un élément ou d’une grille.
 * @return la hauteur
 */
@Override
public int getSizeHeight() {
    return sizeHeight; // Retourne la hauteur définie
}

/**
 * {@inheritDoc}
 * Retourne la largeur d’un élément ou d’une grille.
 * @return la largeur
 */
@Override
public int getSizeWidth() {
    return sizeWidth; // Retourne la largeur définie
}

/**
 * Retourne le bot OpenAI associé.
 * @return une instance d'OpenAIBot
 */
public OpenAIBot getBot() {
    return bot; // Retourne l'instance du bot
}


   
    

    
    
}
