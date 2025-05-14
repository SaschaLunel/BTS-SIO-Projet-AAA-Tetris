package Panel;

import Components.ButtonMenu;
import com.mycompany.mavenproject1.MainFrame;
import com.mycompany.mavenproject1.Mavenproject1;
import events.EventListener;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class CPanelMenu extends JPanel implements EventListener {

    // Chemin absolu du projet
    final private String DIRECTORYPROJECT = System.getProperty("user.dir");
    private ButtonMenu btnLogin;
    private ButtonMenu btnGuest;
    private ButtonMenu btnAI;
    private ButtonMenu btnGpt;
    private BufferedImage background;
    private JFrame frame;
    private int frameWidth;

    public CPanelMenu(Mavenproject1 mainInstance, MainFrame frame) {

        this.frame = frame;
        // Largeur de la frame
        frameWidth = Mavenproject1.getSizeWidth();

        this.setLayout(new GridBagLayout()); // Utilisation du GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Colonne 0
        gbc.gridy = GridBagConstraints.RELATIVE; // Ligne suivante automatiquement
        gbc.insets = new Insets(10, 0, 10, 0); // Marges autour des boutons

        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(new File(DIRECTORYPROJECT + "\\src\\main\\java\\Ressources\\background\\background2.png"));
        } catch (IOException e) {
            System.err.println("l'image n'as pas charger ");
            e.printStackTrace();
        }

        if (originalImage != null) {
            // Obtenir les dimensions originales
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();

            // Calculer la nouvelle hauteur pour conserver le ratio
            int newHeight = (int) ((double) frameWidth / originalWidth * originalHeight);

            background = new BufferedImage(frameWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

            // Remplir l'image de fond avec l'image redimensionnée
            Graphics g = background.getGraphics();
            g.drawImage(originalImage.getScaledInstance(frameWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
            g.dispose();
        }

        // Création du bouton pour accéder à l'écran de connexion
        btnLogin = new ButtonMenu(
                DIRECTORYPROJECT + "\\src\\main\\java\\Ressources\\Buttons\\buttonLogin.png", // Image du bouton "Se connecter"
                e -> frame.addNewPanelLogin(), // Affiche le panneau de connexion quand le bouton est cliqué
                4, // Identifiant du bouton
                frame.getWidth(), // Largeur de la fenêtre
                frame.getHeight(), // Hauteur de la fenêtre
                -50 // Position verticale du bouton (plus haut sur l'écran)
        );

// Création du bouton pour jouer en tant qu'invité (sans se connecter)
        btnGuest = new ButtonMenu(
                DIRECTORYPROJECT + "\\src\\main\\java\\Ressources\\Buttons\\buttonGuest.png", // Image du bouton "Invité"
                e -> frame.addNewPanelGame(), // Lance le jeu directement sans passer par la connexion
                4, // Identifiant du bouton
                frame.getWidth(), // Largeur de la fenêtre
                frame.getHeight(), // Hauteur de la fenêtre
                50 // Position verticale du bouton (plus bas que le bouton Login)
        );

        // Création d'un bouton personnalisé pour accéder à la fenêtre de jeu avec ta propre IA
        btnAI = new ButtonMenu(
                DIRECTORYPROJECT + "\\src\\main\\java\\Ressources\\Buttons\\buttonIA.png", // Chemin vers l'image du bouton
                e -> {
                    try {
                        // Ajoute un nouveau panneau avec l'IA personnalisée lorsque le bouton est cliqué
                        frame.addNewPanelOpenAI();
                    } catch (IOException ex) {
                        // Gère les erreurs d'entrée/sortie, par exemple si une ressource est manquante
                        Logger.getLogger(CPanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        // Gère les interruptions du thread lors du chargement de la fenêtre
                        Logger.getLogger(CPanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                },
                4, // Identifiant ou ordre du bouton
                frame.getWidth(), // Largeur actuelle de la fenêtre (utilisée pour positionner le bouton)
                frame.getHeight(), // Hauteur actuelle de la fenêtre
                150 // Position verticale du bouton (plus haut que le bouton OpenAI)
        );

        // Création d'un bouton personnalisé pour accéder à la fenêtre de jeu avec l'IA OpenAI
        btnGpt = new ButtonMenu(
                DIRECTORYPROJECT + "\\src\\main\\java\\Ressources\\Buttons\\buttonGPT.png", // Chemin vers l'image du bouton
                e -> {
                    try {
                        // Ajoute un nouveau panneau avec l'IA OpenAI lorsqu'on clique sur le bouton
                        frame.addNewPanelOpenAI();
                    } catch (IOException ex) {
                        // Gère les erreurs liées à la lecture de fichiers
                        Logger.getLogger(CPanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        // Gère les interruptions du thread
                        Logger.getLogger(CPanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                },
                4, // ID ou index du bouton (utilisé dans certains designs pour l’ordre ou le type)
                frame.getWidth(), // Largeur de la fenêtre (utilisé pour positionnement/taille du bouton)
                frame.getHeight(), // Hauteur de la fenêtre
                250 // Position verticale du bouton (en pixels)
        );

        // Ajout des boutons au panel
        this.add(btnLogin, gbc);
        this.add(btnGuest, gbc);
        this.add(btnAI, gbc);
        this.add(btnGpt, gbc);
    }

    /**
     * Redéfinit la méthode paintComponent pour dessiner une image de fond sur
     * le composant.
     *
     * @param g l'objet Graphics utilisé pour dessiner
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            // Dessiner l'image de fond aux coordonnées (0, 0)
            g.drawImage(background, 0, 0, this);
        }
    }

    /**
     * Méthode appelée lorsqu'un événement est déclenché.
     *
     * @param eventName le nom de l'événement reçu
     * @param data les données associées à l'événement
     */
    @Override
    public void onEvent(String eventName, Object data) {
        // Affiche le nom de l'événement reçu dans la console (à remplacer par une logique personnalisée)
        System.out.println("Événement reçu : " + eventName);
    }

}
