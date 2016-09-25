/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.model;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Marin
 * 
 * Hauptproblem hier ist das zwei Benutzer auf diese Klasse zugreifen, d.h. sie benutzen nicht das gleiche Objekt!
 * mögliche Lösung ist der Zugriff auf die DB bei getter- und setter-Methoden
 */
public class Multigame extends DatenbankZugang implements Werte{
    private int multigameID;
    private final Player player1;
    private final Player player2;
    private String thema1round1;
    private String thema2round1;
    private String thema3round1;
    private String thema1round2;
    private String thema2round2;
    private String thema3round2;
    private Karteikarte cardset1round1;
    private Karteikarte cardset2round1;
    private Karteikarte cardset3round1;
    private Karteikarte cardset1round2;
    private Karteikarte cardset2round2;
    private Karteikarte cardset3round2;
    private int round;
    
    
    public Multigame(Player player1, String player2Name, String chosenthema1, String chosenthema2, String chosenthema3) throws ClassNotFoundException, SQLException {
        this.player1 = player1;
        this.player2 = new Player(player2Name);
        this.thema1round1 = chosenthema1;
        this.thema2round1 = chosenthema2;
        this.thema3round1 = chosenthema3;
        this.cardset1round1 = new Karteikarte(chosenthema1, player2);
        this.cardset2round1 = new Karteikarte(chosenthema2, player2);
        this.cardset3round1 = new Karteikarte(chosenthema3, player2);
        this.round = 1;
        setMultigameRequest();
        setMultigameID();
    }
    
    public Multigame(String player1Name, Player player2, String chosenthema1, String chosenthema2, String chosenthema3) throws ClassNotFoundException, SQLException {
        this.player1 = new Player(player1Name);
        this.player2 = player2;
        this.thema1round2 = chosenthema1;
        this.thema2round2 = chosenthema2;
        this.thema3round2 = chosenthema3;
        this.cardset1round2 = new Karteikarte(chosenthema1, this.player1);
        this.cardset2round2 = new Karteikarte(chosenthema2, this.player1);
        this.cardset3round2 = new Karteikarte(chosenthema3, this.player1);
        this.round = 2;
        setMultigameID();
        setMultigameResponse();
    }
    
    //Spieleröffner setzt ein Multigame auf mit Kategorien für Gegner, diese werden mit den Spielern in der DB gespeichert. Phase 1 abgeschlossen
    public void setMultigameRequest(){
        this.cardset1round1.setPlayset();
        this.cardset2round1.setPlayset();
        this.cardset3round1.setPlayset();
        String query = "INSERT INTO multigame (player1, player2, thema1forplayer2, thema2forplayer2, thema3forplayer3, cardset1round1, cardset2round1, cardset3round1)"
                + "VALUES (" + this.player1.getUser_id() + "," + this.player2.getUser_id() + ", '" + this.thema1round1 + "', '" + this.thema2round1
                + "', '" + this.thema3round1 + "', '" + this.cardset1round1.getPlaysetIDs() + "', '" + this.cardset2round1.getPlaysetIDs() 
                + "', '" + this.cardset3round1.getPlaysetIDs() + "');";
        System.out.println("query für setMultigameRequest: "+ query);
        insertQuery(query);
    }
    
    //Gegner wählt Kategorien für den Spieleröffner, diese werden hier in der DB gespeichert. Phase 2 abgeschlossen
    public void setMultigameResponse(){
        this.cardset1round2.setPlayset();
        this.cardset2round2.setPlayset();
        this.cardset3round2.setPlayset();
        String query2 = "UPDATE multigame SET thema1forplayer1 = '"+ this.thema1round2 + "', thema2forplayer1 = '" + this.thema2round2 + "', thema3forplayer1 = '" 
                + this.thema3round2 + "', cardset1round2 = '" + this.cardset1round2.getPlaysetIDs() + "', cardset2round2 = '" + this.cardset2round2.getPlaysetIDs()
                + "', cardset3round2 = '" + this.cardset3round2.getPlaysetIDs() + "', round = " + this.round + " WHERE multigameID = " + this.multigameID + ";";
        System.out.println("query für setMultigameRespone: " + query2);
        insertQuery(query2);
    }
    
    public void setMultigameID() {
        String query = "SELECT multigameID FROM multigame WHERE player1 = " + this.player1.getUser_id() + " AND player2 = " + this.player2.getUser_id() + ";";
        this.multigameID = getInteger(query);
    }
    
    public int getMultigameID() {
        return this.multigameID;
    }
    
    public String getthema1round1(){
        return this.thema1round1;
    }
    
    public String getthema2round1(){
        return this.thema2round1;
    }
    
    public String getthema3round1(){
        return this.thema3round1;
    }
    
    public String getthema1round2(){
        return this.thema1round2;
    }
    
    public String getthema2round2(){
        return this.thema2round2;
    }
    
    public String getthema3round2(){
        return this.thema3round2;
    }
    
