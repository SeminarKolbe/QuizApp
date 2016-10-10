<%-- 
    Document   : WeiterleitungRunde2
    Created on : 05.10.2016, 23:25:42
    Author     : Marin
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
        <title>Auf zur Runde 2</title>
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
        
        <div style="margin:10px">
            <h2>Auf zur Runde 2!</h2>

            <h3>Folgende Themen hast du für diese Runde ausgewählt!</h3>
            <table data-role="table" class="ui-responsive" align="center">
                <thead><tr><th>Themanummer</th><th>Thema</th></tr><thead>
                <tbody>
                    <tr><td>1</td><td>${requestScope.thema1}</td></tr>
                    <tr><td>2</td><td>${requestScope.thema2}</td></tr>
                    <tr><td>3</td><td>${requestScope.thema3}</td></tr>
                </tbody>
            </table>


            <form action="MultigameController?themafrage=thema1frage1" method="post" name="nextRound" data-transition="slide">
                <button>Zur Runde 2</button>
            </form>
                
            <div data-role="footer" data-theme="d" id="footer"><h1></h1></div>
        </div>
        
    </body>
</html>
