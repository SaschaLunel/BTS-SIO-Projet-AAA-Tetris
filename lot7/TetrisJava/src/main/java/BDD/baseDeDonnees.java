/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BDD;

import java.sql.*;

/**
 *
 * @author SIO
 */
public class baseDeDonnees {

    static private String login;
    static private String mdp;
    static final String DB_URL = "jdbc:mysql://localhost/tetris_db"; // URL de la BDD
    static final String USER = "root"; // nom d'utilisateur
    static final String PASS = ""; // mot de passe
    Connection conn = null;
    Statement stmt = null;
    CPlayer player;

    public baseDeDonnees(String login, String mdp) {
        this.login = login;
        this.mdp = mdp;
        if (ConnecterBDD(login, mdp)) {
            System.err.println("connecter a la bdd");
        } else {
            System.err.println("Erreur de connection a la bdd");
        }
    }

    public baseDeDonnees() {
        insertNewScore(20, 1);
    }
    
    

    private boolean ConnecterBDD(String login, String mdp) {
        boolean Succes = false;
        return Succes;
    }

    public void insertNewScore(int score, int id) {
        try {
            OpenSession();
            String sql;
            sql = "INSERT INTO score (score, id) VALUES (" + score + ", " + id + ")";
            try {
                int result = stmt.executeUpdate(sql);
            } catch (Exception e) {
                System.err.println(e);
            }
            
            
            }
        catch (Exception ex) {
            
        }
            try {
            CloseSession();
        } catch (Exception e) {
        }
            
        
    }

    public boolean OpenSession() {

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
        return true;

    }

    public void CloseSession() {
        try {
            stmt.close();
        conn.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        
    }
    
    public void InitPLayer(ResultSet rs){
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
    public static void main(String[] args) {
        
        new baseDeDonnees();
        
    }
}
