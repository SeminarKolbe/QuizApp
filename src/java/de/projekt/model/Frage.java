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
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String answer5;
    private String correctanswer;
    /*private int correctanswer1;
    private int correctanswer2;
    private int correctanswer3;
    private int correctanswer4;
    private int correctanswer5;*/     // welche der 5 Antworten ist richtig  
    private int maxgespielt;        // wie oft die Karte insgesamt schon gespielt wurde(auf diesen Player bezogen)
    private int madewrong;          // wie oft die Karte schon falsch beantwortet wurde(auf diesen Player bezogen)       
    private int madecorrect;        // wie oft die Karte schon korrekt beantwortet wurde(auf diesen Player bezogen)  
    private int id;
    private String thema;
    
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
        /*this.correctanswer1 =correctanswer;
        this.correctanswer2 =correctanswer2;
        this.correctanswer3 =correctanswer3;
        this.correctanswer4 =correctanswer4;
        this.correctanswer5 =correctanswer5;*/
    }
    

    @Override
    public String toString() {
        return "Frage{" + "question=" + question + ", thema=" + thema + '}';
    }
    
    //__________________ Zweiter Kontruktor behinhaltet schon eine Relation zum Player und speicher was dieser falsch gemacht hat
     public Frage(int id, String thema, String frage, String antwort1,String antwort2,String antwort3,String antwort4,String antwort5, String correctanswer,int maxgespielt,
             int madewrong, int madecorrect){
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

    public String getCorrectAnswer() {
        Multigame help = new Multigame();
        int[] correctAnswer = help.IDsStringtoIntArray(this.correctanswer);
        for(int i = 0; i< 5; i++){
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
    
    /*public void setCorrectanswer1(int correctanswer) {
        this.correctanswer1 = correctanswer;
    }
    
    public int getCorrectanswer1() {
        return this.correctanswer1;
    }
    
    public void setCorrectanswer2(int correctanswer) {
        this.correctanswer2 = correctanswer;
    }
    
    public int getCorrectanswer2() {
        return this.correctanswer2;
    }
    
    public void setCorrectanswer3(int correctanswer) {
        this.correctanswer3 = correctanswer;
    }
    
    public int getCorrectanswer3() {
        return this.correctanswer3;
    }
    
    public void setCorrectanswer4(int correctanswer) {
        this.correctanswer4 = correctanswer;
    }
    
    public int getCorrectanswer4() {
        return this.correctanswer4;
    }
    
    public void setCorrectanswer5(int correctanswer) {
        this.correctanswer5 = correctanswer;
    }
    
    public int getCorrectanswer5() {
        return this.correctanswer5;
    }*/
    
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
    
    public char [] getThemaChar(){
        char [] w =new char[thema.length()];
        for(int i=0;i<thema.length();i++){
            w[i]=thema.charAt(i);
        }  
        return w;
    }        
}    


