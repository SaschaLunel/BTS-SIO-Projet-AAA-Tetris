package Menu;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import events.InterfaceMain;

public class PanelTransition extends JPanel {
    private JProgressBar progressBar;
    private JLabel messageLabel;
    private Timer messageTimer, progressTimer;
    private String[] messages = {
        "Calculating the meaning of life...",
        "Hacking the mainframe...",
        "Compiling code in the matrix...",
        "Loading... Please wait.",
        "Almost there... Just one more bug to fix.",
        "Finding your data... Don't panic."
    };
    private JPanel nextPanel;
    private int progress = 0;
    private Image backgroundImage;
    private InterfaceMain interfaceMain;

    public PanelTransition(InterfaceMain interfaceMain) {
        this.nextPanel = nextPanel;
        this.interfaceMain = interfaceMain;
        String directoryProject = System.getProperty("user.dir");
        String backgroundPath = directoryProject + "\\src\\main\\java\\Ressources\\background\\background3.png";
        setLayout(new BorderLayout());
        
        // Chargement de l'image de fond
        backgroundImage = new ImageIcon(backgroundPath).getImage();
        
        // Message
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(messageLabel, BorderLayout.NORTH);
        
        // Progress Bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        add(progressBar, BorderLayout.SOUTH);
        
        Random rand = new Random();
        messageTimer = new Timer(500, e -> messageLabel.setText(messages[rand.nextInt(messages.length)]));
        messageTimer.start();
        
        progressTimer = new Timer(20, e -> {
            progress += 1;
            progressBar.setValue(progress);
            if (progress >= 100) {
                ((Timer) e.getSource()).stop();
                messageTimer.stop();
                interfaceMain.addNewPanelGame();
            }
        });
        progressTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}