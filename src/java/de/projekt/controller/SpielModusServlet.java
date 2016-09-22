/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.controller;

import de.projekt.model.Multi;
import de.projekt.model.Player;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author Jonas
 */
@WebServlet(name = "SpielModusServlet", urlPatterns = {"/SpielModusServlet"})
public class SpielModusServlet extends HttpServlet {

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
        System.out.println("Bin im SpielModusServlet");
        String strCategoryParameter =request.getParameter("category");  // Jeder Spielmodus hat eine bestimmte Zahl
        System.out.println("Nummer der Kategorie: "+strCategoryParameter);
        if(strCategoryParameter.equals("3")){ // Falls der Spieler den Einzelmodus auswählt, wird er zu den Kategorien weitergeleitet
           request.getRequestDispatcher("/WEB-INF/views/category.jsp").forward(request, response);
        }
        
        
        
        /*
        *
        * Muss noch im MultiAuswahl implementiert werden
        */
        if(strCategoryParameter.equals("2")){// Falls der Spieler den Mehrspielermodus auswählt, wird er zu Spielerauswahl weitergeleitet
                  //Überprüft ob ein Spieler eine Anfrage hat  
                  HttpSession session = request.getSession();
                  System.out.println("ControllerCategory Session: " + session);
                  Player player = (Player) session.getAttribute("player");
                  Multi ueberpruefen= new Multi(player);
                  System.out.println("ControllerCategory Player: " + (Player)session.getAttribute("player") + "\n" + "ueberpruefen: " + ueberpruefen);  
                
                try{
                    System.out.println("1.");
                    int[] anfrageid= ueberpruefen.checkAnfrage(); // Guckt ob für den Spieler Spielanfargen vorliegen
                    List <String> name =new ArrayList<String>();
                    System.out.println("2.");
                    for(int i=0;i<anfrageid.length;i++){
                        name.add(ueberpruefen.getPlayer(anfrageid[i]));  // Sucht anhander der Game-Id eines Duell den Passenden Spieler und gibt diesen als String zurück
                    }
                    System.out.println("3.");
                    request.setAttribute("anfrageid", anfrageid);  //übergeben der einzelnen id's der Multiplayerspiele
                    request.setAttribute("anfrage", name); // übergibt einen Namen mit dem String des Gegners
                }catch(Exception e){
                    System.out.println("ControllerCategory.java / Bin in der Exception, als ich probiert habe die Anfragen für die Multigames zu laden");
                    System.out.println(e);
                }
                ArrayList<String> gegnernamen = player.getOpponentsNames();
                request.setAttribute("gegnernamen", gegnernamen);
                request.getRequestDispatcher("/WEB-INF/views/MultiAuswahl.jsp").forward(request, response);
            
        }
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
