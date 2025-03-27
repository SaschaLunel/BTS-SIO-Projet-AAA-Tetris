/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Gameplay;

/**
 *
 * @author SIO
 */
public class ScoreWidget {
    private static int currentScore;

    static String getScore() {
        return ""+currentScore;
    }

    static void stopGame() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

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
