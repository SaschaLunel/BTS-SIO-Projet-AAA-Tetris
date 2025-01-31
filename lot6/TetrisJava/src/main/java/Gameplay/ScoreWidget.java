/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gameplay;

/**
 *
 * @author SIO
 */
public class ScoreWidget {
    private static int currentScore;

    public ScoreWidget() {
        currentScore = 0;
    }
    
    public static void addScore(int valueAdd){
        currentScore += valueAdd;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public String getTextScore() {
        return "Score : " + currentScore;
    }
    
}
