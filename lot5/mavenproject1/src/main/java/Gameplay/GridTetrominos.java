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
public class GridTetrominos {
    private int[][] grid;
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

    public GridTetrominos(int row, int column) {
        this.column = column;
        this.row = row;
        grid = new int[row][column];
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
             index_y = (int) (grid[0].length * 0.5f - new_block.getShape()[0].length * 0.5f); 
             index_x = 0;

            // Récupérer la forme du bloc pour l'orientation 0 (première orientation)
            int[][] blockShape = new_block.getShape()[0];
            
            
            // Placer le bloc dans la grille
            for (int i = 0; i < blockShape.length; i++) {
                for (int j = 0; j < blockShape[i].length; j++) {
                    // Vérifier si la position du bloc est valide (dans les limites de la grille)
                    if (index_x + i < grid.length && index_y + j < grid[0].length) {
                        grid[index_x + i][index_y + j] = blockShape[i][j]; // Placer le bloc dans la grille
                    }
                }
            }

            return grid;
        }
    
    public int[][] rotationGrid() {
        
        index_rotation = (index_rotation + 1) % 4;
        
        int[][] blockShape = new_block.getShape()[index_rotation];
        
        for (int i = 0; i < blockShape.length; i++) {
                for (int j = 0; j < blockShape[i].length; j++) {
                    // Vérifier si la position du bloc est valide (dans les limites de la grille)
                    if (index_x + i < grid.length && index_y + j < grid[0].length) {
                        grid[index_x + i][index_y + j] = blockShape[i][j]; // Placer le bloc dans la grille
                    }
                }
            }
        return grid;
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
