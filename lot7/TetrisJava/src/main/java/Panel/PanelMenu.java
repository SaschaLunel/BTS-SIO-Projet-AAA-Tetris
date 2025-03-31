package Panel;

import Components.ButtonMenu;
import com.mycompany.mavenproject1.CMyFrame;
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

public class PanelMenu extends JPanel implements EventListener {

    // Chemin absolu du projet
    final private String DIRECTORYPROJECT = System.getProperty("user.dir");
    private ButtonMenu btnLogin;
    private ButtonMenu btnGuest;
    private ButtonMenu btnAI;
    private BufferedImage background;
    private JFrame frame;
    private int frameWidth;

    public PanelMenu(Mavenproject1 mainInstance, CMyFrame frame) {
        
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

        // Instance des boutons
        btnLogin = new ButtonMenu(DIRECTORYPROJECT + "\\src\\main\\java\\Ressources\\Menu\\buttonLogin.png",
                e -> frame.addNewPanelLogin(), 4, frame.getWidth(), frame.getHeight(), -50);

        btnGuest = new ButtonMenu(DIRECTORYPROJECT + "\\src\\main\\java\\Ressources\\Menu\\buttonGuest.png",
                e -> frame.addNewPanelGame(), 4, frame.getWidth(), frame.getHeight(), 50);
        
        btnAI = new ButtonMenu(DIRECTORYPROJECT + "\\src\\main\\java\\Ressources\\Menu\\buttonIA.png",
                e -> {
            try {
                frame.addNewPanelAI();
            } catch (IOException ex) {
                Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }, 4, frame.getWidth(), frame.getHeight(), 150);

        // Ajout des boutons au panel
        this.add(btnLogin, gbc);
        this.add(btnGuest, gbc);
        this.add(btnAI, gbc);
    }

    // Redéfinir paintComponent pour dessiner l'image de fond
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            // Dessiner l'image de fond
            g.drawImage(background, 0, 0, this);
        }
    }

    @Override
    public void onEvent(String eventName, Object data) {
        // Gestion des événements (ajoute ta logique ici)
        System.out.println("Événement reçu : " + eventName);
    }
}
