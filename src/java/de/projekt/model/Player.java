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
 * 
 */

public class Player extends DatenbankZugang {
    private String name = null;
    private int user_id;
    private List<Object[]> set; 
    private int points;
    private Frage question;
    
    //
    public int getPoints() {
        String query = "SELECT punkte FROM benutzer WHERE name='"+name+"';";
        this.points = getInteger(query);
        return points;
    }
    
    public Player(String name) throws ClassNotFoundException, SQLException{
        this.name=name;
        this.user_id = getIdPlayer(); 
        this.points = getPoints();
    }
    
    public Player(int playerid) throws SQLException{
        this.user_id = playerid;
        this.name = getPlayerName(playerid);
        this.points = getPoints();
        
    }
    public Player(String name, int points) throws ClassNotFoundException, SQLException{
        this.name=name;
        this.points=points;
        this.user_id = getIdPlayer();
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getUser_id(){
        return this.user_id;
    } 

    public int getIdPlayer() throws ClassNotFoundException, SQLException{
        String query = "SELECT id_benutzer FROM benutzer WHERE name='"+name+"';";
        this.user_id = getInteger(query);
        return user_id;
    }
    
    public String getPlayerName(int playerid)throws SQLException{
        String query = "SELECT name FROM benutzer WHERE id_benutzer =" + playerid + ";";
        return getString(query);
    }
    
    //Gibt eine Liste mit den Namen der Gegner zurück   
    public ArrayList<String> getOpponentsNames(){
        String query = "SELECT name FROM benutzer WHERE name <>'"+name+"' AND name <> 'admin';";
        ArrayList<String> opponentsnames = getStringList(query);
        return opponentsnames;
    } 
    
    //Gibt für ein Thema die Anzahl der jemals gespielten Karten zurück
    public int getPlayedCardsByTheman(String thema, int id) throws ClassNotFoundException, SQLException{
        String query = "SELECT SUM(played) FROM relation_benutzer_karten WHERE thema = '" + thema + "' AND id_benutzer = '" + id + "';";
        return getInteger(query);
    }
    
    //Gibt für ein Thema die Anzahl der richtig gespielten Karten zurück
    public int getRightPlayedCardsByThema(String thema, int id) throws ClassNotFoundException, SQLException{
        String query = "SELECT SUM(correct) FROM relation_benutzer_karten WHERE thema='" + thema + "'AND id_benutzer='" + id + "';";
        return getInteger(query);
    }
    
    //Gibt für ein Thema die Anzahl der falsch gespielten Karten zurück
    public int getWrongPlayedCardsByThema(String thema, int id) throws ClassNotFoundException, SQLException{
        String query = "SELECT SUM(wrong) FROM relation_benutzer_karten WHERE thema = '" + thema + "' AND id_benutzer = '" + id + "';";
        return getInteger(query);
    }
    
    //Abgegebene Antwort für Benutzer im Singleplayer
    public void setUserAnswer(int userAnswer, Frage question, Player player){
        int right, wrong, played;
        right = wrong = played = 0;
        if(userAnswer == 1){
            right++;
        } else {
            wrong++;
        }
        String query = "SELECT correct, wrong, played FROM relation_benutzer_karten WHERE id_benutzer = "+ player.getUser_id() + " AND id_karte = " + question.getId()+ ";";
        List<Object[]> ListResult = selectQuery(query);
        query = "SELECT ersteller FROM karten WHERE id_karte = " + question.getId() + ";";
        int questionCreatorID = getInteger(query); 
        if(ListResult.isEmpty()){
            query = "INSERT INTO relation_benutzer_karten (id_benutzer, id_karte, correct, wrong, played, thema, ersteller) VALUES (" + player.getUser_id() + ", " + question.getId() 
                    + "," + right + "," + wrong + ", 1,'" + question.getThema() + "', " + questionCreatorID + ")";
            insertQuery(query);
        } else {
            Object[] result = ListResult.get(0);
            right += (int)result[0];
            wrong += (int)result[1];
            played += (int)result[2] + 1;
            query = "UPDATE relation_benutzer_karten SET correct = " + right + ", wrong = " + wrong + ", played = " + played + " WHERE id_benutzer = " + player.getUser_id()
                    + " AND id_karte = " + question.getId() + ";";
            insertQuery(query);
        }
    }

    // erstellt eine Liste mit den Namen der Benutzer und sortiert diese nach Ihre Punktzahl. Der Benutzer mit den meisten Punkte steht an erster Stelle
    public List<Player> getBestPoints() throws SQLException, ClassNotFoundException{
        List<Player> list =new ArrayList<Player>();
        String query = "SELECT name, punkte FROM benutzer ORDER BY punkte DESC;";
        List<Object[]> ListResult = selectQuery(query);
        Object[] result = new Object[2];
        if(ListResult.size() > 0){
            for(int i = 0; i < ListResult.size(); i++){
            result = ListResult.get(i);
            list.add(new Player(result[0].toString(), (int)result[1]));
            }
        }
        return list;
    }
    
    //speicher Punkte in der DB für den Spieler
    public void setPointsforPlayer(int points){
        int afterPoints = points;
        String query = "SELECT punkte FROM benutzer WHERE id_benutzer = " + this.user_id + ";";
        afterPoints += getInteger(query); 
        query = "UPDATE benutzer SET punkte = " + afterPoints + " WHERE id_benutzer = " + this.user_id + ";";
        insertQuery(query);
    }
}

