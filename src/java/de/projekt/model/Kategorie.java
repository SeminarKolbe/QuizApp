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
    
    //Hole Name der Kategorien
    public ArrayList<String> getNameKategorien(){
        String query = "SELECT name FROM thema;";
        ArrayList<String> kategorien = getStringList(query); 
        return kategorien; 
    }
    
    //Hole IDs der Kategorien
     public ArrayList<Integer> getIdKategorien(){
        String query = "SELECT id_thema FROM thema;";
        ArrayList<Integer> idkategorien = getIntegerList(query); 
        return idkategorien; 
    }
    
    //Hole Namen der vom Spieler gespielten Karten
    public ArrayList<String> getNamesofplayedCategories(int playerid){
        String query = "SELECT DISTINCT thema FROM relation_benutzer_karten WHERE id_benutzer = " + playerid + ";";
        ArrayList<String> playedCategoryNames = getStringList(query);
        return playedCategoryNames;
    }
    
}
