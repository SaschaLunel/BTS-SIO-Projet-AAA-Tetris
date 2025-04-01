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
    static final String DB_URL = "jdbc:mysql://localhost/bddtest"; // URL de la BDD
    static final String USER = "username"; // nom d'utilisateur
    static final String PASS = "password"; // mot de passe
    Connection conn = null;
    Statement stmt = null;
    CPLayer player;

    public baseDeDonnees(String login, String mdp) {
        this.login = login;
        this.mdp = mdp;
        if (ConnecterBDD(login, mdp)) {
            System.err.println("connecter a la bdd");
        } else {
            System.err.println("Erreur de connection a la bdd");
        }
    }

    private boolean ConnecterBDD(String login, String mdp) {
        boolean Succes = false;
        return Succes;
    }

    public void insertNewScore(int score, String email) {
        try {
            OpenSession();
            String sql;
            sql = "INSERT INTO score {"+score+", idUser_User}";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) { // Etape 3: Extraction des données depuis le ResultSet
                int id = rs.getInt("id");
                int age = rs.getInt("age");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                System.out.print("ID: " + id);
                System.out.print(", Age: " + age);
                System.out.print(", Nom: " + nom);
                System.out.println(",Prenom: " + prenom);
            }
            rs.close(); // Etape 4 : Nettoyage du contexte
            CloseSession();
        } catch (SQLException se) { // Gestion des exceptions
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
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
        String prenom = rs.getString("prenom");
        String nom = rs.getString("nom");
        String pseudo = rs.getString("pseudo");
        String mail = rs.getString("mail");
        String id = rs.getString("id");
        player = new CPlayer(prenom, nom, pseudo, mail, id);
    }
}
