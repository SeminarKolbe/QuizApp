/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.controller;

import de.projekt.model.Karteikarte;
import de.projekt.model.Player;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

@WebServlet(name = "CategoryController", urlPatterns = {"/CategoryController"})
public class CategoryController extends HttpServlet {
       public static String thema=null;
       Karteikarte single;
       public int setsize;
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
        Player player=(Player) session.getAttribute("player");
        //Karte erstellen oder Thema gewählt?
        if(request.getParameter("category").equals("NewNotecard")){
            request.getRequestDispatcher("/WEB-INF/views/NewNotecard.jsp").forward(request,response);
        }else{
            String thema = request.getParameter("category");
            //Erstellen der Karten anhand des gewählten Thema
            single = new Karteikarte(thema, player, "single");
            session.setAttribute("single", single);
            session.setAttribute("count", 0);
            request.setAttribute("answer",single.getCorrectAnswer(0));
            request.setAttribute("question",single.getQuestion(0));
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
        try{
            processRequest(request, response);
        }catch(Exception e){
            System.out.println(e);
        }
    } 
        
     
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
    
    private void forwardToErrorView(HttpServletRequest request, HttpServletResponse response, String errorMessage) 
            throws ServletException, IOException{
        /* Fehlermeldung im Request-Scope hinterlegen, damit die View sie anzeigen kann: */
		request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("/WEB-INF/views/category.jsp").forward(request, response);
    } 

}
