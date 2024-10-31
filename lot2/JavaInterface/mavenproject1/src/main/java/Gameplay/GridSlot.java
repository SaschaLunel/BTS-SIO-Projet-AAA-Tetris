package Gameplay;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author SIO
 */


public class GridSlot {
    private static BufferedImage image;
    private static BufferedImage imageEmpty;
    private static boolean useSlot = false;
    
     static {
        try {
            image = ImageIO.read(new File("./img_slot.png"));
            imageEmpty = ImageIO.read(new File("./img_slot.png"));
        } catch (IOException e) {
            // Log the error or handle it appropriately
            System.err.println("Error loading images: " + e.getMessage());
            // Provide default images or set to null
            image = null;
            imageEmpty = null;
        }
    }


public BufferedImage getSlot(boolean type){

    if (type){return image;}
    else {return imageEmpty; }
}


}