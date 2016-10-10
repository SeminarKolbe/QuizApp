<%-- 
    Document   : AusgabeSinglePlayer
    Created on : 27.07.2016, 18:35:20
    Author     : Jonas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/design.css" />
        <link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
        <script src="js/jquery-2.2.4.min.js"></script>
        <script src="js/jquery.mobile-1.4.5.min.js"></script>
        <title>Singleplayer</title>
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
            String question = (String)request.getAttribute("question");
            String answer = (String)request.getAttribute("answer");
        %>
        
        <script>
            function alertAnswer(answer){
                alert(answer);
            }
        </script>
           
       <div data-role="content" style="margin:10px"> 
        <h2>Beantworte folgende Frage:</h2>
        <p align="center">
            <b>
            <%out.println(question);%>
             </b>

        </p>
        
        <button onclick="alertAnswer('<%=answer%>')" type="submit">Antwort anzeigen</button>
                <form action="SingleplayerController?id=1" method="post" data-transition="slide">
                    <button>richtig</button>     
                </form> 
                <form action="SingleplayerController?id=0" method="post" data-transition="slide">
                    <button>falsch</button>     
                </form> 
       </div>        
               
               
                
                <div data-role="footer" data-theme="d" id="footer"><h1></h1></div>
    </body>
</html>
