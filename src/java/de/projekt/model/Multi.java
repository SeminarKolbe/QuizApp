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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jonas
 */
public class Multi extends DatenbankZugang implements Werte{
    private Player player;  // Ich selber
    private String usernameGegner;
    private int idgegner;
    
    public Multi(Player player){
        this.player=player;
    }

    public Multi() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int[] getGame(int id) throws ClassNotFoundException, SQLException, Exception{
        int allidnutzer=0; // gibt die gesamtzahl der Nutzer an
        
        connect();
 //__________ Falls ein Random-Player gespielt wird __________________      
        // -1 es wurde ein randomplayer ausgesucht. 
        if(id==-1){
            Statement stmt3= con.createStatement();
            ResultSet rs3 = stmt3.executeQuery("SELECT * FROM benutzer");
            while(rs3.next()){
                allidnutzer++;
            }
            id=(int) (allidnutzer*Math.random());
            while(id==0 || player.getUser_id()==id){
               id=(int) (allidnutzer*Math.random()); // Damit keiner gegen den Admin spielt 
            }
        }
        this.idgegner=id;
//___________ Es wird ein Kartenset zusammengestellt_____________________        
        System.out.println("ES gibt insgesamt: "+allidnutzer);
        int allcards=0;
        String set="";
        
        Statement stmt2= con.createStatement();
        //ResultSet rs = stmt.executeQuery("INSERT INTO benutzer (match_id) VALUES("+id+")"); 
        
        ResultSet rs2 = stmt2.executeQuery("SELECT * FROM karten");
        while(rs2.next())
                allcards++;
        int[] numbercards = new int[anzahlmulticards];
        System.out.println("Hier 1");
        int help= (int) (allcards*Math.random());
        for(int i=0;i<anzahlmulticards;i++){
            System.out.println("Hier 2");
            while(onSet(numbercards, help)) // Fals die Karte schon im Set enthalten ist true
                help= (int) (allcards*Math.random()); // suche neue Karte
            numbercards[i]=help;
            System.out.println("Hier 3");
            set=set+help+"-";
            
        }    
         System.out.println("Das Kartenset ist: "+set);     
         set= (String) set;
//__________________Einfügen der Daten in die Datenbank____________________
            
            Statement stmt= con.createStatement();
            stmt.executeUpdate("INSERT INTO multi (player1, player2, cardset) VALUES("+player.getUser_id()+","+id+",'"+set+"')");
 
            close();
//_____ Ausgabe für den jetzigen Spieler_________________________
        return numbercards;

    }
    public int getGameId() throws SQLException, ClassNotFoundException{
        connect();
        Statement stmt= con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM multi");
        int id=-1;
        while(rs.next()){
            id=rs.getInt("lla");
        }
        close();
        return id;
    }
 
    
    public boolean onSet(int [] numbercards, int id) throws Exception{
         for(int i=0;i<anzahlmulticards;i++){  
            if(numbercards[i]==id){
                return true;
            }
         }
        return false;  
    }
    /*liefert den Namen des Spielers zurück, gegen den du spielst*/ 
    public String getUsername() throws SQLException, ClassNotFoundException{
        String h="random";
        connect();
        Statement stmt= con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT name FROM benutzer WHERE id_benutzer="+idgegner+"");
        if(rs.next()){
            h= rs.getString(1);
        }            
        return h;
    }
    // Legt auf die Datenbank die id des Spielers, der das Multigame gestartet und beendet hat
    public void insertErgebnis() throws ClassNotFoundException, SQLException{
        connect();
        Statement stmt= con.createStatement();
        stmt.executeUpdate("INSERT INTO multi (gespielt1) VALUES("+player.getUser_id()+")");            
       close();         
    }
    
