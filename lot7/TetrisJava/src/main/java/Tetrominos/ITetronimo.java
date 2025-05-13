/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Tetrominos;

/**
 *
 * @author SIO
 */
public interface ITetronimo {
    int[][][] getShape(); // Méthode pour obtenir la forme du bloc
    String getName();     // Méthode pour obtenir le nom du bloc
    int length();        // Méthode pour récupérer la longueur dans L'axe X
}

