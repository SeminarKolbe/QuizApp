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
@WebServlet(name = "MultiauswahlController", urlPatterns = {"/MultiauswahlController"})
public class MultiauswahlController extends HttpServlet implements Werte{
    
    String id = "";
    int[] questionID = new int[3];
    String cardset = "";
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
    /***************************************Auswahl Multigame*********************************************************/
        switch(id){
            
    /***************************************Erstellen eines Multigames************************************************/            
            case "pickOpponent": 
                Multigame currentMultigame = new Multigame(currentPlayer, request.getParameter("gegnerName"));
                session.setAttribute("multigame", currentMultigame);
                request.setAttribute("otherPlayerName", currentMultigame.getotherPlayerName(currentPlayer.getUser_id()));
                session.setAttribute("round", 1);
                request.getRequestDispatcher("/WEB-INF/views/MultiPlayer.jsp").forward(request, response);
                break;
            case "randomOpponent":
                currentMultigame = new Multigame();
                String otherPlayer = currentMultigame.getRandomPlayerName(currentPlayer.getUser_id());
                currentMultigame = new Multigame(currentPlayer, otherPlayer);
                session.setAttribute("multigame", currentMultigame);
                request.setAttribute("otherPlayerName", otherPlayer);
                session.setAttribute("round", 1);
                request.getRequestDispatcher("/WEB-INF/views/MultiPlayer.jsp").forward(request, response);
                break;

        /***************************************Runde 1 Spieler spielt 3 Themen des Herausforderes****************************/                
            case "round1":
                int multigameID = Integer.parseInt(request.getParameter("GameRequestID"));
                currentMultigame = new Multigame(multigameID);
                session.setAttribute("multigame", currentMultigame);
                request.setAttribute("otherPlayerName", currentMultigame.getotherPlayerName(currentPlayer.getUser_id()));
                session.setAttribute("round", 1);
                request.getRequestDispatcher("/WEB-INF/views/MultiPlayer.jsp").forward(request, response);
                break;

        /***************************************Runde 2 Spieler spielt 3 Themen des Gegners**********************************/                
            case "round2":
                multigameID = Integer.parseInt(request.getParameter("GameRequestID"));
                currentMultigame = new Multigame(multigameID);
                session.setAttribute("multigame", currentMultigame);
                request.setAttribute("otherPlayerName", currentMultigame.getotherPlayerName(currentPlayer.getUser_id()));
                cardset = currentMultigame.getcardset1round2();
                questionID = Helper.IDsStringtoIntArray(cardset);
                request.setAttribute("question", currentMultigame.getQuestion(questionID[0]));
                session.setAttribute("currentThema", currentMultigame.getthema1round2());
                request.setAttribute("next", 2);
                session.setAttribute("round", 2);
                session.setAttribute("points", 0);
                request.setAttribute("answers", currentMultigame.getAnswers(questionID[0]));
                session.setAttribute("done", "not yet");
                request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                break;
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
