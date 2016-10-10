/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.controller;

import de.projekt.model.Karteikarte;
import de.projekt.model.Player;
import de.projekt.model.Werte;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jonas
 */
@WebServlet(name = "SingleplayerController", urlPatterns = {"/SingleplayerController"})
public class SingleplayerController extends HttpServlet implements Werte{
 public static int count;  
 public Karteikarte single;
 public int setsize;
 public static int right=0;
 public static int wrong=0;
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
            throws ServletException, IOException {
        HttpSession session = request.getSession();
            //______________________________ Ausgabe der Karten
        count = (int) session.getAttribute("count");
        single = (Karteikarte) session.getAttribute("single");
        setsize = single.getPlaysetSize();
        int answer = Integer.parseInt(request.getParameter("id"));  // Wenn answer 1 dann wurde die Frage richtig beantwortet, wenn 0 dann falsch
        if(answer==1)    
            right++;
        else
            wrong++;
        Player currentplayer = (Player) session.getAttribute("player");
        currentplayer.setUserAnswer(answer,single.getPlayset(count),single.getPlayer());
        count++;
        if(count>=setsize){ 
            request.setAttribute("right",right);
            request.setAttribute("wrong",wrong);
            session.removeAttribute("count");
            request.getRequestDispatcher("/WEB-INF/views/EndResult.jsp").forward(request, response);
            right = wrong = 0;
        } else{
            //Gibt die jeweilige Frage aus
            String question= single.getQuestion(count);
            String correctAnswer= single.getCorrectAnswer(count);
            session.setAttribute("count", count);
            request.setAttribute("question",question);
            request.setAttribute("answer",correctAnswer);
            request.getRequestDispatcher("/WEB-INF/views/AusgabeSinglePlayer.jsp").forward(request, response);
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
        processRequest(request, response);     
        
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
        processRequest(request, response);
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
