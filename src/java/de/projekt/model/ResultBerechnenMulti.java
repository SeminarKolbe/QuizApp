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
public class ResultBerechnenMulti {
    int [] antworten; // Antworten die der Nutzer im Multiplayer angekreuzt hat.
    int [] correct=new int[5];
    int correct1;
    int correct2;
    int correct3;
    int correct4;
    int correct5;
    int right=0;
    int anzahl=5;
    
    
    public ResultBerechnenMulti(int[] antworten, int correct1, int correct2, int correct3, int correct4, int correct5) {
        this.antworten = antworten;
        this.correct[0] = correct1;
        this.correct[1] = correct2;
        this.correct[2] = correct3;
        this.correct[3] = correct4;
        this.correct[4] = correct5;  
    }
    
    public boolean WrongRight(){
       int [] help =new int[5];
        for(int i=0;i<antworten.length;i++){
            help[antworten[i]-1]=1;
        }
        for(int i=0;i<help.length;i++){
            if(help[i]!=1)
                help[i]=0;
        }

        for(int i=0;i<correct.length;i++){
            if(correct[i]==1&&help[i]==1){
                    right++;
            }else if(correct[i]!=1 &&help[i]==0){
                    right++;
            
            }
      
        }
        if(right==anzahl)
            return true;
       return false;
    }
}
