/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyIA;

/**
 *
 * @author SIO
 */

    
    public class Pattern {
    private int[][] shape;
    
    // Constructor to initialize the pattern with a given shape
    public Pattern(int[][] shape) {
        this.shape = shape;
    }

    // Getter for the pattern shape
    public int[][] getShape() {
        return shape;
    }

    // Method to rotate the block (90 degrees clockwise)
    public void rotate() {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotated = new int[cols][rows];
        
        // Rotate the matrix
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = shape[i][j];
            }
        }
        shape = rotated;
    }

    // Method to check if the pattern fits into the grid at a specific position
    public boolean fits(int[][] grid, int x, int y) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                // Check bounds and if the space is free
                if (shape[i][j] != 0) {
                    if (x + i >= grid.length || y + j >= grid[0].length || grid[x + i][y + j] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}


