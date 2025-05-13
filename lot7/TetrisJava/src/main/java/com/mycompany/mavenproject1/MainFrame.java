/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import BDD.CPlayer;
import BDD.CRequeteSql;
import BDD.baseDeDonnees;
import Panel.CPanelGameOpenAI;
import Panel.CPanelGame;
import Panel.CPanelGameAI;
import Panel.CPanelMenu;
import Panel.CPanelMenuLogin;
import Panel.CPanelSignUp;
import Panel.CPanelTransition;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author SIO
 */
public class MainFrame extends JFrame {

    Mavenproject1 mainInstance;
    private JPanel currentPanel;
    private CPlayer player;

    static int sizeWindowsWidth = 1080;
    static int sizeWindowsHeight = 720;
    static String nameTitle;

    public MainFrame(String title, Mavenproject1 mainInstance) {
        super(title);
        this.nameTitle = title;
        this.mainInstance = mainInstance;
        initFrameGame();
    }

    /**
     * Initialise la fenêtre principale du jeu.
     * <p>
     * Configure la fenêtre avec une taille par défaut, initialise le panel de
     * menu principal et rend la fenêtre visible au centre de l'écran.
     */
    void initFrameGame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermer l'application à la fermeture de la fenêtre

        // Configurer la fenêtre avec une taille par défaut
        this.setSize(sizeWindowsWidth, sizeWindowsHeight);

        // Instance du panel de menu principal
        currentPanel = new CPanelMenu(mainInstance, this);
        this.add(currentPanel);

        // Rendre la fenêtre visible
        this.setVisible(true);