    public String getcardset1round1(){
        return this.cardset1round1.getPlaysetIDs();
    }
    
    public String getcardset2round1(){
        return this.cardset2round1.getPlaysetIDs();
    }
    
    public String getcardset3round1(){
        return this.cardset3round1.getPlaysetIDs();
    }
    
    public String getcardset1round2(){
        return this.cardset1round2.getPlaysetIDs();
    }
    
    public String getcardset2round2(){
        return this.cardset2round2.getPlaysetIDs();
    }
    
    public String getcardset3round2(){
        return this.cardset3round2.getPlaysetIDs();
    }
    
    //Hole Frage zu einem Thema in einer Runde
    public String getQuestion(int questionID, String thema, int round) {
        switch(round) {
            case 1: 
                if(this.thema1round1.equals(thema))
                    return cardset1round1.getQuestion(questionID);
                if(this.thema2round1.equals(thema))
                    return cardset2round1.getQuestion(questionID);
                if(this.thema3round1.equals(thema))
                    return cardset3round1.getQuestion(questionID);
            case 2:
                if(this.thema1round2.equals(thema))
                    return cardset1round2.getQuestion(questionID);
                if(this.thema2round2.equals(thema))
                    return cardset2round2.getQuestion(questionID);
                if(this.thema3round2.equals(thema))
                    return cardset3round2.getQuestion(questionID);
            default: throw new IllegalArgumentException("Invalid argument at Multigame.getQuestion()");
        }
    }
    
    //Hole Antwortmöglichkeiten zur Frage eines Themas in einer Runde
    public ArrayList<String> getAnswers(int questionID, int round, String thema) {
        switch(round) {
            case 1:
                if(this.cardset1round1.getThema().equals(thema))
                    return cardset1round1.getAnswers(questionID);
                if(this.cardset2round1.getThema().equals(thema))
                    return cardset2round1.getAnswers(questionID);
                if(this.cardset3round1.getThema().equals(thema))
                    return cardset3round1.getAnswers(questionID);
            case 2:
                if(this.cardset1round2.getThema().equals(thema))
                    return cardset1round2.getAnswers(questionID);
                if(this.cardset2round2.getThema().equals(thema))
                    return cardset2round2.getAnswers(questionID);
                if(this.cardset3round2.getThema().equals(thema))
                    return cardset3round2.getAnswers(questionID);
            default: throw new IllegalArgumentException("Invalid argument at Multigame.getAnswers()");
        }
    }
    
    //Hole die richtige Antwort zur Frage eines Themas in einer Runde
    public String getCorrectAnswer(int questionID, int round, String thema){
                switch(round) {
            case 1:
                if(this.cardset1round1.getThema().equals(thema))
                    return cardset1round1.getCorrectAnswer(questionID);
                if(this.cardset2round1.getThema().equals(thema))
                    return cardset2round1.getCorrectAnswer(questionID);
                if(this.cardset3round1.getThema().equals(thema))
                    return cardset3round1.getCorrectAnswer(questionID);
            case 2:
                if(this.cardset1round2.getThema().equals(thema))
                    return cardset1round2.getCorrectAnswer(questionID);
                if(this.cardset2round2.getThema().equals(thema))
                    return cardset2round2.getCorrectAnswer(questionID);
                if(this.cardset3round2.getThema().equals(thema))
                    return cardset3round2.getCorrectAnswer(questionID);
            default: throw new IllegalArgumentException("Invalid argument at Multigame.getAnswers()");
        }
    }
    
    //speichert die erreichten Punkte in der DB je nach Spieler
    public void setPointsforMultigame(Player player, int points, int round) {
        String query = "";
        if(this.player1 == player) {
            if(round > 1){
                query = "SELECT pointsforplayer1 FROM multigame WHERE multigameID = " + this.multigameID + ";";
                points += getInteger(query);
            }
            query = "UPDATE multigame SET pointsplayer1 = " + points + " WHERE multigameID = " + this.multigameID + ";";
        }
        if(this.player2 == player){
            if(round > 1){
                query = "SELECT pointsforplayer1 FROM multigame WHERE multigameID = " + this.multigameID + ";";
                points += getInteger(query);
            }
            query = "UPDATE multigame SET pointsplayer2 = " + points + " WHERE multigameID = " + this.multigameID + ";";
        }
        insertQuery(query);
    }
    
    //Hole Punkte für Player für das Multigame
    public int getPointsforMultigame(Player player){
        String query = "";
        if(this.player1 == player) {
            query = "SELECT pointsforplayer1 FROM multigame WHERE player1 = " + player.getUser_id() + " AND multigameID = " + this.multigameID + ";";
        }
        if(this.player2 == player) {
            query = "SELECT pointsforplayer2 FROM multigame WHERE player2 = " + player.getUser_id() + " AND multigameID = " + this.multigameID + ";";
        }
        return getInteger(query);
    }
}
