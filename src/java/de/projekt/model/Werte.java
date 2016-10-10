/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.model;

/**
 *
 * @author Jonas
 */
public interface Werte {
    
    final int fragenanzahl = 3;        //Wie viele Fragen werden bei jedem Spiel gespielt
    final int schwerefragen = 2;         // Wie viele Fragen sind davon Frage, die oft falsch gemacht werden (darf nicht größer als fragenanzal sein
    final int eigenefragen = 1;         //wie viele selbst erstellte Fragen sollen gespielt werden 
    
    final int anzahlmulticards = 3;     // Ist die Anzahl wie viele Karten im Multiplayer gespielt werden
}
