package Panel;

import BDD.baseDeDonnees;
import Components.ButtonMenu;
import Components.MyFormulaire;
import com.mycompany.mavenproject1.CMyFrame;
import com.mycompany.mavenproject1.Mavenproject1;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * PanelSignUp class - Panel for account registration.
 */
public class PanelSignUp extends JPanel {

    private MyFormulaire formulaire;
    private ButtonMenu btnValidate;
    private ButtonMenu btnLogin;
    private ButtonMenu btnBack;
    private CMyFrame frame;
    private final String DIRECTORYPROJECT = System.getProperty("user.dir");

    private int frameWidth;
    private BufferedImage background;

    public PanelSignUp(CMyFrame frame) {
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

        
        // Create "Login" button (to go back to login page)
        btnLogin = new ButtonMenu(DIRECTORYPROJECT + "\\src\\main\\java\\Ressources\\Menu\\buttonLogin.png",
                e -> frame.addNewPanelLogin(), 4, frame.getWidth(), frame.getHeight(), -50);
        gbc.gridy = 0;
        this.add(btnLogin, gbc);
        
        // Create and add the form
        formulaire = new MyFormulaire();
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        this.add(formulaire, gbc);
        gbc.gridwidth = 1;

        // Create "Validate" button (for account creation)
        btnValidate = new ButtonMenu(DIRECTORYPROJECT + "\\src\\main\\java\\Ressources\\Menu\\buttonValidate.png",
                e -> {
            try {
                createAccount();
            } catch (SQLException ex) {
                Logger.getLogger(PanelSignUp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }, 4, frame.getWidth(), frame.getHeight(), -50);
        gbc.gridy = 2;
        this.add(btnValidate, gbc);
        
        btnBack = new ButtonMenu(DIRECTORYPROJECT + "\\src\\main\\java\\Ressources\\Menu\\buttonBack.png",
                e -> frame.addNewPanelMenu(), 4, frame.getWidth(), frame.getHeight(), -50);
        gbc.gridy = 4;
        this.add(btnBack, gbc);
        
    }

    private void createAccount() throws SQLException {
        // Get user input from the form
        String nom = formulaire.getNom();
        String prenom = formulaire.getPrenom();
        String email = formulaire.getEmail();
        String pseudo = formulaire.getPseudo();
        String password = formulaire.getPassword();
        String confirmPassword = formulaire.getConfirmPassword();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDate dBirth = LocalDate.parse(formulaire.getDateNaissance(), formatter);

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Les mots de passe ne correspondent pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        baseDeDonnees bdd = new baseDeDonnees();
        boolean succes = bdd.verifExistUser(pseudo);
        if(succes){
            frame.createNewAccount(email, pseudo, password, prenom, nom, dBirth);
        }
        
        
else {
            impossibleNewAccount();
        }
    }

    private void impossibleNewAccount() {
        JOptionPane.showMessageDialog(this, "Impossible de créer le compte. Veuillez réessayer.", "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, this);
        }
    }
}
