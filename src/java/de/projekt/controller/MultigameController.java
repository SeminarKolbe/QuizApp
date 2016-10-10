/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.controller;

import de.projekt.model.Helper;
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

@WebServlet(name = "MultigameController", urlPatterns = {"/MultigameController"})
public class MultigameController extends HttpServlet implements Werte {

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
        if(request.getParameter("themafrage") != null) {        
            themafrage = request.getParameter("themafrage");
        }
       
/***************************************Verarbeitung eines Multigames**************************************************/
        Multigame currentMultigame = (Multigame) session.getAttribute("multigame");
/***************************************Multigame befindet sich in der ersten Runde************************************/
        if((int)session.getAttribute("round") == 1) {
            switch(themafrage) {
    /***********************gewählte Themen aus der Request holen und in der DB abspeichern für ein Multigame*****************/
                case "thema1frage0":
                    String chosencategory1 = request.getParameter("category1");
                    String chosencategory2 = request.getParameter("category2");
                    String chosencategory3 = request.getParameter("category3");
                    if(currentMultigame.getRound() == 1){
                        currentMultigame.setMultigameResponse(chosencategory1, chosencategory2, chosencategory3);                        
                    }else{
                        currentMultigame.setMultigameRequest(chosencategory1, chosencategory2, chosencategory3);
                    }
    /*************************Multigame anfangen: Frage, Antworten, Weiterleitung zur nächsten Frage(next) in Request legen**********/
    /*************************Punktestand für diese Runde, Antwortabgabe(done) und aktuelles Thema in die Session legen**************/
                    session.setAttribute("points", 0);
                    cardset = currentMultigame.getcardset1round1();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[0]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[0]));
                    session.setAttribute("currentThema", currentMultigame.getthema1round1());
                    request.setAttribute("next", 2);
                    session.setAttribute("done", "not yet");
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                    
                case "thema1frage1":
                    cardset = currentMultigame.getcardset1round1();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[0]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[0]));
                    session.setAttribute("currentThema", currentMultigame.getthema1round1());
                    request.setAttribute("next", 2);
                    //Wenn Antworten abgegeben wurden werden sie ausgewertet. Bei keinen angekreuzten Antworten wird userantworten mit einem Eintrag "null" ergänzt.
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = Helper.StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = currentMultigame.checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[0]));
                        session.setAttribute("points", currentMultigame.getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        //Userantworten und korrigierte Antworten in der Request der View übergeben
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request,response);
                    break;

                case "thema1frage2":
                    cardset = currentMultigame.getcardset1round1();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[1]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[1]));
                    request.setAttribute("next", 3);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = Helper.StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }                        
                        correctedAnswers = currentMultigame.checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[1]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", currentMultigame.getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request,response);
                    break;

                case "thema1frage3":
                    cardset = currentMultigame.getcardset1round1();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[2]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[2]));
                    request.setAttribute("next", 4);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = Helper.StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = currentMultigame.checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[2]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", currentMultigame.getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                    
                case "thema2frage1":
                    cardset = currentMultigame.getcardset2round1();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[0]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[0]));
                    session.setAttribute("currentThema", currentMultigame.getthema2round1());
                    request.setAttribute("next", 5);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = Helper.StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = currentMultigame.checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[0]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", currentMultigame.getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                
                case "thema2frage2":
                    cardset = currentMultigame.getcardset2round1();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[1]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[1]));
                    request.setAttribute("next", 6);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = Helper.StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = currentMultigame.checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[1]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", currentMultigame.getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                
                case "thema2frage3":
                    cardset = currentMultigame.getcardset2round1();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[2]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[2]));
                    request.setAttribute("next", 7);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = Helper.StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = currentMultigame.checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[2]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", currentMultigame.getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }                    
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                    
                case "thema3frage1":
                    cardset = currentMultigame.getcardset3round1();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[0]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[0]));
                    session.setAttribute("currentThema", currentMultigame.getthema3round1());
                    request.setAttribute("next", 8);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = Helper.StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = currentMultigame.checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[0]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", currentMultigame.getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                
                case "thema3frage2":
                    cardset = currentMultigame.getcardset3round1();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[1]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[1]));
                    request.setAttribute("next", 9);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = Helper.StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = currentMultigame.checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[1]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", currentMultigame.getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                
                case "thema3frage3":
                    cardset = currentMultigame.getcardset3round1();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[2]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[2]));
                    request.setAttribute("next", 10);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = Helper.StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = currentMultigame.checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[2]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", currentMultigame.getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                
    /*******************************Multigame hat die letzte Frage der Runde passiert********************************/
                case "endround":
