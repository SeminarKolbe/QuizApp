<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : MultiAuswahl
    Created on : 03.07.2016, 18:21:46
    Author     : Jonas
--%>

<%@page import="java.util.List"%>
<%@page import="de.projekt.model.Multigame"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.0.1.min.css" />
        <link rel="stylesheet" href="css/design.css" />
        <script src="js/jquery-1.7.1.min.js"></script>
        <script src="js/jquery.mobile-1.0.1.min.js"></script>
        <title>Auswahl Multigame</title>
    </head>
    <body>
        <div data-role="header" data-theme="b" id="header">            
                <form action="LoginControllerServlet" method="post" class="ui-btn-left">
                    <button data-theme="a">Back</button>
                </form>    
                <h1>  L'Odyssee </h1>           
                <form action="LogoutController" method=""post class="ui-btn-right">
                    <button data-theme="a">Logout</button>
                </form>     
            </div>
        
        
        <div data-role="content" style="margin:10px">

            <h2> Wähle einen Gegner(in): </h2>
            <%
                List<String> namen = (List<String>)request.getAttribute("gegnernamen");
                List<String> names1 = (List<String>)request.getAttribute("names1");
                List<String> names2 = (List<String>)request.getAttribute("names2");
                List<Integer> GameRequestIDsround1 = (List<Integer>)request.getAttribute("GameRequestIDsround1");
                List<Integer> GameRequestIDsround2 = (List<Integer>)request.getAttribute("GameRequestIDsround2");
            %>
                <form action="MultiplayerController?id=pickOpponent" method="post">
                        <label>Spieler(in):</label>
                        <select name="gegnerName" size="1">
                    <% 
                        for(String q : namen){
                            out.println("<option value="+q+">"+q+"</option>");
                        }
                    %>                      
                        </select>
                        <button>Zur Themenwahl</button>  
                </form>         
                <form action="MultiplayerController?id=randomOpponent" method="post">
                     <button>Zufälliger Spieler</button>
                </form>
          
            <h3> Anfragen für dich: </h3>
                <form action="MultiplayerController?id=round1" method="post">
                    <label>Hier fordert dich jemand heraus! Zeig ihm, dass du der bessere bist!</label>
                    <select name="GameRequestID" size="1">
                    <% 
                        if(names1.size() != 0){
                            for(int a = 0; a < names1.size(); a++){
                                out.println("<option value=" + GameRequestIDsround1.get(a) + ">"+names1.get(a)+"</option>");
                            }
                        }
                    %> 
                    </select>
                    <button>Zur Herausforderung</button>
                </form>
                <form action="MultiplayerController?id=round2" method="post">
                    <label>Jemand hat deine Herausforderung angenommen und kontert mit neuen Themen!
                        Nimmst du die Herausforderung an?</label>
                    <select name="GameRequestID" size="1">
                    <% 
                        if(names2.size() != 0){
                            for(int a = 0; a < names2.size(); a++){
                                out.println("<option value=" + GameRequestIDsround2.get(a)+">"+names2.get(a)+"</option>");
                            }
                        }
                    %>                       
                    </select>
                    <button>Zu den Fragen</button>
                </form>
                
        </div>
              
                    
                    
                    
        <div data-role="footer" data-theme="c" id="footer"><h1></h1></div>  
    </body>
</html>
