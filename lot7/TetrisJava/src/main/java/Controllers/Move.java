/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

/**
 *
 * @author SIO
 */


public class Move {
    private int rotation;  // Rotation de la pièce
    private int column;    // Colonne où la pièce est placée

    

    public Move(int rotation, int column) {
        this.rotation = rotation;
        this.column = column;
    }

    public int getRotation() {
        return rotation;
    }

    public int getColumn() {
        return column;
    }
}



