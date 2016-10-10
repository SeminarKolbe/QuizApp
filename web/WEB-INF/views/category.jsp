<%@page import="de.projekt.model.Kategorie"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
  
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
        <link rel="stylesheet" href="css/design.css" />
        <script src="js/jquery-2.2.4.min.js"></script>
        <script src="js/jquery.mobile-1.4.5.min.js"></script>
        <title>Kategorieauswahl</title>
    </head>
    <body>
        <div data-role="page">   
          
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

                <!--Falls Kategorie ungültig oder falsch gewählt folgt eine Fehlermeldung -->
                <h2><font color="red">${errorMessage}</font></h2>
                <!-------------------------------->
                <h3>Wähle eine Kategorie:</h3>

                <div id="kategorieauswahl" style="text-align: center;">
                    <ul data-role="listview" data-ajax="false" data-inset="true" data-theme="c" id="kategorien">                        <!-- KATEGORIEN WERDEN GELADEN -->    
                    <%
                        Kategorie kategorien =  new Kategorie(); 
                        ArrayList<String> kategoriethemen= kategorien.getNameKategorien(); // Guck auf der Datenbank, welche Kategorien hinterlegt sind
                        for(int i = 0; i < kategoriethemen.size(); i++) {
                            out.print("<li><a href=\"CategoryController?category="+kategoriethemen.get(i)+"\" data-transition=\"slide\">"+kategoriethemen.get(i)+"</a></li>");
                        }
                    %>
                    </ul> 
                </div>

                <h3>Erstelle eine neue Karteikarte</h3>
                <form action="CategoryController?category=NewNotecard" data-transition="slide">
                    <button>Zur Karteikartenerstellung</button>
                </form>
                
            </div>    
                <div data-role="footer" data-theme="d" id="footer"><h1></h1></div>
        </div>
    </body>
</html>