/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.controller;
import de.projekt.model.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginControllerServlet extends HttpServlet {

    
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //neue Instanz von der Klasse UserValidation
    private UserValidation zugriff = new UserValidation();
    private int aufruf=0;
    private String userName;
    private String password;
    
    // Nimmt den Login entgegen
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
     //___________Zurück-Button
     HttpSession sessio = request.getSession();
        try{
            password= sessio.getAttribute("password").toString();
            userName= sessio.getAttribute("userName").toString();
            ControllerCategory.thema = null;  // muss auf null gesetzt werden, da sonst beim ControllerCategory ein Fehler auftritt
            SingleplayerController.wrong=0;
            SingleplayerController.right=0;
            SingleplayerController.count=0;
        }catch(Exception e){
            System.out.println(e);
            userName = request.getParameter("userName");
            password = request.getParameter("password");
        }
      //_______________________-      
        boolean isUserValid = zugriff.isUserValid(userName, password);  // überprüft ob der User in der Datenbank exsistiert
        
        if(isUserValid) {
            sessio.setAttribute("userName", this.userName);
            sessio.setAttribute("password", this.password);
            //zugriff.close();
            //________Login-Admin__________
             if(userName.equals("admin")){  
                    request.getRequestDispatcher("/WEB-INF/views/categoryAdmin.jsp").forward(request, response);
                    return;
             }else{
                    Player player = new Player(userName); // in Player-Klasse soll die UserId für den userName zurückgegeben werden
             
                    sessio.setAttribute("player", player); // und in der Session gespeichert werden
                    
                    request.getRequestDispatcher("/WEB-INF/views/Spielmodiwahl.jsp").forward(request, response);
                   // request.getRequestDispatcher("/WEB-INF/views/category.jsp").forward(request, response);
                    return;
            }
        //____ Falls User nicht bekannt______     
        }else{
            
                aufruf++; 
            //__ Beim ersten Aufruf der Seite soll keine Fehlermeldung ausgegeben werden, da der USer noch keine Daten eingegeben hat    
            if(aufruf==1){
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                return;
            }    
        //____Falls flasche Daten eingegeben wurden       
         request.setAttribute("errorMessage", "Falsche Benutzerdaten!");
         request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
         return;
         }
    }
       
                
                
   
    
    
    
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         try{
        processRequest(request, response);
         }catch(Exception e){}

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
