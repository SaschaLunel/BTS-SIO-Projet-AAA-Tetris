/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btn;

import javax.swing.JButton;

/**
 *
 * @author SIO
 */
public class ButtonMenu {
    
    private JButton button;
    
    static String content = "Toto";
    
    public ButtonMenu() {
    this.button = new JButton(content);
}

    
    public JButton getButtonMenu() {
        return button;

}

}
