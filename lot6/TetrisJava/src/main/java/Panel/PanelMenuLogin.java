package Panel;

import Components.ButtonMenu;
import com.mycompany.mavenproject1.CMyFrame;
import com.mycompany.mavenproject1.Mavenproject1;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * PanelMenuLogin class - Login screen panel with input fields and buttons.
 */
public class PanelMenuLogin extends JPanel {

    private final String DIRECTORYPROJECT = System.getProperty("user.dir");
    private CMyFrame frame;
    private int frameWidth;
    private BufferedImage background;

    private JTextField textFieldPseudo;
    private JPasswordField textFieldPassword;
    private ButtonMenu btnValidate;
    private ButtonMenu btnRegister;
    private ButtonMenu btnBack;
    

    public PanelMenuLogin(Mavenproject1 mainInstance, CMyFrame frame) {
        this.frame = frame;
        frameWidth = Mavenproject1.getSizeWidth();

        this.setLayout(new GridBagLayout()); // Use GridBagLayout for centering
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Spacing between elements

        // Load background image
        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(new File(DIRECTORYPROJECT + "\\src\\main\\java\\Ressources\\background\\background2.png"));
        } catch (IOException e) {
            System.err.println("L'image n'a pas pu être chargée.");
            e.printStackTrace();
        }

        if (originalImage != null) {
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();
            int newHeight = (int) ((double) frameWidth / originalWidth * originalHeight);

            background = new BufferedImage(frameWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics g = background.getGraphics();
            g.drawImage(originalImage.getScaledInstance(frameWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
            g.dispose();
        }

        // Place "Register" button at the top
        btnRegister = new ButtonMenu(DIRECTORYPROJECT + "\\src\\main\\java\\Ressources\\Menu\\buttonRegister.png",
                e -> frame.addNewPanelSignUp(), 4, frame.getWidth(), frame.getHeight(), -50);
        gbc.gridy = 0;
        this.add(btnRegister, gbc);

        // Create input field for pseudo (username)
        textFieldPseudo = new JTextField(20);
        gbc.gridy = 1;
        this.add(textFieldPseudo, gbc);

        // Create input field for password
        textFieldPassword = new JPasswordField(20);
        gbc.gridy = 2;
        this.add(textFieldPassword, gbc);

        // Create "Validate" button just below password field
        btnValidate = new ButtonMenu(DIRECTORYPROJECT + "\\src\\main\\java\\Ressources\\Menu\\buttonValidate.png",
                e -> frame.addNewPanelLogin(), 4, frame.getWidth(), frame.getHeight(), -50);
        gbc.gridy = 3;
        this.add(btnValidate, gbc);
        
        
        btnBack = new ButtonMenu(DIRECTORYPROJECT + "\\src\\main\\java\\Ressources\\Menu\\buttonBack.png",
                e -> frame.addNewPanelMenu(), 4, frame.getWidth(), frame.getHeight(), -50);
        gbc.gridy = 4;
        this.add(btnBack, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, this);
        }
    }
}
