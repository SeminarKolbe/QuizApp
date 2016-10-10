/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.controller;

import de.projekt.model.Player;
import de.projekt.model.Multigame;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "ErgebnisController", urlPatterns = {"/ErgebnisController"})
public class ErgebnisController extends HttpServlet {
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
        Player currentPlayer=(Player) session.getAttribute("player");
        Multigame currentMultigame = (Multigame)session.getAttribute("multigame");
        int userid = currentPlayer.getUser_id();
        int pointsplayer1round1 = currentMultigame.getPointsPlayer1Round1();
        int pointsplayer1round2 = currentMultigame.getPointsPlayer1Round2();
        int pointsplayer2round1 = currentMultigame.getPointsPlayer2Round1();
        int pointsplayer2round2 = currentMultigame.getPointsPlayer2Round2();
        String winnerRound1, winnerRound2;


        request.setAttribute("gegnername",currentMultigame.getotherPlayerName(userid));
        request.setAttribute("pointsplayer1round1",pointsplayer1round1);
        request.setAttribute("pointsplayer1round2",pointsplayer1round2);
        request.setAttribute("pointsplayer2round1",pointsplayer2round1);
        request.setAttribute("pointsplayer2round2",pointsplayer2round2);
        request.getRequestDispatcher("/WEB-INF/views/EndResult.jsp").forward(request, response); 
        return;

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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ErgebnisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ErgebnisController.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ErgebnisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ErgebnisController.class.getName()).log(Level.SEVERE, null, ex);
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

