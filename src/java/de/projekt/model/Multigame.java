/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.model;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Marin
 * 
 */
public class Multigame extends DatenbankZugang implements Werte{
    private int multigameID;
    private Player player1;
    private Player player2;
    private String thema1round1;
    private String thema2round1;
    private String thema3round1;
    private String thema1round2;
    private String thema2round2;
    private String thema3round2;
    private String cardset1round1;
    private String cardset2round1;
    private String cardset3round1;
    private String cardset1round2;
    private String cardset2round2;
    private String cardset3round2;
    private int round;
    private int pointsforplayer1round1;
    private int pointsforplayer2round1;
    private int pointsforplayer1round2;
    private int pointsforplayer2round2;
    
    public Multigame() {
        
    }
    
    public Multigame(int multigameid) throws SQLException {
        this.multigameID = multigameid;
        String query = "SELECT player1, player2, thema1round1, thema2round1, thema3round1, thema1round2, thema2round2, thema3round2,"
                + "cardset1round1, cardset2round1, cardset3round1, cardset1round2, cardset2round2, cardset3round2,"
                + " round, pointsforplayer1round1, pointsforplayer2round1, pointsforplayer1round2, pointsforplayer2round2 FROM multigame WHERE multigameID = " + multigameid + " AND NOT round = 3;";
        ResultSet result = getResultSet(query);
        while(result.next()){
            this.player1 = new Player(result.getInt(1));
            this.player2 = new Player(result.getInt(2));
            this.thema1round1 = result.getString(3);
            this.thema2round1 = result.getString(4);
            this.thema3round1 = result.getString(5);
            this.thema1round2 = result.getString(6);
            this.thema2round2 = result.getString(7);
            this.thema3round2 = result.getString(8);
            this.cardset1round1 = result.getString(9);
            this.cardset2round1 = result.getString(10);
            this.cardset3round1 = result.getString(11);
            this.cardset1round2 = result.getString(12);
            this.cardset2round2 = result.getString(13);
            this.cardset3round2 = result.getString(14);
            this.round = result.getInt(15);
            this.pointsforplayer1round1 = result.getInt(16);
            this.pointsforplayer2round1 = result.getInt(17);
            this.pointsforplayer1round2 = result.getInt(18);
            this.pointsforplayer2round2 = result.getInt(19);
        }
        con.close();
    }
    
    public Multigame(Player player1, String player2Name) throws ClassNotFoundException, SQLException{
        this.player1 = player1;
        this.player2 = new Player(player2Name);
    }    

    //Spieleröffner setzt ein Multigame auf mit Kategorien für Gegner, diese werden mit den Spielern in der DB gespeichert. Phase 1 abgeschlossen
    public void setMultigameRequest(String chosenthema1, String chosenthema2, String chosenthema3) throws ClassNotFoundException, SQLException {
        this.thema1round1 = chosenthema1;
        this.thema2round1 = chosenthema2;
        this.thema3round1 = chosenthema3;
        this.cardset1round1 = new Karteikarte(chosenthema1, this.player2, "single").getPlaysetIDs();
        this.cardset2round1 = new Karteikarte(chosenthema2, this.player2, "single").getPlaysetIDs();
        this.cardset3round1 = new Karteikarte(chosenthema3, this.player2, "single").getPlaysetIDs();
        this.round = 0;        
        String query = "INSERT INTO multigame (player1, player2, thema1round1, thema2round1, thema3round1, cardset1round1, cardset2round1, cardset3round1, round)"
                + "VALUES (" + this.player1.getUser_id() + "," + this.player2.getUser_id() + ", '" + this.thema1round1 + "', '" + this.thema2round1
                + "', '" + this.thema3round1 + "', '" + this.cardset1round1 + "', '" + this.cardset2round1 
                + "', '" + this.cardset3round1 + "', 0);";
        System.out.println("Multigame query für setMultigameRequest: "+ query);
        insertQuery(query);
        query="SELECT multigameID FROM multigame WHERE player1=" + this.player1.getUser_id() + " AND player2=" + this.player2.getUser_id() + " AND round=0;";
        setMultigameID(getInteger(query));
        System.out.println("Multigame query für MultigameID setzen: " + query);
    }
    