    // gibt die Ganzen Anfragen für ein Multigame aus. Falls der Spieler im Multigame herausgefordert wurde
    // jede Anfrage hat eine eigene ID. Diese werden alle in einem int [] gespeichert
    public int [] checkAnfrage() throws ClassNotFoundException, SQLException{
        ArrayList<Integer> idliste =getIntegerList("SELECT lla FROM multi WHERE player2="+player.getUser_id()+";");
        int[] anfrageid=new int[idliste.size()];
        for(int i =0;i<=idliste.size();i++){
            anfrageid[i]=idliste.get(i);
        }
        /*
        Statement stmt= con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT lla FROM multi WHERE player2="+player.getId()+""); 
        System.out.println(rs);
        int anfragen=0;
        while(rs.next()){
            anfragen++;
        }
       
        ResultSet rs2 = stmt.executeQuery("SELECT lla FROM multi WHERE player2="+player.getId()+"");
        int i=0;
        while(rs2.next()){
           anfrageid[i]=rs2.getInt("lla");
           i++;
        }
        close();*/
        return anfrageid;
    }
    
   //Gibt den Gegner eines bestimmten Spiels zurück. WICHTIG= diese Methode wird vom Player2 aufgerufen und es muss deshalb nach Player 1 gesucht werden
    public String getPlayer(int idgame) throws ClassNotFoundException, SQLException{
        connect();
        Statement stmt= con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM multi WHERE lla="+idgame+"");
        String name="";
        String namegeg="";
        String cardset="";
        String idgegner="";
        Statement stmt1= con.createStatement();
        if(rs.next()){
            idgegner=rs.getString("player1");
            cardset= rs.getString("cardset");
        }

        ResultSet rs2 = stmt1.executeQuery("SELECT * FROM benutzer WHERE id_benutzer='"+idgegner+"'");
        if(rs2.next()){
            namegeg =rs2.getString("name");
        }
        name ="Anfrage von: "+namegeg+" will das spielen: "+cardset;
       close();
        return name;
    }
    
    public int [] getCardSet(String id) throws SQLException, ClassNotFoundException{
        connect();
        Statement stmt= con.createStatement();
        System.out.println("Bin for dem rs: id wurde als String übergeben");
        ResultSet rs = stmt.executeQuery("SELECT * FROM multi WHERE lla='"+id+"'");
        System.out.println("Bin for dem rs: id wurde als String übergeben/ hat geklappt");
        String cardset="";
        if(rs.next()){
         cardset=rs.getString("cardset");
        }
        System.out.println(cardset);
        return StringToInt(cardset);                                                            
    } 
    
    
    
    public int[] StringToInt(String cardset){
        int [] cardid=new int [anzahlmulticards];
        int w=0;
        String help="";
        for(int i=0;i<cardset.length();i++){
            if(cardset.charAt(i)!='-')
                help=help+cardset.charAt(i);         // ES wird ein STring erstellt mit nur der Zahl an der Stelle
            else{
                cardid[w]= Integer.parseInt(help);
                w++;
                help="";
            }
        }
        for(int i=0;i<anzahlmulticards;i++)
            System.out.println("Das ist der Array: "+cardid[i]);
    return cardid;    
    }        
    
