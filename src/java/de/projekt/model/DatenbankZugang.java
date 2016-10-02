/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.model; 

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonas
 */
public class DatenbankZugang {
    private static final String JDBC_TREIBER = "com.mysql.jdbc.Driver";
    private static final String DB_ADRESS = "localhost:3306";      // lokale DB-Adresse
    private static final String DB_NAME = "q";
    private static final String DB_USER = "streng";
    private static final String DB_PASSWORD = "geheim";
    
    private static final String DB_URL = "jdbc:mysql://" + DB_ADRESS + "/" + DB_NAME + "?user=" + DB_USER + "&password=" + DB_PASSWORD + "&useSSL=false";
    
    Connection con = null;   
    
    /**
     * Gives Connection to database
     * 
     * @return Connection to database
     */
    public Connection connect() throws SQLException {
        try {
            Class.forName(JDBC_TREIBER);            
            con = DriverManager.getConnection(DB_URL);            
        } catch (SQLException e) {
            System.out.println("Verbindung nicht moglich");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        }
        
        return con;
    }

    public void close() {
        if (con != null) {
            try {
                con.close();
            } catch (Exception ex) {
                System.err.println("Exception beim Versuch, die Verbindung zu schließen:");
                System.err.println(ex);
            }
        }
    }
//___________________________________________________________________________________
    
    /* Holt einen einzelnen String von der Datenbank
    *
    */
    public String getString(String query){
        String result = "";
        try{
            connect();
            Statement stmt = con.createStatement();
            ResultSet rs =stmt.executeQuery(query);
            if(rs.next()) {
                result = rs.getString(1);
            }
            con.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }
    /*
    Holt eine Liste aus Strings von der Datenbank
    */
    public ArrayList<String> getStringList(String query){
        ArrayList<String> result = new ArrayList<String>();
        try{
            connect();
            Statement stmt =con.createStatement();
            ResultSet rs =stmt.executeQuery(query);
            while(rs.next()){
                result.add(rs.getString(1));
            }
            con.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }
    /*
    Holt einen einzelnen Intwert von der Datenbank
    */
     public int getInteger(String query){
        int result = 0;
        try{
            connect();
            Statement stmt =con.createStatement();
            ResultSet rs =stmt.executeQuery(query);
            if(rs.next()) {
                result = rs.getInt(1);
            }
            con.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }
    /*
    Holt eine Liste aus Strings von der Datenbank
    */
    public ArrayList<Integer> getIntegerList(String query){
        ArrayList<Integer> result = new ArrayList<Integer>();
        try{
            connect();
            Statement stmt =con.createStatement();
            ResultSet rs =stmt.executeQuery(query);
            while(rs.next()){
                result.add(rs.getInt(1));
            }
            con.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }
    
    //für insert- UND update- UND delete-SQL-Befehle
    public void insertQuery(String query) {
        try {
            connect();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    //Anstelle eines ResultSet hier eine getFrage-Methode einfügen
    public ResultSet getResultSet(String query) {
        ResultSet result = null;
        try{
            connect();
            Statement stmt = con.createStatement();
            result = stmt.executeQuery(query);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
  //_________________________________________________________________  
    
    
    
    
    
    //Gibt eine String Liste mit allen Nutzern zurück
    public List<String> getUser() throws SQLException, ClassNotFoundException{
        List <String> name =new ArrayList<String>();
        connect();
        Statement stmt= con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM benutzer;");
        while(rs.next()){
           name.add(rs.getString(2));
        }
        con.close();
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
        con.close();
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
         con.close();
     return right;
    }

    // Gibt eine Liste mit allen aktiven themen zurück
    public List<String> getThemaAll() throws ClassNotFoundException, SQLException{
        List<String> list = new ArrayList<String>();
        connect();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT name FROM thema;");
        while (rs.next()) {
            list.add(rs.getString("name"));
        
        }
        con.close();
        return list;
    }
    
    // Gibt die Anzahel an falsch gespielten Karten eine Users zu einem Thema aus
    public int getFalschGespielteKarten(String thema, int id) throws ClassNotFoundException, SQLException{
        int falsch =0;
        connect();
        Statement stmt= con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT falsch FROM relation_benutzer_karten WHERE thema='"+thema+"'AND id_benutzer='"+id+"';");
        while(rs.next()){
            falsch=falsch+rs.getInt("falsch");    
        }
         con.close(); 
        return falsch;
    }
        
    //legt nach einem Multiplayerspiel die Punkte auf die Datenbank

    public void setPoints(String username,int right) throws SQLException, ClassNotFoundException{
        int points=10*right;
        connect();
        Statement stmt= con.createStatement();
        Statement stmt2= con.createStatement();
        ResultSet rs = stmt2.executeQuery("SELECT punkte FROM benutzer WHERE name='"+username+"';");
        if(rs.next()){
            points=points+rs.getInt("punkte");
        }
        stmt.executeUpdate("UPDATE benutzer SET punkte="+points+" WHERE name='"+username+"'");
        con.close();
        return;
    }
     
    // erstellt eine Liste mit den Namen der User und sortiert diese nach Ihre Punktzahl. Der größet wird als erste darngehangen
    public List<Player> getBestPoints() throws ClassNotFoundException, SQLException{
       List<Player> list =new ArrayList<Player>();
       connect(); 
       Statement stmt2= con.createStatement();
       ResultSet rs = stmt2.executeQuery("SELECT * FROM benutzer ORDER BY punkte DESC");
       while(rs.next()){
         list.add(new Player(rs.getString("name"),rs.getInt("punkte")));
       }
       con.close();
       return list;
    }
        
}


    
    




