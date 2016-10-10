<%-- 
    Document   : EndResult
    Created on : 27.06.2016, 17:38:46
    Author     : Jonas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
        <link rel="stylesheet" href="css/design.css" />
        <script src="js/jquery-2.2.4.min.js"></script>
        <script src="js/jquery.mobile-1.4.5.min.js"></script>
        <title>Ergebnisse</title>
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

        <h1 align="center">Endergebnis</h1>
        
       <p align="center">
           <%  //Soll das Ergebnis eines Multiplayerspiels oder eines Singleplayerspiels ausgegeben werden
               if(session.getAttribute("playmode").equals("Multiplayer")){
                String gegnername = (String)request.getAttribute("gegnername");
                int pointsPlayer1Round1 = (int)request.getAttribute("pointsplayer1round1");
                int pointsPlayer1Round2 = (int)request.getAttribute("pointsplayer1round2"); 
                int pointsPlayer2Round1 = (int)request.getAttribute("pointsplayer2round1");
                int pointsPlayer2Round2 = (int)request.getAttribute("pointsplayer2round2");
                String winnerRound1 = request.getAttribute("winnerRound1").toString();
                String winnerRound2 = request.getAttribute("winnerRound2").toString();
                String winnerTotal = request.getAttribute("winnerTotal").toString();
                
                //Gebe Tabelle mit den in der Request übergebenen Daten
                out.println("<table data-role=\"table\" class=\"ui-responsive\" align=\"center\">");
                out.println("<thead><tr><th>Runde</th><th>Du</th><th>"+gegnername+"</th><th>Rundensieger</th><tr><thead><tbody>");
                out.println("<tr><td>Punkte Runde 1: </td><td>  "+pointsPlayer1Round1+"</td><td>"    +pointsPlayer2Round1+"</td><td>" + winnerRound1 + "</td>");
                out.println("<tr><td>Punkte Runde 2: </td><td>"  +pointsPlayer1Round2+"</td><td>"    +pointsPlayer2Round2+"</td><td>" + winnerRound2 + "</td>");
                out.println("</tbody></table>");
            %>
       </p>  <p align="center">
            <%
                    if(winnerTotal.equals(gegnername)){
                        out.println("<b>Das war wohl nichts! "+gegnername+" hat gewonnen. </b>");
                    } else if(winnerTotal.equals("unentschieden")){
                        out.println("<b> Unentschieden!</b>");
                    } else{
                        out.println("<b> Glückwunsch! Du hast gewonnen.</b>");
                    }
                }else{
                    int wrong = (int)request.getAttribute("wrong");
                    int right = (int)request.getAttribute("right");
                    int all = wrong+right;
                    out.println("<b>Anzahl der Fragen: "+all+"<br></b>");
                    out.println("<b>Richtig: "+right+"<br></b>");
                    out.println("<b>Falsch: "+wrong+"<br></b>");
                }
            %> 
             </p>
               
            
             
         <div data-role="footer" data-theme="d" id="footer"><h1></h1></div>
    </body>
</html>
