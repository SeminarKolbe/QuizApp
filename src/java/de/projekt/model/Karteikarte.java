/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Jonas
 * 
 */
public class Karteikarte extends DatenbankZugang implements Werte {
      
    private List<Frage> playset =new ArrayList<Frage>(); 
    private List<Frage> wrongset = new ArrayList<Frage>();  // Eine (geordnete) Liste mit ResultSet Objekten, Referenzen auf die Fragen enthalten sind, die der User oft falsch gemacht hat      
    private Player player;
    private String thema;
    private String playmode;
    
    //gibt die Frage an einer bestimmten Position wieder
    public Frage getPlayset(int pos) {
        return playset.get(pos);
    }
    
    //Anzahl an Fragen im Kartenset
    public int getPlaysetSize(){
        return this.playset.size();
    }
    
    //KartenIDs der im Kartenset enthaltenen Frage erhalten im Format 'ID-ID-ID..'
    public String getPlaysetIDs(){
        String PlaysetIDs = "";
        for(int i=0; i < this.playset.size(); i++){
            PlaysetIDs += this.playset.get(i).getId() + "-";
        }
        PlaysetIDs = PlaysetIDs.substring(0, PlaysetIDs.length()-1); //letztes Bindestrich entfernen
        return PlaysetIDs;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public Karteikarte(){
        
    }
    
    public Karteikarte(String thema, Player player, String playmode){
        this.thema = thema;
        this.player = player;
        this.playmode = playmode;
        setPlayset();
    }
   
    //Kartenset erstellen
    public void setPlayset(){
        try{     
            // Anzahl aller Fragen zu diesem Thema ermitteln in der DB
            String query = "SELECT COUNT(id_karte) FROM karten WHERE thema = '" + this.thema + "';";
            int maxfragen = getInteger(query);
            // Anzahl aller Fragen in der DB ermitteln
            query = "SELECT COUNT(id_karte) FROM karten;";
            int allefragen = getInteger(query);
            wrongset= getDifficult(this.thema);  // Es wird eine Liste erzeugt, in der die ID's der Karten stehen, die der User oft falsch gemacht hat
            for(int i=0;i<fragenanzahl && i<maxfragen ;i++){
            //________________Einfügen der schweren Fragen_________________________________
                if(i==0 && maxfragen>= fragenanzahl){ // das einfügen der schweren Fragen soll nur einmal geschehen
                    for(int j=0;j<schwerefragen && j < wrongset.size();j++){
                        this.playset.add(wrongset.get(j)); // Fügt die Frage und Antwort dem Set an Fragen hinzu, die dem Nutzer ausgegeben werden
                        i++;         // i ist die gesamtzahl an karten und muss, dadurch das eine schwere Karte hinzukommt, erhöt werden
                    }     
                }
            //_______________Einfügen einer selbst erstellten und noch nicht gespielten Frage__________________________________________________
                if(this.playmode.equals("single") && i < fragenanzahl){
                    int ownQuestion = getOwnQuestion();
                    if(ownQuestion != 0 && !onSet(ownQuestion)){
                        query = "SELECT id_karte, frage, antwort1, antwort2, antwort3, antwort4, antwort5, richtigeAntworten FROM karten WHERE id_karte = " + ownQuestion + ";";
                        List<Object[]> ListResult = selectQuery(query);
                        Object[] result = ListResult.get(0);
                        if(result.length != 0){
                            this.playset.add(new Frage((int)result[0],this.thema,result[1].toString(),result[2].toString(),result[3].toString(),result[4].toString(),result[5].toString(),result[6].toString(),result[7].toString()));
                            i++;
                        }
                    }
                }
            //_________Es wird anhand der iD überprüft, ob die Karte schon im Set enthalten ist ____________________________________
                
                if(i < fragenanzahl){
                    int  randomid =(int) (allefragen*Math.random()); // randomid gibt eine zuffällige Id einer Karte an. 
                    if(this.playmode.equals("multi")){
                        while(onSet(randomid) || randomid==0 || onThema(randomid) || onPlaymode(randomid) || userCreated(randomid)){
                            randomid =(int) (allefragen*Math.random());
                        }
                    } else {
                        while(onSet(randomid) || randomid==0 || onThema(randomid) || onPlaymode(randomid)/* || ownQuestion(randomid)*/){
                            randomid =(int) (allefragen*Math.random());
                        }
                    }
                    //Frage dem Kartenset hinzufügen
                    query = "SELECT id_karte, frage, antwort1, antwort2, antwort3, antwort4, antwort5, richtigeAntworten FROM karten WHERE id_karte = "+randomid+";";
                    List<Object[]> ListResult = selectQuery(query);
                    Object[] result = ListResult.get(0);
                    if(result.length != 0){
                        this.playset.add(new Frage((int)result[0],this.thema,result[1].toString(),result[2].toString(),result[3].toString(),result[4].toString(),result[5].toString(),result[6].toString(),result[7].toString()));
                    }
                }
            }
             
        }catch(Exception e){
           System.out.println(e);
           System.out.println("Bin in der Exception");
        } 
    }
    
    /* Guckt ob die ID schon in der Liste, der Karten die gespielt werden sollen, enthalten ist*/
    public boolean onSet(int id) throws Exception{
        Iterator <Frage> j =playset.iterator();
        while(j.hasNext()){
            if(id==j.next().getId()){
                return true;
            }               
        }
        return false; 
    }
    
    //Karte noch für das gesucht Thema
    public boolean onThema(int cardID) throws Exception{
        String query = "SELECT thema FROM karten WHERE id_karte = " + cardID + ";";
        String themafromrandomid = getString(query);
        if(!themafromrandomid.isEmpty()){
            if(themafromrandomid.equals(this.thema)) {
                return false;
            }
        }
        return true;
    }
    
    //Karte noch für richtigen Spielmodus
    public boolean onPlaymode(int cardID) throws Exception{
        String query = "";
        if(this.playmode.equals("multi")){  //Vom Benutzer erstellte Karteikarten sollten nicht im Multigame auftauchen
            query = "SELECT fragetyp FROM karten WHERE id_karte = " + cardID + " AND ersteller <> " + this.player.getUser_id() + ";";
        }else query = "SELECT fragetyp FROM karten WHERE id_karte = " + cardID + ";";
        String questiontypefromrandomid = getString(query);
        if(!questiontypefromrandomid.isEmpty()){
            if(this.playmode.equals("multi")){ //Im Multigame ist egal, ob Multiple oder Single-Choice-Fragen
                return false;
            }else if(questiontypefromrandomid.equals(this.playmode)) {
                    return false;
                }
            }
        return true;
    }
    
    public boolean ownQuestion(int id){
        String query = "SELECT ersteller FROM karten WHERE id_karte = " + id + ";";
        int ersteller = getInteger(query);
        if(ersteller == this.player.getUser_id()){
            return false;
        } else return true;
    }
    
    //Überprüfung ob die Frage auch nicht von einem Benutzer kommt bei Kartenset für Multiplayerspiel
    public boolean userCreated(int id){
        String query = "SELECT ersteller FROM karten WHERE id_karte = " + id + ";";
        int ersteller = getInteger(query);
        if(ersteller == -1)
            return false;
        else return true;
    }
    
    public String getThema(){
        return this.thema;
    }
    
    public String getCorrectAnswer(int i){
        return this.playset.get(i).getCorrectAnswer();
    }
    
    public String getQuestion(int i){
         return this.playset.get(i).getQuestion();
    }
    
    //alle vom Spieler bespielten Fragen aus der DB holen und diese sortieren nach der am häufigsten falsch beantworteten
    public List<Frage> getDifficult(String thema) throws ClassNotFoundException, SQLException {
        List<Frage> set = new ArrayList<Frage>();

        
        String query = "SELECT id_karte, correct, wrong, played FROM relation_benutzer_karten WHERE thema = '"+thema+"' AND id_benutzer = '" + this.player.getUser_id() + "';";
        List<Object[]> ListResult1 = selectQuery(query);
        Object[] result1 = new Object[4];
         //_------------------------------------________________
        for(int i = 0; i < ListResult1.size(); i++){
            result1 = ListResult1.get(i);
            // Suche die Fragen, welche zu dem thema und welche die Karten-id des result1(Relation zum benutzer)
            query = "SELECT frage, antwort1, antwort2, antwort3, antwort4, antwort5, richtigeAntworten FROM karten WHERE thema = '" + thema + "' AND id_karte = " + (int)result1[0] + ";";
            Object[] result2 = selectQuery(query).get(0);

            if(result2.length > 0){

                set.add(new Frage((int)result1[0], thema, result2[0].toString(), result2[1].toString(), result2[2].toString(), result2[3].toString(), result2[4].toString(), result2[5].toString(), result2[6].toString(), (int)result1[1], (int)result1[2], (int)result1[3]));

             }   
        }


        //___________________________________________________________________________

        for(int i=0;i<set.size();i++){   // Liste wird sortiert   
            for(int j=i+1;j<set.size();j++){ 
                Frage help1= (Frage) set.get(i);    
                Frage help2= (Frage) set.get(j);
                double low = (double) help1.getMadewrong()/help1.getMaxgespielt();    
                double high =(double) help2.getMadewrong()/help2.getMaxgespielt();
                if(low<high){   // Wenn die Karte oft falsch gemacht wurde, tausche Sie an die erste stelle der Liste 
                    set.set(i,help2);
                    set.set(j,help1);    
                }
            }  
        }
        return set;     
    }
    
    //Suche nach einer selbsterstellten Karteikarte
    public int getOwnQuestion(){
        String query = "SELECT id_karte FROM karten WHERE thema = '" + this.thema + "' AND ersteller = " + this.player.getUser_id() + ";";
        ArrayList<Integer> ownQuestions = getIntegerList(query);
        query = "SELECT id_karte FROM relation_benutzer_karten WHERE thema = '" + this.thema + "' AND id_benutzer = " + this.player.getUser_id() + " AND ersteller = " + this.player.getUser_id() + ";";
        ArrayList<Integer> ownplayedQuestions = getIntegerList(query);
        List<Integer> getownQuestion = new ArrayList<Integer>();
        for(int i = 0; i < ownQuestions.size(); i++){
                if(!ownplayedQuestions.contains(ownQuestions.get(i))) //Wurde die selbst erstellte Karte noch nicht gespielt, wird sie in die Liste aufgenommen
                    getownQuestion.add(ownQuestions.get(i));
        }
        if(getownQuestion.isEmpty()){ //Liste leer heißt das der Benutzer bereits alle selbst erstellten Karten gespielt hat
            return 0;
        } else{
            return getownQuestion.get(new Random().nextInt(getownQuestion.size())); //Gebe eine zufällige ID einer noch nicht gespielten Karte zurück
        }
    }
            
    //Neue Karteikarte erstellen
    public void createNotecard(String thema, String question, String correctAnswer, int creatorID){
        String query = "INSERT INTO karten (thema,frage,antwort1,antwort2,antwort3,antwort4,antwort5,richtigeAntworten,fragetyp, ersteller) "
                + "VALUES ('" + thema + "', '" + question + "', '" + correctAnswer + "', \"\", \"\", \"\", \"\", '1-0-0-0-0', 'single', " + creatorID + ");";
        insertQuery(query);
        //Ist das Thema nicht in der Thema-Tabelle vorhanden, füge es ein
        query = "SELECT id_thema FROM thema WHERE name = '" + thema + "';";
        if(getString(query).isEmpty()){
            query = "INSERT INTO thema (name) VALUES ('" + thema + "');";
            insertQuery(query);
        }
    }
 }