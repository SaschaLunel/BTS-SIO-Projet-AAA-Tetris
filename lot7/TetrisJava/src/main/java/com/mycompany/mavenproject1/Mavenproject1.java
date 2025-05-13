/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.mavenproject1;


import BDD.baseDeDonnees;
import events.InterfaceMain;
import java.io.IOException;


/**
 *
 * @author SIO
 */
public class Mavenproject1 implements InterfaceMain {
    private static int sizeWidth = 1080;
    private static int sizeHeight = 720;

     private baseDeDonnees bdd;
    private  static MainFrame frame;
    private static Mavenproject1 mainInstance;

    public Mavenproject1() {
        frame = new MainFrame("TETRISCRAFT", this);
    }

    

    
   

    

    
    
    
    public static int getSizeWidth() {
        return sizeWidth;
    }

    public static int getSizeHeight() {
        return sizeHeight;
    }

    @Override
    public void addNewPanelGame() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
     public static void main(String[] args) throws IOException {
        // Créer UNE SEULE instance de Mavenproject1
        mainInstance = new Mavenproject1();
        
        
}
}