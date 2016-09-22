/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.controller;

import de.projekt.model.DatenbankZugang;
import de.projekt.model.Multi;
import de.projekt.model.Player;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
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
 * @author Jonas
 */
@WebServlet(name = "StatistikController", urlPatterns = {"/StatistikController"})
public class StatistikController extends HttpServlet {

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
        String username=(String) session.getAttribute("userName");
        DatenbankZugang db =new DatenbankZugang();
        Player player = new Player(username);
        int userid= player.getIdPlayer();
        
        List <String> list= db.getThema();
        for(String thema : list){
            int gespielt= player.getGespielteKarten(thema,userid);
            int richtig = db.getRichtigGespielteKarten(thema, userid);
            int falsch =db.getFalschGespielteKarten(thema, userid);
            double prozent=0;
            try{
                prozent= ((double) richtig/gespielt);
                prozent = prozent*10000;
                prozent = Math.round(prozent);
                prozent = prozent / 100;
                
            }catch(Exception e){
              
               System.out.println(e);
               prozent=0; 
            }    
        request.setAttribute(thema+"prozent", prozent);
        request.setAttribute(thema+"gespielt", gespielt);  // wie viel Karten du zu dem Thema schon gepielt hast z.B. Mathegespielt
        request.setAttribute(thema+"falsch", falsch);
        request.setAttribute(thema+"richtig", richtig);
        }    
        request.getRequestDispatcher("/WEB-INF/views/Statistik.jsp").forward(request, response);
    
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
            Logger.getLogger(StatistikController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StatistikController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(StatistikController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StatistikController.class.getName()).log(Level.SEVERE, null, ex);
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
