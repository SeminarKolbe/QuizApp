<%-- 
    Document   : MultiAuswahl
    Created on : 03.07.2016, 18:21:46
    Author     : Jonas
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="http://code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.css" />
        <script src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
        <script src="http://code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.js"></script>
        <title>Auswahl</title>
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
        
        <%
            List<String> namen = (List<String>)request.getAttribute("gegnernamen"); 
            int i=1;
        %>
        <form action="VerarbeitungsControllerMulti" method="post">
                    <label>Spieler(in):</label>
                     <select name="top5" size="1">
         
         <% for(String q : namen){
            out.println("<option value=\"1\">"+q+"</option>");
            i++;
         }
         %>                      
                    </select>
                    <button>click</button>  
           </form>         
                    <form action="VerarbeitungsControllerMulti?id=r" method="post">
                     <button>Zufälliger Spieler</button>
                    </form>
    </div>
                    
                    
                    
                    
        <div data-role="footer" data-theme="c" id="footer"><h1></h1></div>  
    </body>
</html>
