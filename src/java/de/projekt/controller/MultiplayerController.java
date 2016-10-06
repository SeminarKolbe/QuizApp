/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.controller;

import de.projekt.model.Frage;
import de.projekt.model.Player;
import de.projekt.model.Werte;
import de.projekt.model.Multigame;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marin
 */

@WebServlet(name = "MultiplayerController", urlPatterns = {"/MultiplayerController"})
public class MultiplayerController extends HttpServlet implements Werte {
/*public static int i=0;
public Frage frage;
public List<Frage> set;
String namegegner;
public static String answercheck="0";
public String username1="";
public static int wrong=0;
public static int right=0;
public int[] numbercards; // Int-Array mit den Id's der Karten die gespielt werden;
public int cardsetid; // id des Cardsets welches gesoielt wird 
public static int spielerzug=0; // Wenn spielerzug 1 ist, ist der zweite Spieler am Zug. Variable wird nur im ersten Schritt verändert
/*******************************************************************************************************************************************************/
String id = "";
String themafrage = "";
int[] questionID = new int[3];
String cardset = "";
ArrayList<Boolean> correctedAnswers = new ArrayList<Boolean>();
ArrayList<String> userantworten = new ArrayList<String>();
int round;
/**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        
        HttpSession session = request.getSession();
        Player currentPlayer = (Player) session.getAttribute("player");
        if(request.getParameter("id") != null) {        
            id = request.getParameter("id");
        }
        else {
            themafrage = request.getParameter("themafrage");
            id = "";
        }
        System.out.println("MultiplayerController1 id: " + id);
        System.out.println("MultiplayerController1 themafrage: " + themafrage);
       
/***************************************Auswahl Multigame*********************************************************/
        switch(id){
            
/***************************************Erstellen eines Multigames************************************************/            
            case "pickOpponent": 
                Multigame currentMultigame = new Multigame(currentPlayer, request.getParameter("gegnerName"));
                session.setAttribute("multigame", currentMultigame);
                request.setAttribute("otherPlayerName", currentMultigame.getotherPlayerName(currentPlayer.getUser_id()));
                session.setAttribute("round", 1);
                System.out.println("MultiplayerController Anfragetyp Herausforderung gewählter Spieler erstellen: " + id + "\notherPlayername: " + currentMultigame.getotherPlayerName(currentPlayer.getUser_id()));
                request.getRequestDispatcher("/WEB-INF/views/categoryMultiplayer.jsp").forward(request, response);
                break;
            case "randomOpponent":
                currentMultigame = new Multigame();
                String otherPlayer = currentMultigame.getRandomPlayerName(currentPlayer.getUser_id());
                currentMultigame = new Multigame(currentPlayer, otherPlayer);
                System.out.println("MultiplayerController Anfragetyp Herausforderung an zufälligen Spieler: " + id);
                session.setAttribute("multigame", currentMultigame);
                request.setAttribute("otherPlayerName", otherPlayer);
                session.setAttribute("round", 1);
                request.getRequestDispatcher("/WEB-INF/views/categoryMultiplayer.jsp").forward(request, response);
                break;
                
/***************************************Runde 1 Spieler spielt 3 Themen des Herausforderes****************************/                
            case "round1":
                int multigameID = Integer.parseInt(request.getParameter("GameRequestID"));
                currentMultigame = new Multigame(multigameID);
                session.setAttribute("multigame", currentMultigame);
                request.setAttribute("otherPlayerName", currentMultigame.getotherPlayerName(currentPlayer.getUser_id()));
                session.setAttribute("round", 1);
                System.out.println("MultiplayerController Anfragetyp Herausforderung angenommen: " + id);
                request.getRequestDispatcher("/WEB-INF/views/categoryMultiplayer.jsp").forward(request, response);
                break;
                
/***************************************Runde 2 Spieler spielt 3 Themen des Gegners**********************************/                
            case "round2":
                multigameID = Integer.parseInt(request.getParameter("GameRequestID"));
                currentMultigame = new Multigame(multigameID);
                session.setAttribute("multigame", currentMultigame);
                request.setAttribute("otherPlayerName", currentMultigame.getotherPlayerName(currentPlayer.getUser_id()));
                cardset = currentMultigame.getcardset1round2();
                questionID = currentMultigame.IDsStringtoIntArray(cardset);
                request.setAttribute("question", currentMultigame.getQuestion(questionID[0]));
                session.setAttribute("currentThema", currentMultigame.getthema1round2());
                request.setAttribute("next", 2);
                session.setAttribute("round", 2);
                session.setAttribute("points", 0);
                request.setAttribute("answers", currentMultigame.getAnswers(questionID[0]));
                session.setAttribute("done", "not yet");
                System.out.println("MultiplayerController Anfragetyp Kontern: " + id);
                request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                break;
        }
/***************************************Verarbeitung eines Multigames**************************************************/
        Multigame currentMultigame = (Multigame) session.getAttribute("multigame");
        System.out.println("MultiplayerController3 round:" + round);
/***************************************Multigame befindet sich in der ersten Runde************************************/
        if((int)session.getAttribute("round") == 1) {
            switch(themafrage) {
                case "thema1frage0":
                    String[] chosenthemen = request.getParameterValues("category");
                    if(currentMultigame.getRound() == 1){
                        currentMultigame.setMultigameResponse(chosenthemen[0], chosenthemen[1], chosenthemen[2]);
                        System.out.println("MultiplayerController hier setze ich die MultigameResponse gewählte themen: " + chosenthemen[0] + chosenthemen[1] + chosenthemen[2] );
                    }else{
                        currentMultigame.setMultigameRequest(chosenthemen[0], chosenthemen[1], chosenthemen[2]);
                    System.out.println("hier bin ich bei Controller vor MultigameRequest gewählte themen: " + chosenthemen[0] + chosenthemen[1] + chosenthemen[2]);
                    }
                    session.setAttribute("points", 0);
                    cardset = currentMultigame.getcardset1round1();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[0]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[0]));
                    session.setAttribute("currentThema", currentMultigame.getthema1round1());
                    request.setAttribute("next", 2);
                    session.setAttribute("done", "not yet");
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                    
                case "thema1frage1":
                    cardset = currentMultigame.getcardset1round1();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[0]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[0]));
                    session.setAttribute("currentThema", currentMultigame.getthema1round1());
                    request.setAttribute("next", 2);
                    //Wenn Antworten abgegeben wurden werden sie ausgewertet. Bei keinen angekreuzten Antworten wird userantworten mit einem Eintrag "null" ergänzt.
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[0]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request,response);
                    break;

                case "thema1frage2":
                    System.out.println("Hier bin ich bei thema1frage2.");
                    cardset = currentMultigame.getcardset1round1();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[1]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[1]));
                    request.setAttribute("next", 3);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }                        
                        correctedAnswers = checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[1]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request,response);
                    break;

                case "thema1frage3":
                    cardset = currentMultigame.getcardset1round1();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[2]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[2]));
                    request.setAttribute("next", 4);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[2]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                    
                case "thema2frage1":
                    cardset = currentMultigame.getcardset2round1();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[0]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[0]));
                    session.setAttribute("currentThema", currentMultigame.getthema2round1());
                    request.setAttribute("next", 5);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[0]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                
                case "thema2frage2":
                    cardset = currentMultigame.getcardset2round1();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[1]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[1]));
                    request.setAttribute("next", 6);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[1]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                
                case "thema2frage3":
                    cardset = currentMultigame.getcardset2round1();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[2]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[2]));
                    request.setAttribute("next", 7);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[2]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }                    
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                    
                case "thema3frage1":
                    cardset = currentMultigame.getcardset3round1();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[0]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[0]));
                    session.setAttribute("currentThema", currentMultigame.getthema3round1());
                    request.setAttribute("next", 8);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[0]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                
                case "thema3frage2":
                    cardset = currentMultigame.getcardset3round1();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[1]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[1]));
                    request.setAttribute("next", 9);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[1]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                
                case "thema3frage3":
                    cardset = currentMultigame.getcardset3round1();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[2]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[2]));
                    request.setAttribute("next", 10);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[2]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                
                case "endround":
                    currentMultigame.setPointsforPlayer(currentPlayer, (int) session.getAttribute("points"));
                    System.out.println("pointsrunde2: " + (int) session.getAttribute("points"));
                    System.out.println("MultiplayerController getRound(): " + currentMultigame.getRound());
