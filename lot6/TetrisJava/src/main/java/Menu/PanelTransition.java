/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PanelTransition extends JPanel {
    private JProgressBar progressBar;
    private JLabel messageLabel;
    private Timer timer;
    private String[] messages = {
        "Calculating the meaning of life...",
        "Hacking the mainframe...",
        "Compiling code in the matrix...",
        "Loading... Please wait.",
        "Almost there... Just one more bug to fix.",
        "Finding your data... Don't panic."
    };
    private JPanel nextPanel;

    public PanelTransition(JPanel nextPanel) {
        this.nextPanel = nextPanel;
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0)); // Fond noir

        // Message
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(messageLabel, BorderLayout.NORTH);

        // Progress Bar
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setStringPainted(true);
        add(progressBar, BorderLayout.CENTER);

        // Timer pour afficher des messages et ajuster la durée
        Random rand = new Random();
        int duration = rand.nextInt(2000) + 2000; // entre 2 et 4 secondes

        timer = new Timer(500, e -> {
            // Change le message toutes les 500ms
            messageLabel.setText(messages[rand.nextInt(messages.length)]);
        });

        timer.start();

        // Délai de fermeture après entre 2 et 4 secondes
        new Timer(duration, e -> {
            timer.stop();
            // Ici, vous pouvez rajouter un code pour passer à un autre écran si besoin.
            JOptionPane.showMessageDialog(this, "Chargement terminé !");
            System.err.println("il faut ouvrir "+nextPanel);
        }).start();
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Transition Panel");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 200);
//        frame.setLocationRelativeTo(null);
//        frame.setContentPane(new JPanelTransition());
//        frame.setVisible(true);
    }
}

