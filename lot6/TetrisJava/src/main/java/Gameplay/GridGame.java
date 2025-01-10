/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gameplay;

import BlockFolder.AbstractBlock;
import BlockFolder.LBlock;
import BlockFolder.OBlock;
import BlockFolder.ZBlock;
import BlockFolder.SBlock;
import BlockFolder.JBlock;
import BlockFolder.IBlock;
import BlockFolder.TBlock;
import java.util.Random;



/**
 *
 * @author SIO
 */
public class GridGame {
    public int[][] gridGame;
    private int row;
    private int column;
    private int index_color;
    private int index_rotation;
    private int index_x;
    private int index_y;
    private AbstractBlock new_block;

    

   
    
    
    // Tableau contenant tous les types de blocs
    private static final AbstractBlock[] blocks = {
        new LBlock(),
        new OBlock(),
        new ZBlock(),
        new SBlock(),
        new JBlock(),
        new IBlock(),
        new TBlock()
    };

    public GridGame(int row, int column) {
        this.column = column;
        this.row = row;
        gridGame = new int[row][column];
    }
    
    // Méthode pour récupérer un bloc aléatoire
    public static AbstractBlock getRandomBlock() {
        Random random = new Random();
        int randomIndex = random.nextInt(blocks.length); // Génère un index aléatoire
        return blocks[randomIndex]; // Retourne le bloc à cet index
    }
    
            // Méthode qui renvoie une grille
        public int[][] CreateGrid() {
            
            
            // Récupérer un bloc aléatoire
             new_block = getRandomBlock();

            // Définir la position de départ du bloc, centré dans la grille en haut
             index_y = (int) (gridGame[0].length * 0.5f - new_block.getShape()[0].length * 0.5f); 
             index_x = 0;

            // Récupérer la forme du bloc pour l'orientation 0 (première orientation)
            int[][] blockShape = new_block.getShape()[0];
            
            
            // Placer le bloc dans la grille
            for (int i = 0; i < blockShape.length; i++) {
                for (int j = 0; j < blockShape[i].length; j++) {
                    // Vérifier si la position du bloc est valide (dans les limites de la grille)
                    if (index_x + i < gridGame.length && index_y + j < gridGame[0].length) {
                        gridGame[index_x + i][index_y + j] = blockShape[i][j]; // Placer le bloc dans la grille
                    }
                }
            }

            return gridGame;
        }
    
  public int[][] rotationGrid() {
        
        index_rotation = (index_rotation + 1) % 4;
        
        int[][] NewBlockShape = new_block.getShape()[index_rotation];
        
        int[][] copyGridGame = copyGrid(gridGame);
        
        copyGridGame = clearBlockInGrid(copyGridGame);
       
        for (int i = 0; i < NewBlockShape.length; i++) {
                for (int j = 0; j < NewBlockShape[i].length; j++) {
                    // Vérifier si la position du bloc est valide (dans les limites de la grille)
                    if (index_x + i < copyGridGame.length && index_y + j < gridGame[0].length) {
                        copyGridGame[index_x + i][index_y + j] = NewBlockShape[i][j] + copyGridGame[index_x + i][index_y + j]; // Placer le bloc dans la grille
                    }
                }
            }
        
        if (hasCollision(gridGame)){System.out.println("collision detected");return gridGame;}
        
        return copyGridGame;
    }

  private boolean hasCollision(int[][] grid){
      
      for (int i = 0; i < grid.length; i++) {
          for (int j = 0; j < grid[i].length; j++) {
              if(grid[i][j] > 1){return true;}
          }
      }
      return false;
  }
  
  
   public int [][] clearBlockInGrid(int [][] grid){
       for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if(grid[i][j]==1){grid[i][j]=0;}
                }
            }
       return grid;
   }

// Helper method to print the gridGame (for debugging purposes)
private void printGrid(int[][] grid) {
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[i].length; j++) {
            System.out.print(grid[i][j] + " ");
        }
        System.out.println(); // Nouvelle ligne après chaque ligne de la grille
    }
}

// Helper method to print the block shape (for debugging purposes)
private void printBlockShape(int[][] blockShape) {
    for (int i = 0; i < blockShape.length; i++) {
        for (int j = 0; j < blockShape[i].length; j++) {
            System.out.print(blockShape[i][j] + " ");
        }
        System.out.println(); // Nouvelle ligne après chaque ligne du bloc
    }
}






// Helper method to create a copy of the gridGame
private int[][] copyGrid(int[][] grid) {
    int[][] newGrid = new int[grid.length][grid[0].length];
    for (int i = 0; i < grid.length; i++) {
        System.arraycopy(grid[i], 0, newGrid[i], 0, grid[i].length);
    }
    return newGrid;
}

   
        public int getStart_x() {
        return index_x;
    }

    public void setStart_x(int start_x) {
        this.index_x = start_x;
    }

    public int getStart_y() {
        return index_y;
    }

    public void setStart_y(int start_y) {
        this.index_y = start_y;
    }
        
    public AbstractBlock getNewBlock() {
        return new_block;
    }
  
}
