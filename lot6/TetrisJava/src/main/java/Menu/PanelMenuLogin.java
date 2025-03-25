/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import com.mycompany.mavenproject1.Mavenproject1;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author SIO
 */
public class PanelMenuLogin extends JPanel {

    final private String DIRECTORYPROJECT = System.getProperty("user.dir");
    private JFrame frame;
    private int frameWidth;
    private ButtonMenu btnBack;
    
    private BufferedImage background;
    
    public PanelMenuLogin(JFrame frame) {
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
    
}
