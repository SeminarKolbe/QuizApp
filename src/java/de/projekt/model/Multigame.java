/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.model;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
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
    private int winnerID;
    
    public Multigame() {
        
    }
    
    //Ein multigame aus der DB holen anhand der multigameID
    public Multigame(int multigameid) throws SQLException {
        this.multigameID = multigameid;
        String query = "SELECT player1, player2, thema1round1, thema2round1, thema3round1, thema1round2, thema2round2, thema3round2,"
                + "cardset1round1, cardset2round1, cardset3round1, cardset1round2, cardset2round2, cardset3round2,"
                + " round, pointsforplayer1round1, pointsforplayer2round1, pointsforplayer1round2, pointsforplayer2round2 FROM multigame WHERE multigameID = " + multigameid + " AND NOT round = 3;";
        List<Object[]> ListResult = selectQuery(query);
        Object[] result = ListResult.get(0);
        if(result.length != 0){
            this.player1 = new Player((int) result[0]);
            this.player2 = new Player((int) result[1]);
            this.thema1round1 = result[2].toString();
            this.thema2round1 = result[3].toString();
            this.thema3round1 = result[4].toString();
            this.thema1round2 = result[5].toString();
            this.thema2round2 = result[6].toString();
            this.thema3round2 = result[7].toString();
            this.cardset1round1 = result[8].toString();
            this.cardset2round1 = result[9].toString();
            this.cardset3round1 = result[10].toString();
            this.cardset1round2 = result[11].toString();
            this.cardset2round2 = result[12].toString();
            this.cardset3round2 = result[13].toString();
            this.round = (int) result[14];
            this.pointsforplayer1round1 = (int) result[15];
            this.pointsforplayer2round1 = (int) result[16];
            this.pointsforplayer1round2 = (int) result[17];
            this.pointsforplayer2round2 = (int) result[18];
        }
    }
    
    public Multigame(Player player1, String player2Name) throws ClassNotFoundException, SQLException{
        this.player1 = player1;
        this.player2 = new Player(player2Name);
    }    

    //Spieleröffner erstellt ein Multigame mit Kategorien für Gegner, diese werden mit den Spielern in der DB gespeichert. 
    public void setMultigameRequest(String chosenthema1, String chosenthema2, String chosenthema3) throws ClassNotFoundException, SQLException {
        this.thema1round1 = chosenthema1;
        this.thema2round1 = chosenthema2;
        this.thema3round1 = chosenthema3;
        this.cardset1round1 = new Karteikarte(chosenthema1, this.player2, "multi").getPlaysetIDs(); //Karteikarten/Fragen werden ausgewählt, welche der Gegner oft falsch macht
        this.cardset2round1 = new Karteikarte(chosenthema2, this.player2, "multi").getPlaysetIDs();
        this.cardset3round1 = new Karteikarte(chosenthema3, this.player2, "multi").getPlaysetIDs();
        this.round = 0;   //Multigame befindet sich im Anfangszustand, d.h. keine Punkte vergeben und keine Karten gespielt
        String query = "INSERT INTO multigame (player1, player2, thema1round1, thema2round1, thema3round1, cardset1round1, cardset2round1, cardset3round1, round)"
                + "VALUES (" + this.player1.getUser_id() + "," + this.player2.getUser_id() + ", '" + this.thema1round1 + "', '" + this.thema2round1
                + "', '" + this.thema3round1 + "', '" + this.cardset1round1 + "', '" + this.cardset2round1 
                + "', '" + this.cardset3round1 + "', 0);";
        insertQuery(query);
        query = "SELECT multigameID FROM multigame WHERE player1=" + this.player1.getUser_id() + " AND player2=" + this.player2.getUser_id() + " AND round=0;";
        setMultigameID(getInteger(query));
    }
    
    //Gegner wählt Kategorien für den Spieleröffner, diese werden hier in der DB gespeichert. 
    public void setMultigameResponse(String chosenthema1, String chosenthema2, String chosenthema3)throws ClassNotFoundException, SQLException {
        this.thema1round2 = chosenthema1;
        this.thema2round2 = chosenthema2;
        this.thema3round2 = chosenthema3;
        this.cardset1round2 = new Karteikarte(chosenthema1, this.player1, "multi").getPlaysetIDs(); //Karteikarten/Fragen werden ausgewählt, welche der Spieleröffner oft falsch macht
        this.cardset2round2 = new Karteikarte(chosenthema2, this.player1, "multi").getPlaysetIDs();
        this.cardset3round2 = new Karteikarte(chosenthema3, this.player1, "multi").getPlaysetIDs();
        this.round = 1;   //Multigame befindet sich noch in der ersten Phase, d.h. Punkte für Player 1 sind gespeichert aber Player hat noch keine Punkte bzw. keine Karten der Runde 1 gespielt
        String query = "UPDATE multigame SET thema1round2 = '"+ this.thema1round2 + "', thema2round2 = '" + this.thema2round2 + "', thema3round2 = '" 
                + this.thema3round2 + "', cardset1round2 = '" + this.cardset1round2 + "', cardset2round2 = '" + this.cardset2round2
                + "', cardset3round2 = '" + this.cardset3round2 + "', round = " + this.round + " WHERE multigameID = " + this.multigameID + ";";
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
        return "You´re not a Player of this Multigame";
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
    
    //spiechert die erreichten Punkte für jede Runde abhängig vom Player sowohl im Objekt als auch in DB
    public void setPointsPlayer1Round1(int points){
        String query = "UPDATE multigame SET pointsforplayer1round1 = " + points + " WHERE multigameID = " + this.multigameID + ";";
        insertQuery(query);
        this.pointsforplayer1round1 = points;
    }
    
    public void setPointsPlayer1Round2(int points){
        String query = "UPDATE multigame SET pointsforplayer1round2 = " + points + " WHERE multigameID = " + this.multigameID + ";";
        insertQuery(query);
        this.pointsforplayer1round2 = points;
    }
    
    public void setPointsPlayer2Round1(int points){
        String query = "UPDATE multigame SET pointsforplayer2round1 = " + points + " WHERE multigameID = " + this.multigameID + ";";
        insertQuery(query);
        this.pointsforplayer2round1 = points;
    }
    
    public void setPointsPlayer2Round2(int points){
        String query = "UPDATE multigame SET pointsforplayer2round2 = " + points + " WHERE multigameID = " + this.multigameID + ";";
        insertQuery(query);
        this.pointsforplayer2round2 = points;
    }
    
    //setzt die übermittelte Runde in dem Objekt sowie DB fest
    public void setRound(int Round){
        String query = "";
        query = "UPDATE multigame SET round = " + Round + " WHERE multigameID = " + this.multigameID + ";";
        insertQuery(query);
        this.round = Round;
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
        List<Object[]> ListResult = selectQuery(query);
        Object[] result = ListResult.get(0);
        if(result.length != 0){
            Answers.add(result[0].toString());
            Answers.add(result[1].toString());
            Answers.add(result[2].toString());
            Answers.add(result[3].toString());
            Answers.add(result[4].toString());
        }
        return Answers;
    }
    
    //Hole alle multigameIDs aus der DB für player
    public ArrayList<Integer> getGameRequests(int playerid, int round) {
        String query = "";
        switch(round) {
            case 1:
                query = "SELECT multigameID FROM multigame WHERE player2 = " + playerid + " AND round = " + round + ";";
                return getIntegerList(query);
            case 2:
                query = "SELECT multigameID FROM multigame WHERE player1 = " + playerid + " AND round = " + round + ";";
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
        int[] CorrectAnswers = Helper.IDsStringtoIntArray(getString(query));
        return CorrectAnswers;
    }
    
    //Hole Punkte für Player 1 für das Multigame
    public int getPointsPlayer1Round1(){
        return this.pointsforplayer1round1;
    }
    
    public int getPointsPlayer1Round2(){
        return this.pointsforplayer1round2;
    }
    
    //Hole Punkte für Player2 für das Multigame
    public int getPointsPlayer2Round1(){
        return this.pointsforplayer2round1;
    }
    
    public int getPointsPlayer2Round2(){
        return this.pointsforplayer2round2;
    }
    
    /*
    angekreuzte Antworten des Benutzers werden mit den richtigen Antworten in der DB verglichen:
    richtige Antwort angekreuzt: true; falsche Antwort angekreuzt: false; richtige Antwort nicht angekreuzt: false; falsche Antwort nicht angekreuzt:true
    */
    public ArrayList<Boolean> checkAnswers(ArrayList<String> useranswers, int[] CorrectAnswer){
        ArrayList<Boolean> correctedAnswers = new ArrayList<Boolean>();
        for(int i = 0; i < 5; i++){
            correctedAnswers.add(i, null);
        }
        // Es wird geguckt, ob alle angekreuzten Antworten richtig sind 
        if(!useranswers.get(0).equals("null")){
            for(int f=0;f<useranswers.size();f++){
                switch(useranswers.get(f)){ 
                    case "a1": 
                        if(CorrectAnswer[0] == 1) correctedAnswers.set(0, true);
                        else if(CorrectAnswer[0] == 0) correctedAnswers.set(0, false);
                        break;
                    case "a2": 
                        if(CorrectAnswer[1] == 1) correctedAnswers.set(1, true);
                        else if(CorrectAnswer[1] == 0) correctedAnswers.set(1, false);
                        break;
                    case "a3": 
                        if(CorrectAnswer[2] == 1) correctedAnswers.set(2, true);
                        else if(CorrectAnswer[2] == 0) correctedAnswers.set(2, false);
                        break;
                    case "a4": 
                        if(CorrectAnswer[3] == 1) correctedAnswers.set(3, true);
                        else if(CorrectAnswer[3] == 0) correctedAnswers.set(3, false);
                        break;
                    case "a5": 
                        if(CorrectAnswer[4] == 1) correctedAnswers.set(4, true);
                        else if(CorrectAnswer[4] == 0) correctedAnswers.set(4, false);
                        break;
                }
            }
        }
        //Überprüfung, ob alle nicht angekreuzten Antworten auch falsch sind. Ja: true  Nein: false
        for(int f = 0; f < correctedAnswers.size(); f++){
            if(correctedAnswers.get(f) == null && CorrectAnswer[f] == 0){
                correctedAnswers.set(f, true);
            } else if(correctedAnswers.get(f) == null && CorrectAnswer[f] == 1) {
                correctedAnswers.set(f, false);
            }
        }
        return correctedAnswers;
    }
    
    //Antworten des Benutzers auswerten: richtige Antwort = 5 Punkte ; falsche Antwort = 0 Punkte
    public int getAchievedPointsForThisQuestion(ArrayList<Boolean> correctedAnswers, int pointsBefore){
        int pointsAfter = pointsBefore;
        for(int i = 0; i < correctedAnswers.size(); i++){
            if(correctedAnswers.get(i)){
                pointsAfter += 5;
            }
        }
        return pointsAfter;
    }
    
    //Gewinner des Multigames speichern in DB und Objekt
    public void setWinnerID(int WinnerID){
        this.winnerID = WinnerID;
        String query = "UPDATE multigame SET winnerID = " + WinnerID + " WHERE multigameID = " + this.multigameID + ";";
        insertQuery(query);
    }
    
    //Punkte für die Spieler auf der DB abspeichern
    public void setPointsforPlayers(){
        int winnerBonus = 100; //Bonus für Gewinner
        if(this.winnerID == getPlayer1ID()){ // Gewinner Spieler 1
            this.player1.setPointsforPlayer(getPointsPlayer1Round1()+getPointsPlayer1Round2()+winnerBonus);
            this.player2.setPointsforPlayer(getPointsPlayer2Round1()+getPointsPlayer2Round2());
        } else if(this.winnerID == getPlayer2ID()){ //Gewinner Spieler 2
            this.player2.setPointsforPlayer(getPointsPlayer2Round1()+getPointsPlayer2Round2()+winnerBonus);
            this.player1.setPointsforPlayer(getPointsPlayer1Round1()+getPointsPlayer1Round2());
        } else{ //  unentschieden
            this.player1.setPointsforPlayer(getPointsPlayer1Round1()+getPointsPlayer1Round2());
            this.player2.setPointsforPlayer(getPointsPlayer2Round1()+getPointsPlayer2Round2());
        }
    }
}
