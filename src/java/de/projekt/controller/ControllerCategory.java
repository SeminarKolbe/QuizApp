/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.controller;

import de.projekt.model.Karteikarte;
import de.projekt.model.Multi;
import de.projekt.model.Player;
import de.projekt.model.DatenbankZugang;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
 * @author Shaun
 */
@WebServlet(name = "ControllerCategory", urlPatterns = {"/ControllerCategory"})
public class ControllerCategory extends HttpServlet {
       public static String thema=null;

       /*final String JDBC_TREIBER = "com.mysql.jdbc.Driver";
       final String JDBC_URL = "jdbc:mysql://127.0.0.1:3307/mob164db";
       final String JDBC_USER = "mob164";      // Hier Wert eintragen!
       final String JDBC_PASSWORD = "S!ya0V8scj";  // Hier Wert eintragen! // Hier Wert eintragen!*/
       Connection con= null;
       DatenbankZugang dbconnection = new DatenbankZugang();
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

        String kategoriename = request.getParameter("category");
        request.setAttribute("category", thema);
        request.getRequestDispatcher("VerarbeitungsControllerSingle").forward(request, response);
        return;
           
                    
           
            
        
        /*Wenn die Variable Thema nicht 0 ist, wurde schon eine Kategorie ausgewählt. Dann Soll noch bestimmt werden, ob es sich
            um einen Single- oder multi-player handelt.
       */
  // Sobald der Spieler weiter zur Spielmodiwahl geleitet wurde, ist das Thema festgelegt. Da der ControllerCategory auch desen Anfragen verarbeitet
  // wird er automatisch in die else geleitet
            
           
        
    } /*
        
     
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
             
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
