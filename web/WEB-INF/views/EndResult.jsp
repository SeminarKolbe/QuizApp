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
        <link rel="stylesheet" href="css/jquery.mobile-1.0.1.min.css" />
        <link rel="stylesheet" href="css/design.css" />
        <script src="js/jquery-1.7.1.min.js"></script>
        <script src="js/jquery.mobile-1.0.1.min.js"></script>
        <title>Ergebnisse</title>
    </head>
    <body>
        
       <div data-role="header" data-theme="b" id="header">            
                <form action="LoginControllerServlet" method="post" class="ui-btn-left">
                    <button data-theme="a">Back</button>
                </form>    
                <h1>  L'Odyssee </h1>           
                <form action="LogoutController" method="post" class="ui-btn-right">
                    <button data-theme="a">Logout</button>
                </form>     
            </div>
        <% String spiel = request.getAttribute("multiausgabe").toString();%>
        <% int wrong = (int)request.getAttribute("wrong");%>
        <% int right = (int)request.getAttribute("right"); %>
        <h1 align="center">Endergebnis</h1>
        
       <p align="center">
            <% if(spiel.equals("multi")){
                String gegnername = (String)request.getAttribute("gegnername");
                int rightplayer1 = (int)request.getAttribute("rightplayer1"); //richtig des Spielers eins
                int wrongplayer1 = (int)request.getAttribute("wrongplayer1"); // Flasch des Spielers eins
                int allplayer1 =wrongplayer1+rightplayer1; //Alle Karten die gespielt wurden
                int all =wrong+right;
                out.println("<table align=\"center\">");
                out.println("<tr><th></th><th>  Du    </th><th>"+gegnername+"</th><tr>");
                out.println("<tr><td>Richtig gemacht: </td><td>  "+right+"</td><td>"    +rightplayer1+"</td>");
                out.println("<tr><td>Falsch gemacht: </td><td>"  +wrong+"</td><td>"    +wrongplayer1+"</td>");
                out.println("</table>");
            %>
       </p>  <p align="center">
            <%
                if(right>rightplayer1)
                    out.println("<b> Glückwunsch! Du hast gewonnen.</b>");
                if(right<rightplayer1)
                    out.println("<b>Das war wohl nichts! "+gegnername+" hat gewonnen. </b>");
                if(right==rightplayer1)
                    out.println("<b> Unentschieden!</b>");
                %>
           
            <%
            }else{
            int all = wrong+right;
            out.println("<b>Anzahl der Fragen: "+all+"<br></b>");
            out.println("<b>Richtig: "+right+"<br></b>");
            out.println("<b>Falsch: "+wrong+"<br></b>");
            }
        %> 
             </p>
        <img src="Bilder/logo.JPG">
               
            
             
         <div data-role="footer" data-theme="c" id="footer"><h1></h1></div>
    </body>
</html>
