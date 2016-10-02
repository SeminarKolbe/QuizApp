/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.controller;

import de.projekt.model.InsertDatenbank;
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
        System.out.println("hier bin ich SingleplayerController. count: " + count + "\nsetsize: " + setsize);
        int answer = Integer.parseInt(request.getParameter("id"));  // Wenn answer 1 dann wurde die Frage richtig beantwortet, wenn 0 dann falsch
        if(answer==1)    
            right++;
        else
            wrong++;
        InsertDatenbank m = new InsertDatenbank(answer,single.getPlayset(count),single.getPlayer());
        try{
            m.insertDatenbankAnswer();
        }catch(Exception e){
           System.out.println(e);
        } 
        count++;
        if(count>=setsize){ 
            String multiausgabe="single";
            request.setAttribute("right",right);
            request.setAttribute("wrong",wrong);
            request.setAttribute("multiausgabe",multiausgabe);
            request.getRequestDispatcher("/WEB-INF/views/EndResult.jsp").forward(request, response);
        } else{
            //Gibt die jeweilige Frage aus
            String question= single.getQuestion(count);
            String correctAnswer= single.getCorrectAnswer(count);
            System.out.println("SingleplayerController count: " + count + "\nSingleplayerController question: " + question + "\nVerarbeitungsCongrollerSingle answer: " + answer);
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
