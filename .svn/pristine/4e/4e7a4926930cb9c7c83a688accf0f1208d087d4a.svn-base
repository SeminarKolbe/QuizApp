package de.projekt.model;

import java.util.Arrays;


/**
 *
 * @author René
 */
public class Question {
    private String frage;
    private String antwort1;
    private String antwort2;
    private String antwort3;
    private String antwort4;
    private String antwort5;
    private String thema;
    private int[] correct;
    private int id_card;
    private String qstType;

    public int getId_card() {
        return id_card;
    }

    public void setId_card(int id_card) {
        this.id_card = id_card;
    }

    public String getQstType() {
        return qstType;
    }

    public void setQstType(String qstType) {
        this.qstType = qstType;
    }
    
    public Question (String qstType, String frage, String antwort1, String antwort2, String antwort3, String antwort4, String antwort5, String thema, int[] correct) {
        this.frage = frage;
        this.antwort1 = antwort1;
        this.antwort2 = antwort2;
        this.antwort3 = antwort3;
        this.antwort4 = antwort4;
        this.antwort5 = antwort5;
        this.thema = thema;
        this.correct=correct;
        this.qstType=qstType;
    }
    
    
    public void setFrage(String frage) {
        this.frage = frage;
    }

    public void setAntwort1(String antwort1) {
        this.antwort1 = antwort1;
    }

    public void setAntwort2(String antwort2) {
        this.antwort2 = antwort2;
    }

    public void setAntwort3(String antwort3) {
        this.antwort3 = antwort3;
    }

    public void setAntwort4(String antwort4) {
        this.antwort4 = antwort4;
    }

    public void setAntwort5(String antwort5) {
        this.antwort5 = antwort5;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }

    public void setCorrect(int[] correct) {
        this.correct = correct;
    }

    public String getFrage() {
        return frage;
    }

    public String getAntwort1() {
        return antwort1;
    }

    public String getAntwort2() {
        return antwort2;
    }

    public String getAntwort3() {
        return antwort3;
    }

    public String getAntwort4() {
        return antwort4;
    }

    public String getAntwort5() {
        return antwort5;
    }

    public String getThema() {
        return thema;
    }
    public int getCorrect1() {
        return correct[0];
    }
    public int getCorrect2() {
        return correct[1];
    }
    public int getCorrect3() {
        return correct[2];
    }
    public int getCorrect4() {
        return correct[3];
    }
    public int getCorrect5() {
        return correct[4];
    }

    @Override
    public String toString() {
        return "Question{" + "id_card=" + id_card + ", qstType= "+ qstType + ", frage=" + frage + ", antwort1=" + antwort1 + ", antwort2=" + antwort2 + ", antwort3=" + antwort3 + ", antwort4=" + antwort4 + ", antwort5=" + antwort5 + ", thema=" + thema + ", correct=" + Arrays.toString(correct) +  '}';
    }
   
}