     public void getResult1(int idgame, int right, int wrong) throws ClassNotFoundException, SQLException{
        connect();
        System.out.println("Multi.java / getResult11111");
        Statement stmt= con.createStatement();
        System.out.println("Bin vor der Select ausführung");
        ResultSet rs = stmt.executeQuery("SELECT * FROM multi WHERE lla="+idgame+"");
        System.out.println("Bin nach der SELECTAUSFÜRHUNG");
        int help=0;       
        String gespielt1="";
        if(rs.next()){
          gespielt1 =  rs.getString("gespielt1");   // WENN DER ANfragenende Spieler schon das cardset gespielt hat, wird eins zurückgegben, sonst 0
          System.out.println("Der Wert von gespielt 1 ist: "+gespielt1);
            // Wenn help eins 1 bedeutet das, dass der Spieler der gerade die Methode ausführt, spieler zwei ist. 
            Statement stmt1= con.createStatement();    
            stmt.executeUpdate("UPDATE multi SET richtig_spieler1="+right+" WHERE lla="+idgame+"");       //Richtigen Fragen des 2 Spielers
            stmt.executeUpdate("UPDATE multi SET falsch_spieler1="+wrong+" WHERE lla="+idgame+"");        // Falschen Fragen des 2 Spielers eintragen
            stmt.executeUpdate("UPDATE multi SET gespielt1=1 WHERE lla="+idgame+"");                      //eins wenn der Spieler das SPiel gespielt hat            
        close();
        }
    }
    
    
    
    
    public void getResult2(int idgame, int right, int wrong, String username) throws ClassNotFoundException, SQLException{
        connect();
        System.out.println("Multi.java / getResult2");
        Statement stmt= con.createStatement();
        System.out.println("Bin vor der Select ausführung");
        ResultSet rs = stmt.executeQuery("SELECT * FROM multi WHERE lla="+idgame+"");
        System.out.println("Bin nach der SELECTAUSFÜRHUNG");
        int help=0;       
        String gespielt1="";
        if(rs.next()){
          gespielt1 =  rs.getString("gespielt1");   // WENN DER ANfragenende Spieler schon das cardset gespielt hat, wird eins zurückgegben, sonst 0
          System.out.println("Der Wert von gespielt 1 ist: "+gespielt1);
            // Wenn help eins 1 bedeutet das, dass der Spieler der gerade die Methode ausführt, spieler zwei ist. 
            Statement stmt2= con.createStatement();
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM multi WHERE lla="+idgame+"");
            int gegnerid=0;
            if(rs2.next())
                gegnerid=rs2.getInt("player1");
            System.out.println("HIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIEEEER: "+gegnerid);
            Statement stmt1= con.createStatement();    
            stmt.executeUpdate("UPDATE multi SET fertig='1' WHERE lla="+idgame+"");
            stmt.executeUpdate("UPDATE multi SET spielerid_fertig_zum_sehen="+gegnerid+" WHERE lla="+idgame+"");
            stmt.executeUpdate("UPDATE multi SET richtig_spieler2="+right+" WHERE lla="+idgame+"");       //Richtigen Fragen des 2 Spielers
            stmt.executeUpdate("UPDATE multi SET falsch_spieler2="+wrong+" WHERE lla="+idgame+"");        // Falschen Fragen des 2 Spielers eintragen
            stmt.executeUpdate("UPDATE multi SET gespielt2=1 WHERE lla="+idgame+"");                      //eins wenn der Spieler das SPiel gespielt hat            
            stmt.executeUpdate("UPDATE multi SET name_spieler1='"+username+"' WHERE lla="+idgame+"");
         close();
        }
    }

    public int getRightPlayer1(int idgame) throws ClassNotFoundException, SQLException{
        connect();
        System.out.println("Multi.java / getRightPlayer1");
        Statement stmt= con.createStatement();
        System.out.println("Bin vor der Select ausführung");
        ResultSet rs = stmt.executeQuery("SELECT * FROM multi WHERE lla="+idgame+"");
        int richtigplayer1=0;
        if(rs.next()){
           richtigplayer1 = rs.getInt("richtig_spieler1");
        }
       close();
       System.out.println("Multi.java / getRightPlayer1: am ende");
       return richtigplayer1;
    }
    
     public int getRightPlayer2(int idgame) throws ClassNotFoundException, SQLException{
        connect();
        System.out.println("Multi.java / getRightPlayer2");
        Statement stmt= con.createStatement();
        System.out.println("Bin vor der Select ausführung");
        ResultSet rs = stmt.executeQuery("SELECT * FROM multi WHERE lla="+idgame+"");
        int richtigplayer2=0;
        if(rs.next()){
           richtigplayer2 = rs.getInt("richtig_spieler2");
        }
       close();
       System.out.println("Multi.java / getRightPlayer2: am ende");
       return richtigplayer2;
    }
    
    
    public int getWrongePlayer2(int idgame) throws ClassNotFoundException, SQLException{
        connect();
        System.out.println("Multi.java / getRightPlayer2");
        Statement stmt= con.createStatement();
        System.out.println("Bin vor der Select ausführung");
        ResultSet rs = stmt.executeQuery("SELECT * FROM multi WHERE lla="+idgame+"");
        int falschplayer2=0;
        if(rs.next()){
           falschplayer2 = rs.getInt("falsch_spieler2");
        }
       close();
       System.out.println("Multi.java / getRightPlayer2: am ende");
       return falschplayer2;
    }
    
