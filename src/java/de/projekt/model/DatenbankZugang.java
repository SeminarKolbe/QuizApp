/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.model; 

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

/**
 *
 * @author Jonas
 * 
 */
public class DatenbankZugang {
    private static final String JDBC_TREIBER = "com.mysql.jdbc.Driver";
    private static final String DB_ADRESS = "localhost:3306";      // lokale DB-Adresse
    private static final String DB_NAME = "q";
    private static final String DB_USER = "streng";
    private static final String DB_PASSWORD = "geheim";
    
    private static final String DB_URL = "jdbc:mysql://" + DB_ADRESS + "/" + DB_NAME + "?user=" + DB_USER + "&password=" + DB_PASSWORD + "&useSSL=false";
    
    Connection con = null;   
    

    //Mit DB verbinden
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

    //Verbindung zur DB beenden
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
    
    /*
    *
    * Holt einen einzelnen String von der Datenbank
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
    * Holt eine Liste aus Strings von der Datenbank
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
    * Holt einen einzelnen Intwert von der Datenbank
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
    * Holt eine Liste aus Strings von der Datenbank
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
    
   
    // beliebige selectQueries können hiermit gestellt werden, gibt eine Liste an Object[] zurück, worin die verschiedenen Spaltenwerte gespeichert sind
    // Ein Objekt repräsentiert einen Datensatz
    public List<Object[]> selectQuery(String query) {

        List<Object[]> listResult = null;
        QueryRunner queryRunner = new QueryRunner();
        
        try {
            listResult = queryRunner.query(connect(), query, new ArrayListHandler());
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listResult;
    }      
}


    
    




