/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        connect();
        Statement stmt= con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from benutzer");
         
        while(rs.next()){
            
            if(rs.getString(2).equals(username) && rs.getString(3).equals(password)){
                return true;   
            }   
        }            
        return false;
    }


}