    public int getWrongePlayer1(int idgame) throws ClassNotFoundException, SQLException{
        connect();
        System.out.println("Multi.java / getRightPlayer1");
        Statement stmt= con.createStatement();
        System.out.println("Bin vor der Select ausführung");
        ResultSet rs = stmt.executeQuery("SELECT * FROM multi WHERE lla="+idgame+"");
        int falschplayer1=0;
        if(rs.next()){
           falschplayer1 = rs.getInt("falsch_spieler1");
        }
       close();
       System.out.println("Multi.java / getRightPlayer1: am ende");
       return falschplayer1;
    }
    public String getNamePlayer2(int idgame) throws ClassNotFoundException, SQLException{
        connect();
        System.out.println("Multi.java / getGegnerName");
        Statement stmt= con.createStatement();
        System.out.println("Bin vor der Select ausführung");
        ResultSet rs = stmt.executeQuery("SELECT * FROM multi WHERE lla="+idgame+"");
        String gegnername="";
        if(rs.next()){
           gegnername = rs.getString("name_spieler2");
        }
       close();
       System.out.println("Multi.java / getRightPlayer2: am ende");
       return gegnername;
    }
    
    
    
    
    public String getNamePlayer1(int idgame) throws ClassNotFoundException, SQLException{
        connect();
        System.out.println("Multi.java / getGegnerName");
        Statement stmt= con.createStatement();
        System.out.println("Bin vor der Select ausführung");
        ResultSet rs = stmt.executeQuery("SELECT * FROM multi WHERE lla="+idgame+"");
        String gegnername="";
        if(rs.next()){
           gegnername = rs.getString("name_spieler1");
        }
       close();
       System.out.println("Multi.java / getRightPlayer1: am ende");
       return gegnername;
    }
    //Guckt ob ein Spiel komplett beendet wurde und ob ddas Spiel aus der Tabelle multi gelöscht werden kann
    public boolean getErgebnisAll(int idgame,String username ) throws ClassNotFoundException, SQLException{
        connect();
        System.out.println("Multi.java / getErgebnisAll "+username);
        Statement stmt1= con.createStatement();
        ResultSet rs2 = stmt1.executeQuery("SELECT * FROM benutzer WHERE name='"+username+"'");
        int playerid=-1;
        if(rs2.next()){
        System.out.println("Bin in der IF von rs2");
        rs2.getInt("id_benutzer");
            playerid=rs2.getInt("id_benutzer");
        }
        Statement stmt= con.createStatement();
        System.out.println("Bin vor der Select. die Id des Benutzers ist: "+ playerid);
        ResultSet rs = stmt.executeQuery("SELECT * FROM multi WHERE lla="+idgame+" AND spielerid_fertig_zum_sehen="+playerid+"");
        int fertig=3;
        if(rs.next()){
           System.out.println("ER GEHT IN DIE RS:NEXT");
           fertig= rs.getInt("fertig");
        }
        System.out.println("Fertig ist: "+ fertig);
        close();
        if(fertig == 1 ){     // Wenn es eins ist, wurde das Spiel von beiden Spieler gespielt. Es soll dem jetzigen Spieler nochmal angezeigt werdne und gelöscht werden
           System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHJJJJJJJJJJJJJJJJJJJJJJJJJAAAAAAAAAAAAAAAAA");

            return true;
        }    
        else 
            return false;
    }
    
    public void deletDb(int idgame) throws ClassNotFoundException, SQLException{
        String query = "DELETE FROM multi WHERE lla =" + idgame + ";";
        insertQuery(query);
    }
    
}
