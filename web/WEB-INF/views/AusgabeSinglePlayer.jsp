<%-- 
    Document   : AusgabeSinglePlayer
    Created on : 16.06.2016, 18:35:20
    Author     : Jonas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/design.css" />
        <link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
        <script src="js/jquery-1.7.1.min.js"></script>
        <script src="js/jquery.mobile-1.4.5.min.js"></script>
        <title>Singleplayer</title>
    </head>
    <body>
            <div data-role="header" data-theme="b" id="header">            
                <form action="LoginControllerServlet" method="post" class="ui-btn-left" data-transition="slide" data-direction="reverse">
                    <button data-theme="a">Back</button>
                </form>    
                <h1>  L'Odyssee </h1>           
                <form action="LogoutController" method="post" class="ui-btn-right" data-transition="slide" data-direction="reverse">
                    <button data-theme="a">Logout</button>
                </form>     
            </div>
        
        
        
        <% 
            String question = (String)request.getAttribute("question");
            String answer = (String)request.getAttribute("answer");
            
        %>
         
        <script>
            function buttonAnswer(answer){
                alert(answer);
            }
        </script>    
       <div data-role="content" style="margin:10px"> 
        <h2>Beantworte folgende Frage:</h2>
        <p align="center">
            <img src="images/kartei.jpg">
            <b>
            <%out.println(question);%>
             </b>

        </p>
        
                <button ONCLICK="buttonAnswer(<%out.println(answer);%>)">Antwort anzeigen</button>
                <form action="SingleplayerController?id=1" method="post">
                    <button>richtig</button>     
                </form> 
                <form action="SingleplayerController?id=0" method="post">
                    <button>falsch</button>     
                </form> 
       </div>        
               
               
                
                <div data-role="footer" data-theme="c" id="footer"><h1></h1></div>
    </body>
</html>
