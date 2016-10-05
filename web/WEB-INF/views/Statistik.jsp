<%-- 
    Document   : Statistik
    Created on : 24.07.2016, 15:20:58
    Author     : Jonas
--%>

<%@page import="java.util.List"%>
<%@page import="de.projekt.model.DatenbankZugang"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
        <title>Statistik</title>
    </head>
    <body>
          <div data-role="header" data-theme="b" id="header">            
                <form action="LoginControllerServlet?id=10" method="post" class="ui-btn-left">
                    <button data-theme="a">Back</button>
                </form>    
                <h1>  L'Odyssee </h1>           
                <form action="LogoutController" method="post" class="ui-btn-right" data-transition="slide" data-direction="reverse">
                    <button data-theme="a">Logout</button>
                </form>     
            </div>
            <%
                DatenbankZugang db =new DatenbankZugang();
                List <String> list= db.getThemaAll();
            %>
            <div data-role="content" style="background-color:#dbdbdb; border-width: 1px; border-style:solid; border-color: black; text-align: center; margin:40px; border-radius:10px;">
            
            <%    
                for(String thema : list){
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
        
        
        
        <div data-role="footer" data-theme="c" id="footer"><h1></h1></div>
    </body>
</html>
