package Menu;

import events.EventListener;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PanelMenu extends JPanel implements EventListener {

    // Chemin absolu du projet
    final private String DIRECTORYPROJECT = System.getProperty("user.dir");
    private ButtonMenu btnLogin;
    private ButtonMenu btnGuest;
    private BufferedImage background;
    private JFrame frame;

    public PanelMenu(JFrame frame) {
        // Définition du layout
        this.setLayout(null);
        this.frame = frame;
        // Largeur de la frame
        int frameWidth = this.frame.getWidth();
       
        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(new File(DIRECTORYPROJECT + "\\src\\main\\java\\Ressources\\background\\background2.png"));
        } catch (IOException e) {
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
                e -> System.out.println("Connexion"), 4, frame.getWidth(), frame.getHeight(), -50);

        btnGuest = new ButtonMenu(DIRECTORYPROJECT + "\\src\\main\\java\\Ressources\\Menu\\buttonGuest.png",
                e -> System.out.println("Mode Invité"), 4, frame.getWidth(), frame.getHeight(), 50);

        // Ajout des boutons au panel
        this.add(btnLogin);
        this.add(btnGuest);
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
