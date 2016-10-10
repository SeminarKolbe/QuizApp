/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.model;

/**
 *
 * @author Marin
 * 
 * 
 */
public class Frage {
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String answer5;
    private String correctanswer;
    private int maxgespielt;        // wie oft die Karte insgesamt schon gespielt wurde(auf diesen Player bezogen)
    private int madewrong;          // wie oft die Karte schon falsch beantwortet wurde(auf diesen Player bezogen)       
    private int madecorrect;        // wie oft die Karte schon korrekt beantwortet wurde(auf diesen Player bezogen)  
    private int id;
    private String thema;
    
    //Konstruktor um eine Frage-Objekt zu erstellen
    public Frage(int id, String thema,String frage, String antwort1,String antwort2,String antwort3,String antwort4,String antwort5, String correctAnswer){
        this.id=id;
        this.thema=thema;
        this.question=frage;
        this.answer1=antwort1;
        this.answer2=antwort2;
        this.answer3=antwort3;
        this.answer4=antwort4;
        this.answer5=antwort5;
        this.correctanswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "Frage{" + "question=" + question + ", thema=" + thema + '}';
    }
    
    //Zweiter Kontruktor behinhaltet schon eine Relation zum Benutzer und speichert was dieser wie oft falsch gemacht hat
     public Frage(int id, String thema, String frage, String antwort1,String antwort2,String antwort3,String antwort4,String antwort5, String correctanswer, int madecorrect, int madewrong, int maxgespielt){
        this.id=id;
        this.thema=thema;
        this.question=frage;
        this.answer1=antwort1;
        this.answer2=antwort2;
        this.answer3=antwort3;
        this.answer4=antwort4;
        this.answer5=antwort5;
        this.correctanswer=correctanswer;
        this.maxgespielt = maxgespielt;
        this.madewrong=madewrong;
        this.madecorrect=madecorrect;
     }
    
    //_______________Getter-Setter__________________________
    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }
    
    public String getAnswer1() {
        return this.answer1;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getAnswer2() {
        return this.answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return this.answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return this.answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getAnswer5() {
        return this.answer5;
    }

    public void setAnswer5(String answer5) {
        this.answer5 = answer5;
    }
    
    //Überprüfung welcher der Antworten die richtige sit
    public String getCorrectAnswer(){
        int[] correctAnswer = Helper.IDsStringtoIntArray(this.correctanswer);
        for(int i = 0; i < 5; i++){
            switch(i){
                case 0:
                    if(correctAnswer[i] == 1) return this.answer1;
                    break;
                case 1:
                    if(correctAnswer[i] == 1) return this.answer2;
                    break;
                case 2:
                    if(correctAnswer[i] == 1) return this.answer3;
                    break;
                case 3:
                    if(correctAnswer[i] == 1) return this.answer4;
                    break;
                case 4:
                    if(correctAnswer[i] == 1) return this.answer5;
                    break;
            }
        }
        return "";
    }
    
    public int getMaxgespielt() {
        return maxgespielt;
    }

    public void setMaxgespielt(int maxgespielt) {
        this.maxgespielt = maxgespielt;
    }

    public int getMadewrong() {
        return madewrong;
    }

    public void setMadewrong(int madewrong) {
        this.madewrong = madewrong;
    }

    public int getMadecorrect() {
        return madecorrect;
    }

    public void setMadecorrect(int madecorrect) {
        this.madecorrect = madecorrect;
    }
    
    public String getQuestion() {
        return question;
    }
       
    public int getId(){
        return id;
    }
    
    public String getThema(){
            return thema;
    }     
}    