        // Centrer la fenêtre sur l'écran
        this.setLocationRelativeTo(null);
    }

    /**
     * Remplace le panel actuel par un nouveau panel de menu principal.
     * <p>
     * Cette méthode supprime tous les composants de la fenêtre, ajoute une
     * nouvelle instance de {@code CPanelMenu}, puis réactualise l'affichage.
     * Elle donne également le focus au nouveau panel pour garantir la réception
     * des événements clavier.
     */
    public void addNewPanelMenu() {
        currentPanel = new CPanelMenu(mainInstance, this); // Créer une nouvelle instance du menu principal
        getContentPane().removeAll(); // Supprimer tous les composants existants de la fenêtre
        add(currentPanel); // Ajouter le nouveau panel à la fenêtre
        revalidate(); // Recalculer la disposition des composants
        repaint(); // Repeindre la fenêtre

        // Très important : donner le focus au nouveau panel
        currentPanel.setFocusable(true);
        currentPanel.requestFocusInWindow();
    }

    /**
     * Remplace le panel actuel par un nouveau panel de jeu avec intelligence
     * artificielle.
     * <p>
     * Cette méthode initialise une instance de {@code CPanelGameAI}, remplace
     * les composants de la fenêtre, met à jour l'affichage et donne le focus au
     * nouveau panel.
     *
     * @throws IOException si une erreur d'entrée/sortie se produit lors de
     * l'initialisation du panel
     * @throws InterruptedException si le thread est interrompu pendant le
     * processus
     */
    public void addNewPanelAI() throws IOException, InterruptedException {

        currentPanel = new CPanelGameAI(this); // Créer une nouvelle instance du panel de jeu avec IA

        getContentPane().removeAll(); // Supprimer tous les composants existants
        add(currentPanel); // Ajouter le nouveau panel
        revalidate(); // Recalculer la disposition
        repaint(); // Repeindre la fenêtre

        // Très important : donner le focus au nouveau panel
        currentPanel.setFocusable(true);
        currentPanel.requestFocusInWindow();
    }

    /**
     * Remplace le panel actuel par un nouveau panel de jeu standard (sans
     * intelligence artificielle).
     * <p>
     * Cette méthode instancie un nouveau {@code CPanelGame}, met à jour les
     * composants de la fenêtre, rafraîchit l'affichage et attribue le focus au
     * nouveau panel.
     */
    public void addNewPanelGame() {
        currentPanel = new CPanelGame(this); // Créer une nouvelle instance du panel de jeu

        getContentPane().removeAll(); // Supprimer les composants existants de la fenêtre
        add(currentPanel); // Ajouter le nouveau panel
        revalidate(); // Recalculer l'agencement des composants
        repaint(); // Repeindre la fenêtre

        // Très important : donner le focus au nouveau panel
        currentPanel.setFocusable(true);
        currentPanel.requestFocusInWindow();
    }

    /**
     * Remplace le panel actuel par un nouveau panel de connexion utilisateur.
     * <p>
     * Cette méthode crée une instance de {@code CPanelMenuLogin}, efface les
     * composants de la fenêtre actuelle, met à jour l'affichage, et donne le
     * focus au nouveau panel pour permettre la saisie utilisateur.
     */
    public void addNewPanelLogin() {
        currentPanel = new CPanelMenuLogin(mainInstance, this); // Créer une nouvelle instance du panel de connexion

        getContentPane().removeAll(); // Supprimer tous les composants de la fenêtre
        add(currentPanel); // Ajouter le nouveau panel
        revalidate(); // Recalculer l'agencement des composants
        repaint(); // Repeindre la fenêtre

        // Très important : donner le focus au nouveau panel
        currentPanel.setFocusable(true);
        currentPanel.requestFocusInWindow();
    }

    /**
     * Remplace le panel actuel par un nouveau panel de transition.
     * <p>
     * Cette méthode instancie un {@code CPanelTransition}, supprime les
     * composants de la fenêtre, met à jour l'affichage, puis donne le focus au
     * nouveau panel. Elle est utilisée pour afficher une scène de transition
     * entre deux états du jeu.
     */
    public void addNewPanelTransition() {
        currentPanel = new CPanelTransition(this); // Créer une nouvelle instance du panel de transition

        getContentPane().removeAll(); // Supprimer tous les composants existants
        add(currentPanel); // Ajouter le nouveau panel à la fenêtre
        revalidate(); // Recalculer l'agencement des composants
        repaint(); // Repeindre la fenêtre

        // Très important : donner le focus au nouveau panel
        currentPanel.setFocusable(true);
        currentPanel.requestFocusInWindow();
    }

    /**
     * Remplace le panel actuel par un nouveau panel d'inscription utilisateur.
     * <p>
     * Cette méthode crée une instance de {@code CPanelSignUp}, supprime les
     * composants existants de la fenêtre, met à jour l'affichage, et donne le
     * focus au nouveau panel pour permettre la saisie des informations
     * d'inscription.
     */
    public void addNewPanelSignUp() {
        currentPanel = new CPanelSignUp(this); // Créer une nouvelle instance du panel d'inscription

        getContentPane().removeAll(); // Supprimer tous les composants de la fenêtre
        add(currentPanel); // Ajouter le nouveau panel
        revalidate(); // Recalculer l'agencement
        repaint(); // Repeindre la fenêtre

        // Très important : donner le focus au nouveau panel
        currentPanel.setFocusable(true);
        currentPanel.requestFocusInWindow();
    }

    /**
     * Remplace le panel actuel par un nouveau panel de jeu utilisant OpenAI.
     * <p>
     * Cette méthode instancie un {@code CPanelGameOpenAI}, supprime les
     * composants actuels de la fenêtre, met à jour l'affichage, et attribue le
     * focus au nouveau panel pour permettre les interactions utilisateur.
     *
     * @throws IOException si une erreur d'entrée/sortie survient lors de
     * l'initialisation
     * @throws InterruptedException si le thread est interrompu pendant le
     * processus
     */
    public void addNewPanelOpenAI() throws IOException, InterruptedException {
        currentPanel = new CPanelGameOpenAI(this); // Créer une nouvelle instance du panel de jeu avec OpenAI

        getContentPane().removeAll(); // Supprimer les composants existants de la fenêtre
        add(currentPanel); // Ajouter le nouveau panel
        revalidate(); // Recalculer l'agencement
        repaint(); // Repeindre la fenêtre

        // Très important : donner le focus au nouveau panel
        currentPanel.setFocusable(true);
        currentPanel.requestFocusInWindow();
    }

    /**
     * Crée un nouveau compte utilisateur dans la base de données et initialise
     * l'objet joueur.
     * <p>
     * Cette méthode appelle une requête asynchrone pour créer un utilisateur,
     * puis récupère son identifiant une fois l'opération terminée. Elle
     * remplace ensuite le panel actuel par celui de connexion.
     *
     * @param email l'adresse email de l'utilisateur
     * @param pseudo le pseudonyme de l'utilisateur
     * @param password le mot de passe de l'utilisateur
     * @param prenom le prénom de l'utilisateur
     * @param nom le nom de l'utilisateur
     * @param dBirth la date de naissance de l'utilisateur
     * @throws SQLException si une erreur survient lors de la communication avec
     * la base de données
     * @throws InterruptedException si l'attente du résultat asynchrone est
     * interrompue
     * @throws ExecutionException si une erreur survient pendant l'exécution
     * d'une tâche asynchrone
     */
    public void createNewAccount(String email, String pseudo, String password, String prenom, String nom, String dBirth)
            throws SQLException, InterruptedException, ExecutionException {

        // Ajouter le panel de connexion
        addNewPanelLogin();

        // Créer une instance de la base de données
        baseDeDonnees bdd = new baseDeDonnees();

        try {
            // Lancer la création de l'utilisateur de façon asynchrone et attendre la fin
            CompletableFuture<Void> future = CRequeteSql.createUser(email, password, prenom, nom, pseudo, dBirth);
            future.get(); // bloque jusqu'à la fin de la tâche
            System.out.println("Tâche terminée !");
        } catch (ExecutionException e) {
            throw new RuntimeException("Erreur lors de la création de l'utilisateur", e);
        }

        // Récupérer l'identifiant de l'utilisateur de manière asynchrone et attendre le résultat
        CompletableFuture<Integer> futureId = CRequeteSql.getIdUser(pseudo);
        int id = futureId.get(); // attendre la récupération de l'ID

        // Créer l'objet joueur avec les informations saisies
        player = new CPlayer(prenom, nom, pseudo, email, id);
    }

    public void initSession(String Pseudo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
