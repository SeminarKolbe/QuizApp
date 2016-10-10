<%-- 
    Document   : Statistik
    Created on : 24.07.2016, 15:20:58
    Author     : Jonas
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="de.projekt.model.Kategorie"%>
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
        <title>Statistik</title>
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
            <%
                Player currentPlayer = (Player) session.getAttribute("player");
                ArrayList<String> themen = new Kategorie().getNamesofplayedCategories(currentPlayer.getUser_id());
            %>
            <h2 align="center">Deine Statistik</h2>
            <div data-role="content" style="background-color:#dbdbdb; border-width: 1px; border-style:solid; border-color: black; text-align: center; margin:40px; border-radius:10px;">
            
            <%    
                for(String thema : themen){
                    try{
                        out.print("<h2><u>"+thema+"</u></h2>");
                        out.print("<b>Gespielte Karten: "+(int) request.getAttribute(thema+"gespielt")+"</b><br>");
                        out.print("<b>Richtig:          "+(int) request.getAttribute(thema+"richtig")+"</b><br>");
                        out.print("<b>Falsch:           "+(int) request.getAttribute(thema+"falsch")+"</b><br>");
                        out.print("<b>in Prozent:       "+(double)request.getAttribute(thema+"prozent")+"%</b><br>");
                    }catch(Exception e){
                        
                    }
                }
            %>    
            </div>
        
        
        
        <div data-role="footer" data-theme="d" id="footer"><h1></h1></div>
    </body>
</html>