    //Gegner wählt Kategorien für den Spieleröffner, diese werden hier in der DB gespeichert. Phase 2 abgeschlossen
    public void setMultigameResponse(String chosenthema1, String chosenthema2, String chosenthema3)throws ClassNotFoundException, SQLException {
        this.thema1round2 = chosenthema1;
        this.thema2round2 = chosenthema2;
        this.thema3round2 = chosenthema3;
        this.cardset1round2 = new Karteikarte(chosenthema1, this.player1, "single").getPlaysetIDs();
        this.cardset2round2 = new Karteikarte(chosenthema2, this.player1, "single").getPlaysetIDs();
        this.cardset3round2 = new Karteikarte(chosenthema3, this.player1, "single").getPlaysetIDs();
        this.round = 1;
        String query = "UPDATE multigame SET thema1round2 = '"+ this.thema1round2 + "', thema2round2 = '" + this.thema2round2 + "', thema3round2 = '" 
                + this.thema3round2 + "', cardset1round2 = '" + this.cardset1round2 + "', cardset2round2 = '" + this.cardset2round2
                + "', cardset3round2 = '" + this.cardset3round2 + "', round = " + this.round + " WHERE multigameID = " + this.multigameID + ";";
        System.out.println("query für setMultigameRespone: " + query);
        insertQuery(query);
    }
    
    public void setMultigameID(int multigameid) {
        this.multigameID = multigameid;
    }
    
    public int getMultigameID() {
        return this.multigameID;
    }
    
    public int getPlayer1ID() {
        return this.player1.getUser_id();
    }
    
    public int getPlayer2ID() {
        return this.player2.getUser_id();
    }
    
    public String getotherPlayerName(int playerid) {
        if(this.player1.getUser_id() == playerid)
            return this.player2.getName();
        if(this.player2.getUser_id() == playerid)
            return this.player1.getName();
        return "You´re no Player of this Multigame";
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
        return this.cardset1round1;
    }
    
    public String getcardset2round1(){
        return this.cardset2round1;
    }
    
    public String getcardset3round1(){
        return this.cardset3round1;
    }
    
    public String getcardset1round2(){
        return this.cardset1round2;
    }
    
    public String getcardset2round2(){
        return this.cardset2round2;
    }
    
    public String getcardset3round2(){
        return this.cardset3round2;
    }
    
    public int getRound(){
        return this.round;
    }
    
    //setzt die übermittelte Runde in dem Objekt sowie DB fest
    public void setRound(int Round){
        String query = "";
        query = "UPDATE multigame SET round = " + Round + " WHERE multigameID = " + this.multigameID + ";";
        insertQuery(query);
        this.round = Round;
        System.out.println("Multigame setRound query: " + query);
    }
    
    //Hole Frage zu einem Thema in einer Runde
    public String getQuestion(int questionID) {
        String query = "SELECT frage FROM karten WHERE id_karte = " + questionID + ";";
        String Frage = getString(query);
        return Frage;
    }
    
    //Hole Antwortmöglichkeiten zur Frage eines Themas in einer Runde
    public ArrayList<String> getAnswers(int questionID) throws SQLException, ClassNotFoundException {
        ArrayList<String> Answers = new ArrayList<String>();
        String query = "SELECT antwort1, antwort2, antwort3, antwort4, antwort5 FROM karten WHERE id_karte = " + questionID + ";";
        ResultSet result = getResultSet(query);
        if(result.next()){
            Answers.add(result.getString(1));
            Answers.add(result.getString(2));
            Answers.add(result.getString(3));
            Answers.add(result.getString(4));
            Answers.add(result.getString(5));
        }
        con.close();
        return Answers;
    }
    
    //Hole alle multigameIDs aus der DB für player
    public ArrayList<Integer> getGameRequests(int playerid, int round) {
        String query = "";
        switch(round) {
            case 1:
                query = "SELECT multigameID FROM multigame WHERE player2 = " + playerid + " AND round = " + round + ";";
                System.out.println("Multigame getAnfragen query: " + query);
                return getIntegerList(query);
            case 2:
                query = "SELECT multigameID FROM multigame WHERE player1 = " + playerid + " AND round = " + round + ";";
                System.out.println("Multigame getAnfragen query: " +query);
                return getIntegerList(query);
            default: throw new IllegalArgumentException("Invalid argument at Multigame.getAnswers()");
        }
    }
    
