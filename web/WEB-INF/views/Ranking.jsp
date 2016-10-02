<%-- 
    Document   : Ranking
    Created on : 24.07.2016, 16:48:38
    Author     : Jonas
--%>

<%@page import="java.util.List"%>
<%@page import="de.projekt.model.Player"%>
<%@page import="de.projekt.model.DatenbankZugang"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.0.1.min.css" />
        <link rel="stylesheet" href="css/design.css" />
        <title>Ranking</title>
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
        
        <div data-role="content"  style="background-color:#dbdbdb; border-width: 1px; border-style:solid; border-color: black; text-align: center; margin:40px; border-radius:10px;">
        <%
          
        DatenbankZugang db =new DatenbankZugang();
        List <Player> list = db.getBestPoints();
        int h=1;
        for(Player i:list){
            out.print("<b>"+h+") "+i.getName()+" Punkte: "+i.getPoints()+"</b><br>");
            h++;
        }
        
        %>
        </div>
         <div data-role="footer" data-theme="c" id="footer"><h1></h1></div>
    </body>
</html>
