/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.model;

import java.util.ArrayList;

/**
 *
 * @author Marin
 */
public class Helper {
    
    //Hilfsmethode um einen String in einen Integer-Array umzuwandeln für Fragenset und richtige Antwort
    public static int[] IDsStringtoIntArray(String IDsString) {
        String[] playsetids = IDsString.split("-");
        int[] IDsIntArray = new int[playsetids.length];
        for(int i = 0; i < playsetids.length; i++){
            IDsIntArray[i] = Integer.parseInt(playsetids[i]);
        }
        return IDsIntArray;
    }
    
    //Hilfsmethode zur Umwandlung eines String[] in ArrayList<String>          
    public static ArrayList<String> StringArraytoArrayList(String[] userantworten){
        ArrayList<String> userantwortenNew = new ArrayList<String>();
        if(userantworten.length == 0) {
            return userantwortenNew;
        }
        else {
            for(int i = 0; i < userantworten.length; i++){
                userantwortenNew.add(userantworten[i]);
            }
            return userantwortenNew;
        }
    }
    
}