    //Wählt einen zufälligen Spieler aus
    public String getRandomPlayerName(int playerid){
        String query = "SELECT id_benutzer FROM benutzer WHERE id_benutzer <>" + playerid + " AND id_benutzer <> 1;";
        ArrayList<Integer> IntList = getIntegerList(query);
        int RandomPlayerID = IntList.get(new Random().nextInt(IntList.size()));
        query = "SELECT name FROM benutzer WHERE id_benutzer = " + RandomPlayerID + ";";
        return getString(query);
    }
    
    //Hole die richtige Antwort zur Frage eines Themas in einer Runde
    public int[] getCorrectAnswers(int questionID){
        String query = "SELECT richtigeAntworten FROM karten WHERE id_karte ="+ questionID + ";";
        int[] CorrectAnswers = IDsStringtoIntArray(getString(query));
        return CorrectAnswers;
    }
    
    //speichert die erreichten Punkte in der DB je nach Spieler
    public void setPointsforPlayer(Player player, int points) {
        String query = "";
        if(this.player1.getUser_id() == player.getUser_id()){
            if(this.round > 1){
                query = "UPDATE multigame SET pointsforplayer1round2 = " + points + " WHERE multigameID = " + this.multigameID + ";";
                insertQuery(query);
                this.pointsforplayer1round2 = points;
            } else {
                query = "UPDATE multigame SET pointsforplayer1round1 = " + points + " WHERE multigameID = " + this.multigameID + ";";
                insertQuery(query);
                this.pointsforplayer1round1 = points;
            }
        } else if(this.player2.getUser_id() == player.getUser_id()){
            if(this.round > 2){
                query = "UPDATE multigame SET pointsforplayer2round2 = " + points + " WHERE multigameID = " + this.multigameID + ";";
                insertQuery(query);
                this.pointsforplayer2round2 = points;
            } else {
                query = "UPDATE multigame SET pointsforplayer2round1 = " + points + " WHERE multigameID = " + this.multigameID + ";";
                insertQuery(query);
                this.pointsforplayer2round1 = points;
            }
        }
        System.out.println("setPointsforPlayer-query: " + query + "\tpoints: " + points + "\tplayer.getUser_id(): " + player.getUser_id());
    }
    
    //Hole Punkte für Player 1 für das Multigame
    public int getPointsforPlayer1(){
        String query = "";
        if(this.round > 1){
            query = "SELECT pointsforplayer1round2 FROM multigame WHERE player1 = " + this.player1.getUser_id() + " AND multigameID = " + this.multigameID + ";";
            this.pointsforplayer1round2 = getInteger(query);
            return this.pointsforplayer1round2;
        } else{
            query = "SELECT pointsforplayer1round1 FROM multigame WHERE player1 = " + this.player1.getUser_id() + " AND multigameID = " + this.multigameID + ";";
            this.pointsforplayer1round1 = getInteger(query);
            return this.pointsforplayer1round1;
        }
    }
    
    //Hole Punkte für Player 2 für das Multigame
    public int getPointsforPlayer2(){
        String query = "";
        if(this.round > 1){
            query = "SELECT pointsforplayer2round2 FROM multigame WHERE player1 = " + this.player2.getUser_id() + " AND multigameID = " + this.multigameID + ";";
            this.pointsforplayer2round2 = getInteger(query);
            return this.pointsforplayer2round2;
        } else{
            query = "SELECT pointsforplayer2round1 FROM multigame WHERE player1 = " + this.player2.getUser_id() + " AND multigameID = " + this.multigameID + ";";
            this.pointsforplayer2round1 = getInteger(query);
            return this.pointsforplayer2round1;
        }
    }     
    
    //Hilfsmethode um einen String in einen Integer-Array umzuwandeln für Fragenset und richtige Antwort
    public int[] IDsStringtoIntArray(String IDsString) {
        String[] playsetids = IDsString.split("-");
        int[] IDsIntArray = new int[playsetids.length];
        for(int i = 0; i < playsetids.length; i++){
            IDsIntArray[i] = Integer.parseInt(playsetids[i]);
        }
        return IDsIntArray;
    }
}
