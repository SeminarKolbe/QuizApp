/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.controller;

import de.projekt.model.Multigame;
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
        String strCategoryParameter =request.getParameter("category");  // Jeder Spielmodus hat eine bestimmte Zahl
        HttpSession session = request.getSession();
        
        switch(strCategoryParameter) {
            
/***************************************SinglePlayer-Modus ausgewählt*******************************/
            case "3": 
                session.setAttribute("playmode", "Singleplayer");
                request.getRequestDispatcher("/WEB-INF/views/category.jsp").forward(request, response);
                break;
            
/***************************************MultiplayerModus ausgewählt*********************************/
            case "2":
                Player player = (Player) session.getAttribute("player");
                Multigame playermultigame = new Multigame();
                session.setAttribute("playmode", "Multiplayer");
                
                try{
                    //liegen Anfragen für die erste oder zweite Runde vor?
                    ArrayList<Integer> GameRequestIDsround1 = playermultigame.getGameRequests(player.getUser_id(), 1);
                    ArrayList<Integer> GameRequestIDsround2 = playermultigame.getGameRequests(player.getUser_id(), 2);
                    List<String> names1 =new ArrayList<String>();
                    List<String> names2 = new ArrayList<String>();
                    //Spielernamen des anderen Teilnehmers am Multigame
                    for(int i=0; i < GameRequestIDsround1.size(); i++){
                        Multigame actualMultigame = new Multigame(GameRequestIDsround1.get(i));
                        names1.add(actualMultigame.getotherPlayerName(player.getUser_id()));
                    }
                    for(int i=0; i < GameRequestIDsround2.size(); i++){
                        Multigame actualMultigame = new Multigame(GameRequestIDsround2.get(i));
                        names2.add(actualMultigame.getotherPlayerName(player.getUser_id()));
                    }
                    //Die MultigameIDs und dazugehörige Namen in der Request-Anfrage hinterlegen
                    request.setAttribute("GameRequestIDsround1", GameRequestIDsround1);
                    request.setAttribute("names1", names1); // übergibt einen Namen mit dem String des Gegners
                    request.setAttribute("GameRequestIDsround2", GameRequestIDsround2);
                    request.setAttribute("names2", names2);
                }catch(Exception e){
                    System.out.println("CategoryController.java / Bin in der Exception, als ich probiert habe die Anfragen für die Multigames zu laden");
                    System.out.println(e);
                }
                //Um ein Multigame zu erstellen werden die anderen Spielernamen in der Request hinterlegt
                ArrayList<String> gegnernamen = player.getOpponentsNames();
                request.setAttribute("gegnernamen", gegnernamen);
                request.getRequestDispatcher("/WEB-INF/views/MultiAuswahl.jsp").forward(request, response);
                break;
                
/*********************************Falscher CategoryParameter*********************************************/
            default: throw new IllegalArgumentException("Nicht erkannter CategoryParameter!");      
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
