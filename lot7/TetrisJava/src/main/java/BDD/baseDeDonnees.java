/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BDD;

import java.sql.*;
import java.time.LocalDate;

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

    }

    private boolean ConnecterBDD(String login, String mdp) {
        boolean Succes = false;
        return Succes;
    }

    public void insertNewScore(int score, int id) {
        try {
            Connection conn = OpenSession();
            String sql = "INSERT INTO score (score, id) VALUES (?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, score);
            stmt.setInt(2, id);
            try {
                int result = stmt.executeUpdate(sql);
            } catch (Exception e) {
                System.err.println(e);
            }

        } catch (Exception ex) {

        } finally {
            
        }

    }

    public Connection OpenSession() {
            Connection conn;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.err.println("COnnecté");

        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
        return conn;

    }

   

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

    public boolean verifExistUser(String pseudo) throws SQLException {
    // Prepare the SQL query
    String sql = "SELECT iduser FROM users WHERE pseudo = ?";
    
    // Open database connection
    Connection conn = OpenSession();

    // Prepare the statement
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, pseudo); // index starts at 1

    // Execute the query
    ResultSet result = stmt.executeQuery();

    // Check if a result exists
    boolean exists = result.next();

    // Close resources
    result.close();
    stmt.close();

    return exists;
}


    public void createUser(String email, String mdp, String prenom, String nom, String pseudo, LocalDate dBirth) {
    try {
        // Open a connection/session
        Connection conn = OpenSession();
        
        

        // Prepare SQL INSERT statement
        String sql = "INSERT INTO User (email, mdp, prenom, nom, pseudo, dBirth) VALUES (?, ?, ?, ?, ?, ?)";
        
       
        
        // Use the connection from bdd to create a prepared statement
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setString(1, email);
        stmt.setString(2, mdp);
        stmt.setString(3, prenom);
        stmt.setString(4, nom);
        stmt.setString(5, pseudo);
        stmt.setDate(6, java.sql.Date.valueOf(dBirth));


        // Execute the update
        stmt.executeUpdate();

        // Close the statement
        stmt.close();

    } catch (SQLException e) {
        // Handle SQL exceptions
        e.printStackTrace();
    }
}
    public int getIdUser(String pseudo) throws SQLException{
        Connection conn = OpenSession();
        String sql = "SELECT iduser from users where pseudo = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, pseudo);
        ResultSet result = stmt.executeQuery();
        return result.getInt(1);
    }

    
}
