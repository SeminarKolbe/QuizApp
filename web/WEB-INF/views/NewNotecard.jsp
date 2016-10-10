<%-- 
    Document   : NewNotecard
    Created on : 02.10.2016, 19:33:14
    Author     : Marin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset=UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
        <link rel="stylesheet" href="css/design.css" />
        <script src="js/jquery-2.2.4.min.js"></script>
        <script src="js/jquery.mobile-1.4.5.min.js"></script>
        <title>Karteikarte erstellen</title>
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
        
        <div data-role="content" style="margin:10px">
            <h1>Neue Karteikarte</h1>
            <h2>Folgende Angaben werden benötigt</h2>

            <form action="NewNotecardController?create=true" method="post" data-transition="slide">

                <p>Thema:</p>
                <input type="text" name="thema" id="thema" required />

                <p>Frage:</p>
                    <input type="text" name="question" id="question" required/>

                <p>richtige Antwort:</p>
                    <input type="text" name="correctAnswer" id="correctAnswer" required />

                <input type="submit" value="Karteikarte erstellen" />

            </form>
        </div>
        <div data-role="footer" data-theme="d" id="footer"><h1></h1></div>
    </body>
</html>
