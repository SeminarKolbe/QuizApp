<%-- 
    Document   : MultiAuswahl
    Created on : 01.10.2016, 19:46:51
    Author     : Jonas
--%>

<%@page import="java.util.List"%>
<%@page import="de.projekt.model.Multigame"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
        <link rel="stylesheet" href="css/design.css" />
        <script src="js/jquery-2.2.4.min.js"></script>
        <script src="js/jquery.mobile-1.4.5.min.js"></script>
        <title>Auswahl Multigame</title>
    </head>
    <body>
  <!-- HEADER-->
        <div data-role="header" data-theme="d" id="header" class="ui-header">
            <div class="ui-alt-icon">
            <a href="LoginControllerServlet" data-transition="slide" class="ui-btn-left ui-btn-corner-all ui-btn ui-icon-home ui-btn-icon-notext ui-shadow" data-direction="reverse" data-form="ui-icon" data-role="button"></a>
            <h1 class="ui-title">  Quizmania </h1>
            <a href="LogoutController" data-transition="slide" class="ui-btn-right ui-btn-corner-all ui-btn ui-icon-action ui-btn-icon-notext ui-shadow" data-direction="reverse" data-form="ui-icon" data-role="button"></a>  
            </div>
        </div>
    <!-- /HEADER-->
        
        
        <div data-role="content" style="margin:10px">

            <h2> Wähle einen Gegner(in): </h2>
            <%
                List<String> namen = (List<String>)request.getAttribute("gegnernamen");
                List<String> names1 = (List<String>)request.getAttribute("names1");
                List<String> names2 = (List<String>)request.getAttribute("names2");
                List<Integer> GameRequestIDsround1 = (List<Integer>)request.getAttribute("GameRequestIDsround1");
                List<Integer> GameRequestIDsround2 = (List<Integer>)request.getAttribute("GameRequestIDsround2");
                String disabled = "enable";
            %>
                <form action="MultiauswahlController?id=pickOpponent" method="post" data-transition="slide">
                        <label>Spieler(in):</label>
                        <select name="gegnerName" size="1">
                    <% 
                        for(String q : namen){
                            out.println("<option value="+q+" aria-haspopup=\"true\" data-form=\"ui-btn-up-a\" data-rel=\"popup\">"+q+"</option>");
                        }
                    %>                      
                        </select>
                        <button>Zur Themenwahl</button>  
                </form>         
                <form action="MultiauswahlController?id=randomOpponent" method="post">
                     <button>Zufälliger Spieler</button>
                </form>
          
            <h3> Anfragen für dich: </h3>
                <form action="MultiauswahlController?id=round1" method="post" data-transition="slide">
                    <label>Hier fordert dich jemand heraus! Zeig ihm, dass du der bessere bist!</label>
                    <select name="GameRequestID" size="1">
                    <% 
                        if(names1.size() != 0){
                            for(int a = 0; a < names1.size(); a++){
                                out.println("<option value=" + GameRequestIDsround1.get(a) + ">"+names1.get(a)+"</option>");
                            }
                        }else{ disabled = "disabled";}
                    %> 
                    </select>
                    <button <%=disabled%> >Zur Herausforderung</button>
                </form>
                <form action="MultiauswahlController?id=round2" method="post" data-transition="slide">
                    <label>Jemand hat deine Herausforderung angenommen und kontert mit neuen Themen!
                        Nimmst du die Herausforderung an?</label>
                    <select name="GameRequestID" size="1">
                    <% 
                        disabled = "enable";
                        if(names2.size() != 0){
                            for(int a = 0; a < names2.size(); a++){
                                out.println("<option value=" + GameRequestIDsround2.get(a)+">"+names2.get(a)+"</option>");
                            }
                        } else { disabled = "disabled"; }
                    %>                       
                    </select>
                    <button <%=disabled%>>Zu den Fragen</button>
                </form>
                
        </div>
              
                    
                    
                    
        <div data-role="footer" data-theme="d" id="footer"><h1></h1></div>  
    </body>
</html>
