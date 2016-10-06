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
import java.util.List;

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
    
    public int getPlaysetSize(){
        return this.playset.size();
    }
    
    public String getPlaysetIDs(){
        String PlaysetIDs = "";
        for(int i=0; i < this.playset.size(); i++){
            PlaysetIDs += this.playset.get(i).getId() + "-";
        }
        System.out.println("PlaysetIDs mit letztem Bindestrich:" + PlaysetIDs);
        PlaysetIDs = PlaysetIDs.substring(0, PlaysetIDs.length()-1); //letztes Bindestrich entfernen
        
        System.out.print("PlaysetIDs:" + PlaysetIDs + "\n");
        return PlaysetIDs;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public Karteikarte(){
        
    }
    
    public Karteikarte(String thema, Player player, String playmode){
        this.thema=thema;
        this.player=player;
        this.playmode=playmode;
        setPlayset();
    }
   
    public void setPlayset(){
        try{     
            // Anzahl aller Fragen zu diesem Thema ermitteln in der DB
            String query = "SELECT COUNT(id_karte) FROM karten WHERE thema = '" + this.thema + "';";
            int maxfragen = getInteger(query);
            // Anzahl aller Fragen in der DB ermitteln
            query = "SELECT COUNT(id_karte) FROM karten;";
            int allefragen = getInteger(query);
            //wrongset= getDifficult(thema);  // Es wird eine Liste erzeugt, in der die ID's der Karten stehen, die der User oft falsch gemacht hat
            for(int i=0;i<fragenanzahl && i<maxfragen ;i++){
            //________________Einfügen der schweren Fragen_________________________________
                /*if(i==0 && maxfragen>= fragenanzahl){ // das einfügen der schweren Fragen soll nur einmal geschehen
                    Iterator schwer = wrongset.iterator();
                  
                    for(int j=0;j<schwerefragen && schwer.hasNext();j++){
                        playset.add(wrongset.get(j)); // Fügt die Frage und Antwort dem Set an Fragen hinzu, die dem Nutzer ausgegeben werden
                         i++;         // i ist die gesamtzahl an karten und muss, dadurch das eine schwere Karte hinzukommt, erhöt werden
                    }     
                }*/
            //_________Es wird anhand der iD überprüft, ob die Karte schon im Set enthalten ist ____________________________________
                
                int  randomid =(int) (allefragen*Math.random());    // randomid gibt eine zuffällige Id einer Karte an. 
                
                while(onSet(randomid) || randomid==0 || onThema(randomid) || onPlaymode(randomid)){
                    randomid =(int) (allefragen*Math.random());
                }
                //Frage dem playset hinzufügen
                query = "SELECT id_karte, frage, antwort1, antwort2, antwort3, antwort4, antwort5, richtigeAntworten FROM karten WHERE id_karte = "+randomid+";";  
                System.out.println("Bevor eine Frage hinzugefügt wird. query: " + query);
                ResultSet result = getResultSet(query);
                if(result.next()){
                    System.out.println("Resultset: " + result.getInt(1)+this.thema+result.getString(2)+result.getString(3)+result.getString(4)+result.getString(5)+result.getString(6)+result.getString(7)+result.getString(8));
                    this.playset.add(new Frage(result.getInt(1),this.thema,result.getString(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6),result.getString(7),result.getString(8)));
                }
                con.close();
                System.out.println("Nachdem eine Frage hinzugefügt wurde. Playset.siz() " + this.playset.size());
            }
             
        }catch(Exception e){
           System.out.println(e);
           System.out.println("Bin in der Exception");
        } 
    }
    //________________________________________________________________________________________-
    
    /* Guckt ob die ID schon in der Liste, der Karten die gespielt werden sollen, enthalten ist*/
    public boolean onSet(int id) throws Exception{
        Iterator <Frage> j =playset.iterator();
        while(j.hasNext()){
            if(id==j.next().getId()){    // wenn die id einer Frage mit der -id einer Frage im set übereinstimmt, wird true zurückgegeben
                return true;
            }               
        }
        return false; 
    }
    //__________________________________________________________________________________________________________
    public boolean onThema(int id) throws Exception{
        String query = "SELECT thema FROM karten WHERE id_karte = " + id + ";";
        System.out.println("query: " + query);
        String themafromrandomid = getString(query);
        if(!themafromrandomid.isEmpty()){
            if(themafromrandomid.equals(this.thema)) {
                return false;
            }
        }
        return true;
        /*Statement stmtt= con.createStatement();
        ResultSet rshelp1 = stmtt.executeQuery("SELECT * FROM karten WHERE id_karte = '"+id+"';"); 
        if(rshelp1.next()){
            if(rshelp1.getString(2).equals(thema)){
                return false;
            }     
        }
        return true;*/
    }
    
    public boolean onPlaymode(int id) throws Exception{
        String query = "SELECT fragetyp FROM karten WHERE id_karte = " + id + ";";
        String fragetypfromrandomid = getString(query);
        if(!fragetypfromrandomid.isEmpty()){
            if(fragetypfromrandomid.equals(this.playmode)) {
                System.out.println("fragetypfromrandomid: " + fragetypfromrandomid);
                return false;
            }
        }
        return true;
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

    public List<Frage> getDifficult(String thema) throws ClassNotFoundException, SQLException {     
        //Statement stmt2= con.createStatement();
        //Statement stmt3= con.createStatement(); 
        List<Frage> set = new ArrayList<Frage>();

        String query = "SELECT * FROM relation_benutzer_karten WHERE thema = '"+thema+"' AND id_benutzer = '"+player.getUser_id()+"';";
        ResultSet result1 = getResultSet(query);
        //ResultSet rf = stmt2.executeQuery("SELECT * FROM relation_benutzer_karten WHERE thema = '"+thema+"' AND id_benutzer = '"+player.getUser_id()+"';");
         //_------------------------------------________________
        System.out.println("Karteikarte getDifficult()");
        while(result1.next()){

            // Suche die Fragen, welche zu dem thema und welche die Karten-id des rs Resultset haben(Relation zum benutzer)
            query = "SELECT * FROM karten WHERE thema = '"+thema+"' AND id_karte = '"+result1.getInt(2)+"';";
            ResultSet result2 = getResultSet(query);
            //ResultSet rw = stmt3.executeQuery("SELECT * FROM karten WHERE thema = '"+thema+"' AND id_karte = '"+rf.getInt(2)+"';");

             if(result2.next()){

                set.add(new Frage(result1.getInt(2),thema,result2.getString(3),result2.getString(4),result2.getString(5),result2.getString(6),result2.getString(7),result2.getString(8),result2.getString(9),result1.getInt(5),result1.getInt(4),result1.getInt(3)));

             }   
        }

        //___________________________________________________________________________

        for(int i=0;i<set.size();i++){   // Liste wird sortiert   
            for(int j=i+1;j<set.size();j++){ 
              Frage help1= (Frage) set.get(i);    
              Frage help2= (Frage) set.get(j);
              double low = (double) help1.getMadewrong()/help1.getMaxgespielt();    // In spalte 4 steht die Anzahl, wie oft die Karte falsch gemacht wurde wurde. In spalte 5 wie oft sie gespielt wurde
              double high =(double) help2.getMadewrong()/help2.getMaxgespielt();
                if(low<high){   // Wenn die Karte oft falsch gemacht wurde, tausche Sie an die erste stelle der Liste 
                    set.set(i,help2);
                    set.set(j,help1);    
                }

            }  
        }
        return set;     
    }
            
    public void createNotecard(String thema, String question, String correctAnswer, int creatorID){
        String query = "INSERT INTO karten (thema,frage,antwort1,antwort2,antwort3,antwort4,antwort5,richtigeAntworten,punkte,fragetyp, hersteller) "
                + "VALUES ('" + thema + "', '" + question + "', '" + correctAnswer + "', \"\", \"\", \"\", \"\", '1-0-0-0-0', 10, 'single', " + creatorID + ");";
        System.out.println(query);
        insertQuery(query);
        //Ist das Thema nicht in der Thema-Tabelle vorhanden, füge es ein   - unschöne Lösung
        query = "SELECT id_thema FROM thema WHERE name = '" + thema + "';";
        if(getString(query).isEmpty()){
            query = "INSERT INTO thema (name) VALUES ('" + thema + "');";
            insertQuery(query);
        }
        System.out.println("Insert neues thema - query: " + query);
    }
 }