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
    
    public int getUserIDByName(String name) throws SQLException, ClassNotFoundException{
        int userID;
        String query = "SELECT id_benutzer FROM benutzer WHERE name = '" + name + "';";
        userID = getInteger(query);
        return userID;
    }
    
    public String getUserNameByID(int userID){
        String name = "";
        String query = "SELECT name FROM benutzer WHERE id_benutzer = " + userID + ";";
        name = getString(query);
        return name;
    }
    
    //Gibt für ein Thema die gesamtzahl an Karten zurück, die der Spieler jemals gespeilt hat
    public int getGespielteKarten(String thema, int id) throws ClassNotFoundException, SQLException{
        int all=0;
        connect();
        Statement stmt= con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT gespielt FROM relation_benutzer_karten WHERE thema='"+thema+"' AND id_benutzer='"+id+"';");
        while(rs.next()){
            all=all+rs.getInt("gespielt");    
        }
        close();
     return all;
    }
    // Gibt die Anzahl an richtig gespielten Karten, die der Nutzer jemals zu einem Thema gespielt hat aus.
    public int getRichtigGespielteKarten(String thema, int id) throws ClassNotFoundException, SQLException{
        int right=0;
        connect();
        Statement stmt= con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT richtig FROM relation_benutzer_karten WHERE thema='"+thema+"'AND id_benutzer='"+id+"';");
        while(rs.next()){
            right=right+rs.getInt("richtig");    
        }
         close();
     return right;
    }
}
