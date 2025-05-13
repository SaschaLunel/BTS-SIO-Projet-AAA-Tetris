package Components;

import javax.swing.*;
import java.awt.*;

/**
 * MyFormulaire class - Represents a form with multiple input fields.
 */
public class MyFormulaire extends JPanel {

    private JTextField fieldNom;
    private JTextField fieldPrenom;
    private JTextField fieldDateNaissanceJour;
    private JTextField fieldDateNaissanceMois;
    private JTextField fieldDateNaissanceAnnee;
    private JTextField fieldEmail;
    private JTextField fieldPseudo;
    private JPasswordField fieldPassword;
    private JPasswordField fieldConfirmPassword;

    public MyFormulaire() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        // Add "Nom" field
        gbc.gridy = 0;
        this.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1;
        fieldNom = new JTextField(20);
        this.add(fieldNom, gbc);

        // Add "Prénom" field
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(new JLabel("Prénom :"), gbc);
        gbc.gridx = 1;
        fieldPrenom = new JTextField(20);
        this.add(fieldPrenom, gbc);

        // Add "Date de naissance Jour " field
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(new JLabel("Jour de naissance :"), gbc);
        gbc.gridx = 1;
        fieldDateNaissanceJour = new JTextField(2);
        this.add(fieldDateNaissanceJour, gbc);
        
        // Add "Date de naissance Mois" field
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(new JLabel("Mois de naissance:"), gbc);
        gbc.gridx = 1;
        fieldDateNaissanceMois = new JTextField(2);
        this.add(fieldDateNaissanceMois, gbc);
        // Add "Date de naissance Annee" field
        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(new JLabel("Année de naissance :"), gbc);
        gbc.gridx = 1;
        fieldDateNaissanceAnnee = new JTextField(2);
        this.add(fieldDateNaissanceAnnee, gbc);
        

        // Add "Email" field
        gbc.gridx = 0;
        gbc.gridy = 5;
        this.add(new JLabel("Email :"), gbc);
        gbc.gridx = 1;
        fieldEmail = new JTextField(20);
        this.add(fieldEmail, gbc);

        // Add "Pseudo" field
        gbc.gridx = 0;
        gbc.gridy = 6;
        this.add(new JLabel("Pseudo :"), gbc);
        gbc.gridx = 1;
        fieldPseudo = new JTextField(20);
        this.add(fieldPseudo, gbc);

        // Add "Mot de passe" field
        gbc.gridx = 0;
        gbc.gridy = 7;
        this.add(new JLabel("Mot de passe :"), gbc);
        gbc.gridx = 1;
        fieldPassword = new JPasswordField(20);
        this.add(fieldPassword, gbc);

        // Add "Confirmation de mot de passe" field
        gbc.gridx = 0;
        gbc.gridy = 8;
        this.add(new JLabel("Confirmer le mot de passe :"), gbc);
        gbc.gridx = 1;
        fieldConfirmPassword = new JPasswordField(20);
        this.add(fieldConfirmPassword, gbc);
    }

    
    
    
    
    // Getters to retrieve user input
    public String getNom() { return fieldNom.getText(); }
    public String getPrenom() { return fieldPrenom.getText(); }
    public String getDateNaissance() { return fieldDateNaissanceAnnee.getText()+"-"+fieldDateNaissanceJour.getText()+"-"+fieldDateNaissanceJour.getText();}
    public String getEmail() { return fieldEmail.getText(); }
    public String getPseudo() { return fieldPseudo.getText(); }
    public String getPassword() { return new String(fieldPassword.getPassword()); }
    public String getConfirmPassword() { return new String(fieldConfirmPassword.getPassword()); }
}
