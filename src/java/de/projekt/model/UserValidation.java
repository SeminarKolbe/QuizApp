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
import java.util.Iterator;

/**
 *
 * @author Jonas
 *
 *
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jonas
 */
public class UserValidation extends DatenbankZugang {

    // überprüft ob der User und Passwort in der Datenbank gespeichert sind
    public boolean isUserValid(String username, String password) throws ClassNotFoundException, SQLException{
        String querynames = "select name from benutzer";
        String querypasswords = "select passwort from benutzer";
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

