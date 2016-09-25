/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonas
 */
public class Kategorie extends DatenbankZugang {
    
    
    public ArrayList<String> getKategorie(){
            String query = "SELECT name FROM thema;";
            ArrayList<String> kategorien = getStringList(query); 
        return kategorien; 
    }
    
     public ArrayList<Integer> getIdKategorien(){
            String query = "SELECT idthema FROM thema;";
            ArrayList<Integer> idkategorien = getIntegerList(query); 
        return idkategorien; 
    }
    
}
