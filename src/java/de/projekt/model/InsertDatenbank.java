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
 */
public class InsertDatenbank extends DatenbankZugang {
    private int falsch=0;
    private int richtig=0;    // Wenn 1 hat er die Anwtort richtig gemacht, wenn 0 falsch
    private Frage frage;
    private Player player;
    
    public InsertDatenbank(int beantwortet, Frage frage, Player player ){
        if(beantwortet==1)
            richtig=1;
        else
            falsch=1;
        this.frage=frage;
        this.player=player;
    }
    
    
    public void insertDatenbankAnswer()throws ClassNotFoundException, SQLException {
        connect();
        Statement stmt= con.createStatement();
        Statement stmt2= con.createStatement();
       
        ResultSet rs = stmt2.executeQuery("SELECT * FROM relation_benutzer_karten WHERE id_benutzer = '"+player.getUser_id()+"' AND id_karte ='"+frage.getId()+"';");
        if(rs.next()){
           
            int right = richtig+rs.getInt(3);
            int wrong = falsch+rs.getInt(4);
            int all = rs.getInt(5)+1;
           
            stmt.executeUpdate("UPDATE relation_benutzer_karten SET richtig='"+right+"' WHERE id_benutzer="+player.getUser_id()+" AND id_karte="+frage.getId()+"");
            stmt.executeUpdate("UPDATE relation_benutzer_karten SET falsch='"+wrong+"' WHERE id_benutzer="+player.getUser_id()+" AND id_karte="+frage.getId()+"");
            stmt.executeUpdate("UPDATE relation_benutzer_karten SET gespielt='"+all+"' WHERE id_benutzer="+player.getUser_id()+" AND id_karte="+frage.getId()+"");
        }else{
  
            stmt.executeUpdate("INSERT INTO relation_benutzer_karten (id_benutzer, id_karte, richtig, falsch, gespielt, thema) VALUES ("+player.getUser_id()+", "+frage.getId()+","+richtig+","+falsch+", 1,'"+frage.getThema()+"')");
        }    
        close();    
           
    }
    
}
