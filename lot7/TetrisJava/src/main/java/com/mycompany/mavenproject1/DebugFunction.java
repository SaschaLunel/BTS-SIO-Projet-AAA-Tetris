/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author SIO
 */
public class DebugFunction {
    
    static public void printArray2D(int [][] array2D){
        for (int i = 0; i < array2D.length; i++) {
                // Loop through each element in the row
                for (int j = 0; j < array2D[i].length; j++) {
                    // Print the element with a space
                    System.out.print(array2D[i][j] + " ");
                }
                // Move to the next line after printing each row
                System.out.println();
            }
    }
}
