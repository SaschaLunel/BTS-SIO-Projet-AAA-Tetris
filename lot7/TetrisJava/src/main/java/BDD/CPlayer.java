/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BDD;

/**
 *
 * @author SIO
 */
public class CPlayer {
    
    String id;
    String prenom;
    String nom;
    String pseudo;
    String mail;
    

    public CPlayer(String prenom, String nom, String pseudo, String mail ,String id) {
        this.prenom = prenom;
        this.nom = nom;
        this.pseudo = pseudo;
        this.mail = mail;
        
        this.id = id;
    }
    
    
}
