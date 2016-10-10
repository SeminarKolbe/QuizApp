/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marin
 */
public class User extends DatenbankZugang {
    
    //Hole UserID anhand des Namen
    public int getUserIDByName(String name) throws SQLException, ClassNotFoundException{
        int userID;
        String query = "SELECT id_benutzer FROM benutzer WHERE name = '" + name + "';";
        userID = getInteger(query);
        return userID;
    }
    
    //Hole Username anhand der ID
    public String getUserNameByID(int userID){
        String name = "";
        String query = "SELECT name FROM benutzer WHERE id_benutzer = " + userID + ";";
        name = getString(query);
        return name;
    }
    
    // überprüft ob der User und Passwort in der Datenbank gespeichert sind
    public boolean isUserValid(String username, String password) throws ClassNotFoundException, SQLException{
        String querynames = "select name from benutzer;";
        String querypasswords = "select passwort from benutzer;";
        ArrayList<String> users = getStringList(querynames);
        ArrayList<String> passwords =getStringList(querypasswords);
        
        for(int i =0; i<users.size();i++){
            if(users.get(i).equals(username) && passwords.get(i).equals(password)){
                return true;   
            }   
        }            
        return false;
    }
}
