/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BDD;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author thoma
 */
public class CRequeteSql {
    
    static final String DB_URL = "jdbc:mysql://localhost/tetris_db"; // URL de la BDD
    static final String USER = "root"; // nom d'utilisateur
    static final String PASS = ""; // mot de passe

    static public boolean verifExistUser(String pseudo) throws SQLException {
        // Prepare the SQL query
        String sql = "SELECT iduser FROM users WHERE pseudo = ?";

        // Open database connection
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

        System.err.println("COnnecté");

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
        conn.close();

        return exists;

    }

    public static CompletableFuture<Void> createUser(String email, String password, String prenom, String nom, String pseudo, LocalDate dBirth) {
        return CompletableFuture.runAsync(() -> {
            try {
                String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                // Open database connection
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

                // Prepare the SQL insert
                String sql = "INSERT INTO users (email, mdp, prenom, nom, pseudo, dBirth) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, email);
                stmt.setString(2, hashPassword);
                stmt.setString(3, prenom);
                stmt.setString(4, nom);
                stmt.setString(5, pseudo);
                stmt.setDate(6, Date.valueOf(dBirth));

                // Execute update
                stmt.executeUpdate();

                // Close resources
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    static public CompletableFuture<Integer> getIdUser(String pseudo) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Open database connection
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

                // Prepare SQL query
                String sql = "SELECT iduser FROM users WHERE pseudo = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, pseudo);

                // Execute query
                ResultSet result = stmt.executeQuery();

                // Check if user exists
                if (result.next()) {
                    int id = result.getInt("iduser"); // Use correct column name

                    // Clean up resources
                    result.close();
                    stmt.close();
                    conn.close();

                    return id;
                } else {
                    // Clean up resources
                    result.close();
                    stmt.close();
                    conn.close();

                    return -1; // Or throw an exception if needed
                }
            } catch (SQLException e) {
                throw new RuntimeException("Erreur lors de la récupération de l'id utilisateur", e);
            }
        });
    }

    public void insertNewScore(int score, int id) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
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
}
