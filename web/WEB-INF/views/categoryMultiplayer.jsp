<%-- 
    Document   : categoryMultiplayer
    Created on : 03.10.2016, 15:26:06
    Author     : Marin
--%>

<%@page import="de.projekt.model.Kategorie"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
  
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
        <link rel="stylesheet" href="css/design.css" />
        <script src="js/jquery-1.12.4.min.js"></script>
        <script src="js/jquery.mobile-1.4.5.min.js"></script>
        <title>Kategorieauswahl</title>
    </head>
    <body>
        <div data-role="page">   
          
  <!-- HEADER-->
            <div data-role="header" data-theme="b" id="header">            
                <form action="LoginControllerServlet" method="post" class="ui-btn-left" data-transition="slide" data-direction="reverse">
                    <button data-theme="a">Back</button>
                </form>    
                <h1>  L'Odyssee </h1>           
                <form action="LogoutController" method="post" class="ui-btn-right" data-transition="slide" data-direction="reverse">
                    <button data-theme="a">Logout</button>
                </form>     
            </div>
<!-- /HEADER-->

            <div data-role="content" style="margin:10px">
            
                <h1>Wähle drei Themen ${userName}!</h1>

                <!--Falls Kategorie ungültig oder falsch gewählt folgt eine Fehlermeldung -->
                <h2><font color="red">${errorMessage}</font></h2>
                <!-------------------------------->
                <h3><font color="LimeGreen">Du spielst gegen ${requestScope.otherPlayerName}</font></h3>
<!-- Ohne Link funktioniert die listview nicht 

    Lösung: toggle switches als übergangslösung
-->
                <script>
//                    var categories = {};
//                    var clickCounter = 1;
//                    $("#category1").on("click",function(){
//                        var chosenthema = "chosenthema" + clickCounter;
//                        categories[chosenthema] = $(this).data();
//                        console.log($(this).val());
//                        console.log(categories);
//                        clickCounter++;
//                        console.log(categories["chosenthema"+clickCounter]);
//                    });

//                    if("category:input")
//                        $("p.category")
//                    $("#kategorien li").click(function(){
//                        categories["category1"] = $(this).attr("id");
//                        submitCategories.enable = true;
//                    });
//                    $("input.category").click(function(){
//                        switch(clickCounter){
//                            case 1:
//                                categories["category1"] = $(this).attr('data-value');
//                                clickCounter++;
//                                break;
//                            case 2:
//                                categories["category2"] = $(this).attr('data-value');
//                                clickCounter++;
//                                break;
//                            case 3:
//                                categories["category3"] = $(this).attr('data-value');
//                                clickCounter++;
//                                break;
//                        }
//                    });
//                    if(clickCounter === 1){
//                        submitCategories.enable = true;
//                    }
//                    $("#submitCategories").click(function(){
//                        $.post("/MultiplayerController?themafrage=thema1frage0",{
//                            chosenthema1: categories.category1,
//                            chosenthema2: categories.category2,
//                            chosenthema3: categories.category3
//                        });
//                    });
                </script>
                <form action="MultiplayerController?themafrage=thema1frage0" method="post">
                <div id="kategorieauswahl" style="text-align: center;">
                    <ul data-role="listview" data-ajax="false" data-inset="true" data-theme="c" id="categories">
                        <!-- KATEGORIEN WERDEN GELADEN -->    
                    <% 
                        Kategorie kategorien =  new Kategorie(); 
                        ArrayList<String> kategoriethemen= kategorien.getKategorien(); // Guck auf der Datenbank, welche Kategorien hinterlegt sind
                        for(int i = 0; i < kategoriethemen.size(); i++) {
                            out.print("<li><input type=\"checkbox\" data-role=\"flipswitch\" value=\""+kategoriethemen.get(i)+"\" name=\"category\">"+kategoriethemen.get(i)+"</input></li>");
                        }
                    %>
                    </ul> 
                </div>
                
                <input type="submit" value="Themen abschicken" enabled/>
                    </form>
                
            </div>
                <div data-role="footer" data-theme="c" id="footer"><h1></h1></div>
        </div>
    </body>
</html>