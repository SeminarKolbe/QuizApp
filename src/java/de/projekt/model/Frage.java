/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.model;

/**
 *
 * @author Shaun
 */
public class Frage {
    private  String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String answer5;
    private int correctanswer;      // welche der 5 Antworten ist richtig  
    private int correctanswer2;

    public int getCorrectanswer2() {
        return correctanswer2;
    }

    public int getCorrectanswer3() {
        return correctanswer3;
    }

    public int getCorrectanswer4() {
        return correctanswer4;
    }

    public int getCorrectanswer5() {
        return correctanswer5;
    }
    private int correctanswer3;
    private int correctanswer4;
    private int correctanswer5;
    private int maxgespielt;        // wie oft die Karte insgesamt schon gespielt wurde(auf diesen Player bezogen)
    private int madewrong;          // wie oft die Karte schon falsch beantwortet wurde(auf diesen Player bezogen)       
    private int madecorrect;        // wie oft die Karte schon korrekt beantwortet wurde(auf diesen Player bezogen)  
    private int id;
    private String thema;
    
    public Frage(int id, String thema,String frage, String antwort1,String antwort2,String antwort3,String antwort4,String antwort5, int correctanswer){
        this.id=id;
        this.thema=thema;
        this.question=frage;
        this.answer1=antwort1;
        this.answer2=antwort2;
        this.answer3=antwort3;
        this.answer4=antwort4;
        this.answer5=antwort5;
        this.correctanswer = correctanswer;
    }
    
    public Frage(int id, String thema,String frage, String antwort1,String antwort2,String antwort3,String antwort4,String antwort5, int correctanswer, int correctanswer2, int correctanswer3, int correctanswer4
     ,int correctanswer5){
        this.id=id;
        this.thema=thema;
        this.question=frage;
        this.answer1=antwort1;
        this.answer2=antwort2;
        this.answer3=antwort3;
        this.answer4=antwort4;
        this.answer5=antwort5;
        this.correctanswer =correctanswer;
        this.correctanswer2 =correctanswer2;
        this.correctanswer3 =correctanswer3;
        this.correctanswer4 =correctanswer4;
        this.correctanswer5 =correctanswer5;
    }
    
    

    @Override
    public String toString() {
        return "Frage{" + "question=" + question + ", thema=" + thema + '}';
    }
    
    //__________________ Zweiter Kontruktor behinhaltet schon eine Relation zum Player und speicher was dieser falsch gemacht hat
     public Frage(int id, String thema, String frage, String antwort1,String antwort2,String antwort3,String antwort4,String antwort5, int correctanswer,int maxgespielt,
             int madewrong, int madecorrect){
        this.id=id;
        this.thema=thema;
        this.question=frage;
        this.answer1=antwort1;
        this.answer2=antwort2;
        this.answer3=antwort3;
        this.answer4=antwort4;
        this.answer5=antwort5;
        this.correctanswer= correctanswer;
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

    public void setId(int id) {
        this.id = id;
    }
    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getAnswer5() {
        return answer5;
    }

    public void setAnswer5(String answer5) {
        this.answer5 = answer5;
    }

    public int getCorrectanswer() {
        return correctanswer;
    }

    public void setCorrectanswer(int correctanswer) {
        this.correctanswer = correctanswer;
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

    public String getAnswer1() {
        return answer1;
    }
    
   
    public int getId(){
        return id;
    }
    public String getThema(){
            return thema;
    } 
    
    public char [] getThemaChar(){
        char [] w =new char[thema.length()];
        for(int i=0;i<thema.length();i++){
            w[i]=thema.charAt(i);
        }  
        return w;
    }        
}    


