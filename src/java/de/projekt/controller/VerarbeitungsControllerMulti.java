/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.controller;

import de.projekt.model.AntwortenUebergabe;
import de.projekt.model.DatenbankZugang;
import de.projekt.model.Frage;
import de.projekt.model.Karteikarte;
import de.projekt.model.Multi;
import de.projekt.model.MultiPlayerKarten;
import de.projekt.model.Player;
import de.projekt.model.ResultBerechnenMulti;
import de.projekt.model.Werte;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "VerarbeitungsControllerMulti", urlPatterns = {"/VerarbeitungsControllerMulti"})
public class VerarbeitungsControllerMulti extends HttpServlet implements Werte {
public static int i=0;
public Frage frage;
public List<Frage> set;
String namegegner;
public static String answercheck="0";
String[] userantworten;
public String username1="";
public static int wrong=0;
public static int right=0;
public int[] numbercards; // Int-Array mit den Id's der Karten die gespielt werden;
public int cardsetid; // id des Cardsets welches gesoielt wird 
public static int spielerzug=0; // Wenn spielerzug 1 ist, ist der zweite Spieler am Zug. Variable wird nur im ersten Schritt verändert
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
      
   //___________Anfrage eines anderen Spielers verarebeiten     
        try{
         String [] help =request.getParameterValues("top5");
         String anfrage=help[0];  // ist die Id des Cardsets welches gepielt wird
         cardsetid=Integer.parseInt(anfrage);
         
         for(int w=0;w<help.length;w++){
            
         }
          Multi g =new Multi((Player) session.getAttribute("player"));
          namegegner = g.getNamePlayer1(cardsetid);
          numbercards=g.getCardSet(anfrage);    // gibt einen int-Array mit den Karten-ids aus
          MultiPlayerKarten mult =new MultiPlayerKarten();
          set=mult.generateGame(numbercards); // erzeugt ein Set aus Fragen
          spielerzug=1;
          i=2;
           
       }catch(Exception e){
      
           System.out.println(e);
       } 
        
        
        try{ 
      
       
       //___Falls der Spieler die Kategorie gewählt hat, soll ihm alle Spieler angezeigt werden
       
       if(i==0){
                Multi m =new Multi((Player) session.getAttribute("player"));
                List<String> namen= m.getUser();
                i++;
                request.setAttribute("name", namen);
                request.getRequestDispatcher("/WEB-INF/views/MultiAuswahl.jsp").forward(request, response);
                return;
        }
        //_____Hier wird der Benutzer überprüft und Karten erzeugt
        if(i==1){
            int id=-2;   
            String help= request.getParameter("id");
            if(help.equals("r"))  // r = random
                id=-1;
            Multi n =new Multi((Player) session.getAttribute("player"));
            numbercards= n.getGame(id);      
            MultiPlayerKarten mult =new MultiPlayerKarten();
            set=mult.generateGame(numbercards);
            namegegner=n.getUsername();
            i++;
 //______Hier folgt die Ausgabe ___    
        }if(i>1){
            
            if(i>2){
                
                answercheck= (String)request.getParameter("res");
                userantworten= request.getParameterValues("antwort"); 
      
            }    
           
            
            if(answercheck.equals("1")){  // Das Servlet soll gucken ob die Antworten richtig sind und dme Benutzer die JSP zurückliefern, ohne eine neue Frage
                frage=set.get(i-3);
                //___überprüfen ob die Antwort richtig ist
                
                
                
                request.setAttribute("namegegner", namegegner);
                request.setAttribute("frage",frage.getQuestion());
                request.setAttribute("antwor1", frage.getAnswer1());
                request.setAttribute("antwor2", frage.getAnswer2());
                request.setAttribute("antwor3", frage.getAnswer3());
                request.setAttribute("antwor4", frage.getAnswer4());
                request.setAttribute("antwor5", frage.getAnswer5());
                request.setAttribute("correct1", Integer.toString(frage.getCorrectanswer()));
                request.setAttribute("correct2", Integer.toString(frage.getCorrectanswer2()));
                request.setAttribute("correct3", Integer.toString(frage.getCorrectanswer3()));
                request.setAttribute("correct4", Integer.toString(frage.getCorrectanswer4()));
                request.setAttribute("correct5", Integer.toString(frage.getCorrectanswer5()));
                int f;
                int [] santworten =new int[userantworten.length];  // Die Antworten die der Nutzer gegeben hat, werden Sortiert und ausgegeben
               
                // Es wird geguckt, ob alle angekreutzen Kästchen richtig sind und speichert die Ergebnisse für das Endresult
                for(f=0;f<userantworten.length;f++){
                    if(userantworten[f].equals("a1")){
                        santworten[f]=1;
                    }if(userantworten[f].equals("a2")){
                        santworten[f]=2;
                    }if(userantworten[f].equals("a3")){
                        santworten[f]=3;
                    }if(userantworten[f].equals("a4")){
                        santworten[f]=4;
                    }if(userantworten[f].equals("a5")){
                        santworten[f]=5;
                    }
                }
                ResultBerechnenMulti result = new ResultBerechnenMulti(santworten, frage.getCorrectanswer(), frage.getCorrectanswer2(), frage.getCorrectanswer3(), frage.getCorrectanswer4(), frage.getCorrectanswer5());
                if(result.WrongRight()==true){
                    right++;      
                }else{
                    wrong++;
                }    
                
            
                AntwortenUebergabe hilfe = new AntwortenUebergabe(santworten);
                request.setAttribute("santworten",hilfe);
                request.setAttribute("abgegeben", Integer.toString(f));
                request.setAttribute("checkanswer", "1");
                
                request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);    
                return;
       //______Letzter Schritt vor der Ausgabe des Endergebnisses 
            }if(i==anzahlmulticards+2){
                  
                  request.setAttribute("right",right);
                  request.setAttribute("wrong",wrong);
                  try{
                       username1= (String) session.getAttribute("userName"); 
                  }catch(Exception e){
                      
                  }
                      Multi resultabfrage =new Multi((Player) session.getAttribute("player"));
                  if(spielerzug==1){// Falls ein Duell gespielt wird und der zweite Spieler am Zug ist
                       resultabfrage.getResult2(cardsetid, right, wrong, username1);        //updatet die Datenbank und legt das Ergebnis des Spielers ab
                       request.setAttribute("rightplayer1",resultabfrage.getRightPlayer1(cardsetid));
                       request.setAttribute("wrongplayer1",resultabfrage.getWrongePlayer1(cardsetid));
                       request.setAttribute("multiausgabe","multi");
                       request.setAttribute("gegnername",namegegner); 
                  }else{
                      resultabfrage.getResult1(resultabfrage.getGameId(), right, wrong);
                      request.setAttribute("multiausgabe","einzel");
                  }
                   DatenbankZugang db=new DatenbankZugang();
                   db.setPoints((String)session.getAttribute("userName"), right);
                  request.getRequestDispatcher("/WEB-INF/views/EndResult.jsp").forward(request, response);  
                  return;
            }
            
       //______________________________________________     
            
            frage=set.get(i-2);
         
            request.setAttribute("namegegner", namegegner);
            request.setAttribute("frage",frage.getQuestion());
            request.setAttribute("antwor1", frage.getAnswer1());
            request.setAttribute("antwor2", frage.getAnswer2());
            request.setAttribute("antwor3", frage.getAnswer3());
            request.setAttribute("antwor4", frage.getAnswer4());
            request.setAttribute("antwor5", frage.getAnswer5());
            request.setAttribute("checkanswer", "0");  
          
            if(!answercheck.equals("1")){
                i++;
       
                request.getRequestDispatcher("/WEB-INF/views/AusgabeMultiplayer.jsp").forward(request, response);
                return;
            }
        }
            }catch(Exception e){
                System.out.println(e);
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
        }catch(Exception e){}
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