//Runde = 0 Spieleröffner hat gespielt, Runde = 1 Spielannehmer hat gespielt/kann gespielt werden. Abhängig von der Runde der in der DB gespeichert ist.
                    if(currentMultigame.getRound() == 1){
                        session.setAttribute("round", 2); //Weiter zur zweiten Runde
                        request.setAttribute("thema1", currentMultigame.getthema1round2());
                        request.setAttribute("thema2", currentMultigame.getthema2round2());
                        request.setAttribute("thema3", currentMultigame.getthema3round2());
                        request.getRequestDispatcher("/WEB-INF/views/WeiterleitungRunde2.jsp").forward(request, response);
                    }else {
                        currentMultigame.setRound(1);
                        request.getRequestDispatcher("/WEB-INF/views/Spielmodiwahl.jsp").forward(request, response);
                    }
                    break;
            }
        }
        System.out.println("MultiplayerController4 getRound(): \t" + currentMultigame.getRound());
/***************************************Multigame befindet sich in der zweiten Runde************************************/
        if((int)session.getAttribute("round") == 2) {
            switch(themafrage) {
                case "thema1frage1":
                    cardset = currentMultigame.getcardset1round2();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[0]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[0]));
                    session.setAttribute("currentThema", currentMultigame.getthema1round2());
                    request.setAttribute("next", 2);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[0]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request,response);
                    break;

                case "thema1frage2":
                    cardset = currentMultigame.getcardset1round2();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[1]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[1]));
                    request.setAttribute("next", 3);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[1]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request,response);
                    break;

                case "thema1frage3":
                    cardset = currentMultigame.getcardset1round2();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[2]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[2]));
                    request.setAttribute("next", 4);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[2]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                    
                case "thema2frage1":
                    cardset = currentMultigame.getcardset2round2();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[0]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[0]));
                    session.setAttribute("currentThema", currentMultigame.getthema2round2());
                    request.setAttribute("next", 5);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[0]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                
                case "thema2frage2":
                    cardset = currentMultigame.getcardset2round2();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[1]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[1]));
                    request.setAttribute("next", 6);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[1]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                
                case "thema2frage3":
                    cardset = currentMultigame.getcardset2round2();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[2]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[2]));
                    request.setAttribute("next", 7);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[2]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                    
                case "thema3frage1":
                    cardset = currentMultigame.getcardset3round2();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[0]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[0]));
                    session.setAttribute("currentThema", currentMultigame.getthema3round2());
                    request.setAttribute("next", 8);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[0]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                
                case "thema3frage2":
                    cardset = currentMultigame.getcardset3round2();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[1]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[1]));
                    request.setAttribute("next", 9);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[1]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                
                case "thema3frage3":
                    cardset = currentMultigame.getcardset3round2();
                    questionID = currentMultigame.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[2]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[2]));
                    request.setAttribute("next", 10);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[2]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                    
                case "endround":
                    if(currentMultigame.getRound() == 1){
                        currentMultigame.setRound(2);
                    } else{
                        currentMultigame.setRound(3);
                    }
                    currentMultigame.setPointsforPlayer(currentPlayer, (int) session.getAttribute("points"));
                    request.getRequestDispatcher("/WEB-INF/views/Spielmodiwahl.jsp").forward(request, response);
                    break;
            }
        }
    }
    
    //angekreuzte Antworten des Benutzers werden mit den Antworten in der DB verglichen: richtige Antworten angekreuzt = true; falsche Antworten angekreuzt = false;
    private ArrayList<Boolean> checkAnswers(ArrayList<String> useranswers, int[] CorrectAnswer){
        ArrayList<Boolean> correctedAnswers = new ArrayList<Boolean>();
        for(int i = 0; i < 5; i++){
            correctedAnswers.add(i, null);
        }
        // Es wird geguckt, ob alle angekreuzten Antworten richtig sind 
        if(!useranswers.get(0).equals("null")){
            for(int f=0;f<useranswers.size();f++){
                switch(useranswers.get(f)){ 
                    case "a1": 
                        if(CorrectAnswer[0] == 1) correctedAnswers.set(0, true);
                        else if(CorrectAnswer[0] == 0) correctedAnswers.set(0, false);
                        break;
                    case "a2": 
                        if(CorrectAnswer[1] == 1) correctedAnswers.set(1, true);
                        else if(CorrectAnswer[1] == 0) correctedAnswers.set(1, false);
                        break;
                    case "a3": 
                        if(CorrectAnswer[2] == 1) correctedAnswers.set(2, true);
                        else if(CorrectAnswer[2] == 0) correctedAnswers.set(2, false);
                        break;
                    case "a4": 
                        if(CorrectAnswer[3] == 1) correctedAnswers.set(3, true);
                        else if(CorrectAnswer[3] == 0) correctedAnswers.set(3, false);
                        break;
                    case "a5": 
                        if(CorrectAnswer[4] == 1) correctedAnswers.set(4, true);
                        else if(CorrectAnswer[4] == 0) correctedAnswers.set(4, false);
                        break;
                }
            }
        }
        //Überprüfung, ob alle nicht angekreuzten Antworten auch falsch sind. Ja: true  Nein: false
        for(int f = 0; f < correctedAnswers.size(); f++){
            if(correctedAnswers.get(f) == null && CorrectAnswer[f] == 0){
                correctedAnswers.set(f, true);
            } else if(correctedAnswers.get(f) == null && CorrectAnswer[f] == 1) {
                correctedAnswers.set(f, false);
            }
        }
        return correctedAnswers;
    }
    
    private int getAchievedPointsForThisQuestion(ArrayList<Boolean> correctedAnswers, int pointsBefore){
        int pointsAfter = pointsBefore;
        for(int i = 0; i < correctedAnswers.size(); i++){
            if(correctedAnswers.get(i)){
                pointsAfter += 5;
            }
        }
        return pointsAfter;
    }
    
    //Hilfsmethode zur Umwandlung eines String[] in ArrayList<String>           #evtl kapseln in Multigame!
    private ArrayList<String> StringArraytoArrayList(String[] userantworten){
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
                
   //___________Anfrage eines anderen Spielers verarebeiten     
        /*try{
             String [] help = request.getParameterValues("top5");
             String anfrage = help[0];  // ist die Id des Cardsets welches gepielt wird
             cardsetid = Integer.parseInt(anfrage);

              Multi g = new Multi((Player) session.getAttribute("player"));
              namegegner = g.getNamePlayer1(cardsetid);
              numbercards=g.getCardSet(anfrage);    // gibt einen int-Array mit den Karten-ids aus
              MultiPlayerKarten mult = new MultiPlayerKarten();
              set=mult.generateGame(numbercards); // erzeugt ein Set aus Fragen
              spielerzug=1;
              i=2;
           
       }catch(Exception e){
           System.out.println(e.getMessage());
       } */
        

        /*try{ 
      
       //___Falls der Spieler die Kategorie gewählt hat, soll ihm alle Spieler angezeigt werden
       
            if(i==0){
                    Multi m =new Multi((Player) session.getAttribute("player"));
                    List<String> namen= m.getUser();
                    i++;
                    request.setAttribute("name", namen);
                    request.getRequestDispatcher("/WEB-INF/views/MultiAuswahl.jsp").forward(request, response);
                    return;
            }
        //_____Hier wird der Benutzer überprüft und Karten erzeugt
                if(i==1){
                    int id=-2;   
                    String help= request.getParameter("id");
                    if(help.equals("r"))  // r = random
                        id=-1;
                    Player player = (Player) session.getAttribute("player");
                    System.out.println("Hier bin ich im MultiplayerController i=1");
                    Multigame currentMultigame = new Multigame(player.getUser_id());
                    /*Multi n =new Multi((Player) session.getAttribute("player"));
                    numbercards= n.getGame(id);      
                    MultiPlayerKarten mult =new MultiPlayerKarten();
                    set=mult.generateGame(numbercards);
                    namegegner=n.getUsername();
                    i++;
         //______Hier folgt die Ausgabe ___    
                }if(i>1){
                /*    
                    if(i>2){

                        answercheck= (String)request.getParameter("res");
                        userantworten= request.getParameterValues("antwort"); 

                    }    


                    if(answercheck.equals("1")){  // Das Servlet soll gucken ob die Antworten richtig sind und dme Benutzer die JSP zurückliefern, ohne eine neue Frage
                        frage=set.get(i-3);
                        //___überprüfen ob die Antwort richtig ist



                        request.setAttribute("namegegner", namegegner);
                        request.setAttribute("frage",frage.getQuestion());
                        request.setAttribute("antwor1", frage.getAnswer1());
                        request.setAttribute("antwor2", frage.getAnswer2());
                        request.setAttribute("antwor3", frage.getAnswer3());
                        request.setAttribute("antwor4", frage.getAnswer4());
                        request.setAttribute("antwor5", frage.getAnswer5());
                        //request.setAttribute("correctanswer", frage.getCorrectAnswer());
                        request.setAttribute("correct1", Integer.toString(frage.getCorrectanswer1()));
                        request.setAttribute("correct2", Integer.toString(frage.getCorrectanswer2()));
                        request.setAttribute("correct3", Integer.toString(frage.getCorrectanswer3()));
                        request.setAttribute("correct4", Integer.toString(frage.getCorrectanswer4()));
                        request.setAttribute("correct5", Integer.toString(frage.getCorrectanswer5()));

                        int f;
                        int [] santworten =new int[userantworten.length];  // Die Antworten die der Nutzer gegeben hat, werden Sortiert und ausgegeben

                        // Es wird geguckt, ob alle angekreutzen Kästchen richtig sind und speichert die Ergebnisse für das Endresult
                        for(f=0;f<userantworten.length;f++){

                            switch(userantworten[f]){
                                case "a1": 
                                    santworten[f] = 1;
                                    break;
                                case "a2": 
                                    santworten[f] = 2;
                                    break;
                                case "a3": 
                                    santworten[f] = 3;
                                    break;
                                case "a4": 
                                    santworten[f] = 4;
                                    break;
                                case "a5": 
                                    santworten[f] = 5;
                                    break;
                            }
                            /*
                            if(userantworten[f].equals("a1")){
                                santworten[f]=1;
                            }if(userantworten[f].equals("a2")){
                                santworten[f]=2;
                            }if(userantworten[f].equals("a3")){
                                santworten[f]=3;
                            }if(userantworten[f].equals("a4")){
                                santworten[f]=4;
                            }if(userantworten[f].equals("a5")){
                                santworten[f]=5;
                            }
                        }
                        //ResultBerechnenMulti result = new ResultBerechnenMulti(santworten, frage.getCorrectAnswer());
                        ResultBerechnenMulti result = new ResultBerechnenMulti(santworten, frage.getCorrectanswer1(), frage.getCorrectanswer2(), frage.getCorrectanswer3(), frage.getCorrectanswer4(), frage.getCorrectanswer5());
                        if(result.WrongRight()==true){
                            right++;      
                        }else{
                            wrong++;
                        }    


                        AntwortenUebergabe hilfe = new AntwortenUebergabe(santworten);
                        request.setAttribute("santworten",hilfe);
                        request.setAttribute("abgegeben", Integer.toString(f));
                        request.setAttribute("checkanswer", "1");

                        request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);    
                        return;
               //______Letzter Schritt vor der Ausgabe des Endergebnisses 
                    }if(i==anzahlmulticards+2){

                          request.setAttribute("right",right);
                          request.setAttribute("wrong",wrong);
                          try{
                               username1= (String) session.getAttribute("userName"); 
                          }catch(Exception e){

                          }
                              Multi resultabfrage =new Multi((Player) session.getAttribute("player"));
                          if(spielerzug==1){// Falls ein Duell gespielt wird und der zweite Spieler am Zug ist
                               resultabfrage.getResult2(cardsetid, right, wrong, username1);        //updatet die Datenbank und legt das Ergebnis des Spielers ab
                               request.setAttribute("rightplayer1",resultabfrage.getRightPlayer1(cardsetid));
                               request.setAttribute("wrongplayer1",resultabfrage.getWrongePlayer1(cardsetid));
                               request.setAttribute("multiausgabe","multi");
                               request.setAttribute("gegnername",namegegner); 
                          }else{
                              resultabfrage.getResult1(resultabfrage.getGameId(), right, wrong);
                              request.setAttribute("multiausgabe","einzel");
                          }
                           DatenbankZugang db=new DatenbankZugang();
                           db.setPoints((String)session.getAttribute("userName"), right);
                          request.getRequestDispatcher("/WEB-INF/views/EndResult.jsp").forward(request, response);  
                          return;
                    }

               //______________________________________________     

                    frage=set.get(i-2);

                    request.setAttribute("namegegner", namegegner);
                    request.setAttribute("frage",frage.getQuestion());
                    request.setAttribute("antwort1", frage.getAnswer1());
                    request.setAttribute("antwort2", frage.getAnswer2());
                    request.setAttribute("antwort3", frage.getAnswer3());
                    request.setAttribute("antwort4", frage.getAnswer4());
                    request.setAttribute("antwort5", frage.getAnswer5());
                    request.setAttribute("checkanswer", "0");  

                    if(!answercheck.equals("1")){
                        i++;

                        request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                        return;
                    }
                }
            }catch(Exception e){
                System.out.println(e);
            }*/
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            processRequest(request, response);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            processRequest(request, response);
        }catch(Exception e){
            System.out.println(e);
        }
                
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
