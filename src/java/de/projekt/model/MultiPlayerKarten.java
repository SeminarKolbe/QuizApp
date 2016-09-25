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
 * @author Jonas
 */
public class MultiPlayerKarten extends DatenbankZugang implements Werte{
    
    
    public List<Frage> generateGame(int [] numbercards) throws ClassNotFoundException, SQLException{
        connect();
        List<Frage> set = new ArrayList<Frage>();
        for(int i=0;i<anzahlmulticards;i++){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM karten WHERE id_karte="+numbercards[i]+"");
            if(rs.next()){
                
                Frage frage =new Frage(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString("antwort"), rs.getString("antwort2"), rs.getString("antwort3"), rs.getString("antwort4"), rs.getString("antwort5"), rs.getInt(9));
                
     
                set.add(frage);
            }  
        }
        
        return set;
    }
    
    
}
