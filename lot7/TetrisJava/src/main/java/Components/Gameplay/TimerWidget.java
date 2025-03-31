/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Gameplay;

/**
 *
 * @author SIO
 */
public class TimerWidget {

   public static int minutes = 0;
   public static int secondes = 0;
    public TimerWidget() {
        
        
    }
    
    public void removeTime(){
        if (secondes>59){
            secondes=0;
            minutes+=1;
        }
        else {
            secondes+=1;
        }
    }
    
}
