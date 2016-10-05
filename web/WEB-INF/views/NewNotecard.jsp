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
        <script src="js/jquery-1.7.1.min.js"></script>
        <script src="js/jquery.mobile-1.4.5.min.js"></script>
        <script src="js/jquery-1.12.4.min.js"></script>
        <title>Karteikarte erstellen</title>
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
        <h1>Neue Karteikarte</h1>
        <h2>Folgende Angaben werden benötigt</h2>

        <form action="NewNotecardController?create=true" method="post" >
            
            <p>Thema:</p>
            <input type="text" name="thema" id="thema" required />
            
            <p>Frage:</p>
                <input type="text" name="question" id="question" required/>
                
            <p>richtige Antwort:</p>
                <input type="text" name="correctAnswer" id="correctAnswer" required />
                
            <input type="submit" value="Karteikarte erstellen" />
            
        </form>
        
        <div data-role="footer" data-theme="c" id="footer"><h1></h1></div>
    </body>
</html>
