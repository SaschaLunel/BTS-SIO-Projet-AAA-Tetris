/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gameplay;

/**
 *
 * @author SIO
 */
public class TimerWidget {

   public static int minutes = 3;
   public static int secondes = 4;
    public TimerWidget() {
        
        
    }
    
    public boolean removeTime(){
        if (secondes<1){
            secondes=59;
            minutes-=1;
        }
        else {
            secondes-=1;
            if (secondes<1){
                return true;
            }
        }
        return false;
    }
    
}
