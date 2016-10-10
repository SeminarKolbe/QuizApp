/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.controller;

import de.projekt.model.Karteikarte;
import de.projekt.model.Player;
import java.io.IOException;
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

@WebServlet(name = "NewNotecardController", urlPatterns = {"/NewNotecardController"})
public class NewNotecardController extends HttpServlet {
    
    
    
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
        Player player = (Player) session.getAttribute("player");
        String create = request.getParameter("create");
        //Bei RequesParameter "create" werden die nötigen Daten aus dem Request geholt und auf DB gespeichert. Gilt nur für SingleChoice-Fragen
        if(create.equals("true")){
            String thema = request.getParameter("thema");
            String question = request.getParameter("question");
            String correctAnswer = request.getParameter("correctAnswer");
            Karteikarte newNotecard = new Karteikarte();
            newNotecard.createNotecard(thema, question, correctAnswer, player.getUser_id());
            request.getRequestDispatcher("/WEB-INF/views/success.jsp").forward(request, response);

        } else request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
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
