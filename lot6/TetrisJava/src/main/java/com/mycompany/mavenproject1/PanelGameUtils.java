///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.mycompany.mavenproject1;
//
//import BlockFolder.AbstractBlock;
//import Gameplay.GridGame;
//
///**
// *
// * @author SIO
// */
//public class PanelGameUtils {
//
//    //Function qui permets de vérifé si le block en cours peut descendre 
//    protected static boolean canMoveBlock() {
//        return true;
//    }
//
//    
//
//
//
//
//
//    private void SuccesBlock() {
//        replaceOneAndTwoWithSeven(PanelGame.gridGameInstance.gridGame);
//        PanelGame.gridGameInstance.CreateNewBlock();
//    }
//
//    
//
//    
//
//    // Helper method to create a copy of the gridGame
//    private static int[][] copyGrid(int[][] grid) {
//        int[][] newGrid = new int[grid.length][grid[0].length];
//        for (int i = 0; i < grid.length; i++) {
//            System.arraycopy(grid[i], 0, newGrid[i], 0, grid[i].length);
//        }
//        return newGrid;
//    }
//
//    
//
//  
//
//    
//
//
//
//// Helper method to print the gridGame (for debugging purposes)
//    private void printGrid(int[][] grid) {
//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid[i].length; j++) {
//                System.out.print(grid[i][j] + " ");
//            }
//            System.out.println(); // Nouvelle ligne après chaque ligne de la grille
//        }
//    }
//
//// Fonction pour remplacer les 1 et les 2 par des 7 dans un tableau 2D
//    public static void replaceOneAndTwoWithSeven(int[][] grid) {
//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid[i].length; j++) {
//                if (grid[i][j] == 1 || grid[i][j] == 2) {
//                    grid[i][j] = 7; // Remplacer 1 ou 2 par 7
//                }
//            }
//        }
//    }
//
//}