//Runde = 0 Spieleröffner hat gespielt, Runde = 1 Spielannehmer hat gespielt/kann gespielt werden. Abhängig von der Runde der in der DB gespeichert ist.
                    if(currentMultigame.getRound() == 1){
                        currentMultigame.setPointsPlayer2Round1((int)session.getAttribute("points"));
                        session.setAttribute("round", 2);
                        request.setAttribute("thema1", currentMultigame.getthema1round2());
                        request.setAttribute("thema2", currentMultigame.getthema2round2());
                        request.setAttribute("thema3", currentMultigame.getthema3round2());
                        request.getRequestDispatcher("/WEB-INF/views/WeiterleitungRunde2.jsp").forward(request, response);
                    }else {
                        currentMultigame.setPointsPlayer1Round1((int)session.getAttribute("points"));
                        currentMultigame.setRound(1);
                        request.getRequestDispatcher("/WEB-INF/views/Spielmodiwahl.jsp").forward(request, response);
                    }
                    session.setAttribute("points", 0); //Zurücksetzen des Punktestand für die Sitzung des Benutzer
                    return;
            }
        }
/***************************************Multigame befindet sich in der zweiten Runde************************************/
        if((int)session.getAttribute("round") == 2) {
            switch(themafrage) {
                case "thema1frage1":
                    cardset = currentMultigame.getcardset1round2();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[0]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[0]));
                    session.setAttribute("currentThema", currentMultigame.getthema1round2());
                    request.setAttribute("next", 2);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = Helper.StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = currentMultigame.checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[0]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", currentMultigame.getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request,response);
                    break;

                case "thema1frage2":
                    cardset = currentMultigame.getcardset1round2();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[1]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[1]));
                    request.setAttribute("next", 3);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = Helper.StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = currentMultigame.checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[1]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", currentMultigame.getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request,response);
                    break;

                case "thema1frage3":
                    cardset = currentMultigame.getcardset1round2();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[2]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[2]));
                    request.setAttribute("next", 4);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = Helper.StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = currentMultigame.checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[2]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", currentMultigame.getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                    
                case "thema2frage1":
                    cardset = currentMultigame.getcardset2round2();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[0]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[0]));
                    session.setAttribute("currentThema", currentMultigame.getthema2round2());
                    request.setAttribute("next", 5);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = Helper.StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = currentMultigame.checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[0]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", currentMultigame.getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                
                case "thema2frage2":
                    cardset = currentMultigame.getcardset2round2();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[1]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[1]));
                    request.setAttribute("next", 6);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = Helper.StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = currentMultigame.checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[1]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", currentMultigame.getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                
                case "thema2frage3":
                    cardset = currentMultigame.getcardset2round2();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[2]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[2]));
                    request.setAttribute("next", 7);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = Helper.StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = currentMultigame.checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[2]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", currentMultigame.getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                    
                case "thema3frage1":
                    cardset = currentMultigame.getcardset3round2();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[0]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[0]));
                    session.setAttribute("currentThema", currentMultigame.getthema3round2());
                    request.setAttribute("next", 8);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = Helper.StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = currentMultigame.checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[0]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", currentMultigame.getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                
                case "thema3frage2":
                    cardset = currentMultigame.getcardset3round2();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[1]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[1]));
                    request.setAttribute("next", 9);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = Helper.StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = currentMultigame.checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[1]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", currentMultigame.getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                
                case "thema3frage3":
                    cardset = currentMultigame.getcardset3round2();
                    questionID = Helper.IDsStringtoIntArray(cardset);
                    request.setAttribute("question", currentMultigame.getQuestion(questionID[2]));
                    request.setAttribute("answers", currentMultigame.getAnswers(questionID[2]));
                    request.setAttribute("next", 10);
                    if(session.getAttribute("done").equals("done")){
                        if(request.getParameterValues("answer") != null){
                            userantworten = Helper.StringArraytoArrayList(request.getParameterValues("answer"));
                        } else {
                            userantworten.clear();
                            userantworten.add("null");
                        }
                        correctedAnswers = currentMultigame.checkAnswers(userantworten, currentMultigame.getCorrectAnswers(questionID[2]));
                        request.setAttribute("correctedAnswers", correctedAnswers);
                        session.setAttribute("points", currentMultigame.getAchievedPointsForThisQuestion(correctedAnswers, (int) session.getAttribute("points")));
                        request.setAttribute("useranswers", userantworten);
                    }
                    request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                    break;
                    
                case "endround":
                    //Hat Spieler 2 seine Runde beendet, d.h. in der DB ist 1 als Runde gespeichert, 
                    //werden Punkte für die Runde in der DB gespeichert und zum Hauptmenü weitergeleitet
                    if(currentMultigame.getRound() == 1){
                        currentMultigame.setRound(2);
                        currentMultigame.setPointsPlayer2Round2((int)session.getAttribute("points"));
                        request.getRequestDispatcher("/WEB-INF/views/Spielmodiwahl.jsp").forward(request, response);
                    } else{ //Spieler 1 hat zweite Runde gespielt, sodass Runde auf 3 gesetzt wird in DB
                        currentMultigame.setRound(3);
                        currentMultigame.setPointsPlayer1Round2((int)session.getAttribute("points"));
                        String gegnername = currentMultigame.getotherPlayerName(currentPlayer.getUser_id());
                        String playername = currentPlayer.getName();
                        int pointsplayer1round1 = currentMultigame.getPointsPlayer1Round1();
                        int pointsplayer1round2 = currentMultigame.getPointsPlayer1Round2();
                        int pointsplayer2round1 = currentMultigame.getPointsPlayer2Round1();
                        int pointsplayer2round2 = currentMultigame.getPointsPlayer2Round2();
                        String winnerRound1, winnerRound2, winnerTotal;
                        
                        //Ermitteln des Gewinners einer Runde: Der Spieler der mehr Punkte hat, gewinnt diese; Sonst unentschieden
                        if(pointsplayer1round1 > pointsplayer2round1) {
                            winnerRound1 = currentPlayer.getName();
                        } else if(pointsplayer1round1 < pointsplayer2round1) {
                            winnerRound1 = currentMultigame.getotherPlayerName(currentPlayer.getUser_id());
                        } else winnerRound1 = "unentschieden";
                        if(pointsplayer1round2 > pointsplayer2round2) {
                            winnerRound2 = currentPlayer.getName();
                        } else if(pointsplayer1round2 < pointsplayer2round2){
                            winnerRound2 = currentMultigame.getotherPlayerName(currentPlayer.getUser_id());
                        } else winnerRound2 = "unentschieden";
                        
                        //Gewinner des Multigames ermitteln: Gewinner muss eine Runde mehr gewinnen als der andere Spieler sonst unentschieden(=-1)
                        if((winnerRound1.equals("unentschieden")&&winnerRound2.equals("unentschieden"))||(winnerRound1.equals(gegnername)&&(winnerRound2.equals(playername)))||(winnerRound1.equals(playername)&&winnerRound2.equals(gegnername))){
                            winnerTotal = "unentschieden";
                            currentMultigame.setWinnerID(-1);
                        }else if((winnerRound1.equals("unentschieden")&&winnerRound2.equals(gegnername))||(winnerRound1.equals(gegnername)&&(winnerRound2.equals("unentschieden")||winnerRound2.equals(gegnername)))){
                            winnerTotal = gegnername;
                            currentMultigame.setWinnerID(new Player(gegnername).getUser_id());
                        } else {
                            winnerTotal = playername;
                            currentMultigame.setWinnerID(currentPlayer.getUser_id());
                        }
                        //Punkte für Multigame werden in der DB gespeichert
                        currentMultigame.setPointsforPlayers();
                        //Alle benötigten Daten für das Endresultat in die Request ablegen
                        request.setAttribute("gegnername",gegnername);
                        request.setAttribute("pointsplayer1round1",pointsplayer1round1);
                        request.setAttribute("pointsplayer1round2",pointsplayer1round2);
                        request.setAttribute("pointsplayer2round1",pointsplayer2round1);
                        request.setAttribute("pointsplayer2round2",pointsplayer2round2);
                        request.setAttribute("winnerRound1", winnerRound1);
                        request.setAttribute("winnerRound2", winnerRound2);
                        request.setAttribute("winnerTotal", winnerTotal);
                        request.getRequestDispatcher("/WEB-INF/views/EndResult.jsp").forward(request, response);
                    }
                    //Zurücksetzen auf Anfangszustand des Benutzers
                    session.removeAttribute("points");
                    session.removeAttribute("round"); 
                    break;
            }
        }
    }
    
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
