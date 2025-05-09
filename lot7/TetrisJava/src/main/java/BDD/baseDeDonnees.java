/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BDD;

import java.sql.*;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author SIO
 */
public class baseDeDonnees {

    static private String login;
    static private String mdp;
    CPlayer player;

   
   

    public void InitPLayer(ResultSet rs) {
        try {
            String prenom = rs.getString("prenom");
            String nom = rs.getString("nom");
            String pseudo = rs.getString("pseudo");
            String mail = rs.getString("email");
            int id = rs.getInt("iduser");
            player = new CPlayer(prenom, nom, pseudo, mail, id);

        } catch (Exception e) {
            System.err.println(e);
        }

    }



    
}
