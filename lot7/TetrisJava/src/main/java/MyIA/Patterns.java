/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyIA;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SIO
 */

    
    public class Patterns {
    private List<Pattern> patterns;

    // Constructor to initialize the patterns list
    public Patterns() {
        patterns = new ArrayList<>();
    }

    // Method to add a new pattern (block shape) to the list
    public void addPattern(Pattern pattern) {
        patterns.add(pattern);
    }
    
        // Method to find the best position for a block in a grid
    public int[] findBestPlacement(int[][] grid, int[][] block) {
        Pattern blockPattern = new Pattern(block);
        int bestX = -1, bestY = -1, bestRotation = 0;
        int maxFittingScore = 0;

        // Try all rotations of the block (0, 90, 180, 270 degrees)
        for (int rotation = 0; rotation < 4; rotation++) {
            blockPattern.rotate(); // Rotate the block
            // Try placing the block in all possible positions
            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[0].length; y++) {
                    if (blockPattern.fits(grid, x, y)) {
                        // A fitting position is found, calculate the score (here just 1 for simplicity)
                        int score = calculateFitScore(grid, blockPattern, x, y);
                        if (score > maxFittingScore) {
                            maxFittingScore = score;
                            bestX = x;
                            bestY = y;
                            bestRotation = rotation;
                        }
                    }
                }
            }
        }

        return new int[] {bestX, bestY, bestRotation}; // Return the best position and rotation
    }
    // A simple method to calculate how well the block fits into the grid (for this example, just count)
    private int calculateFitScore(int[][] grid, Pattern block, int x, int y) {
        int score = 0;
        for (int i = 0; i < block.getShape().length; i++) {
            for (int j = 0; j < block.getShape()[0].length; j++) {
                if (block.getShape()[i][j] != 0) {
                    score++;
                }
            }
        }
        return score;
    }
}
