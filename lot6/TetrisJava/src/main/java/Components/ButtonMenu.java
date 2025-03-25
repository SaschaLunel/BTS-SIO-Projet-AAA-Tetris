package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonMenu extends JButton {

    // Constructor
    public ButtonMenu(String imagePath, ActionListener action, int scale, int frameWidth, int frameHeight, int yOffset) {
        // Load and scale the image
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(icon.getIconWidth() * scale, icon.getIconHeight() * scale, Image.SCALE_SMOOTH);
        this.setIcon(new ImageIcon(img));

        // Remove button background and border
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);

        // Add action listener
        this.addActionListener(action);

        // Set button size based on image
        this.setSize(icon.getIconWidth() * scale, icon.getIconHeight() * scale);

        // Center the button with vertical offset
        int x = (frameWidth - this.getWidth()) / 2;
        int y = (frameHeight - this.getHeight()) / 2 + yOffset;
        this.setBounds(x, y, this.getWidth(), this.getHeight());
    }
}
