package Gameplay;

import BlockFolder.Block;
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
    public static BufferedImage image;
    private static BufferedImage imageEmpty;
    private static boolean useSlot = false;
    public Block block = null;
    
    
    
public GridSlot(){  
        
        String directoryProject = System.getProperty("user.dir");
        
        
        try {
            image = ImageIO.read(new File(directoryProject.concat("\\src\\main\\java\\Gameplay\\img_slot.png")));
            
        } catch (IOException e) {
            // Log the error or handle it appropriately
            System.err.println("Error loading images: " + e.getMessage());
            // Provide default images or set to null
            image = null;
            imageEmpty = null;
        }
    }
     


public BufferedImage getImage(boolean type){
    
    if (type){return image;}
    else {return image; }
}

public boolean IsValidSlot(){
    
    return useSlot;
}

public void SetUse(boolean use){
    useSlot = use;
}
}