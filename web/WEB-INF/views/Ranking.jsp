<%-- 
    Document   : Ranking
    Created on : 24.07.2016, 16:48:38
    Author     : Jonas
--%>

<%@page import="java.util.List"%>
<%@page import="de.projekt.model.Player"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
        <link rel="stylesheet" href="css/design.css" />
        <script src="js/jquery-2.2.4.min.js"></script>
        <script src="js/jquery.mobile-1.4.5.min.js"></script>
        <title>Ranking</title>
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
    <h2 align="center">Ranking</h2>
        <div data-role="content"  style="background-color:#dbdbdb; border-width: 1px; border-style:solid; border-color: black; text-align: center; margin:40px; border-radius:10px;">
        <%
            //Gebe eine Liste mit den Spieler nach Punkten sortiert aus
            Player currentplayer = (Player) session.getAttribute("player");
            List <Player> list = currentplayer.getBestPoints();
            int h=1;
            for(Player i:list){
                out.print("<b>"+h+") "+i.getName()+" Punkte: "+i.getPoints()+"</b><br>");
                h++;
            }
        
        %>
        </div>
         <div data-role="footer" data-theme="d" id="footer"><h1></h1></div>
    </body>
</html>
