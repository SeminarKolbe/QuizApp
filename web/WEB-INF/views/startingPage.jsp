<%-- 
    Document   : startingPage
    Created on : 07.07.2016, 13:39:06
    Author     : René
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="http://code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.css" />
        <script src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
        <script src="http://code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.js"></script>
        <title>Startseite</title>
    </head>
    <body>
        <h1>Herzlich Willkommen!</h1>
            <img src="http://mob164.projektserver3.as.wiwi.uni-goettingen.de:8080/HalloDuDa.jpg" alt="HalloDuDa" width="500" height="500" name="HalloDuDa"/> 
            <form href="/WEB-INF/views/login.jsp" method="post">              
                <button class="ui-btn ui-corner-all" >Zum Login</button>
            </form>   
    </body>
</html>
