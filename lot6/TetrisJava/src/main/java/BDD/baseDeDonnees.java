/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BDD;

/**
 *
 * @author SIO
 */
public class baseDeDonnees {
    static private String login;
    static private String mdp;

    public baseDeDonnees(String login, String mdp) {
        this.login = login;
        this.mdp = mdp;
        if (ConnecterBDD(login, mdp)){
            System.err.println("connecter a la bdd");
        }
        else{
            System.err.println("Erreur de connection a la bdd");
        }
    }
    
    private boolean ConnecterBDD(String login, String mdp){
        boolean Succes = false;
        return Succes;
    }
    
}
