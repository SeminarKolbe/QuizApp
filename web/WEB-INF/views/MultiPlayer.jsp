<%-- 
    Document   : MultiPlayer
    Created on : 26.09.2016, 10:49:02
    Author     : Jonas
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="de.projekt.model.Kategorie"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
        <link rel="stylesheet" href="css/design.css" />
        <script src="js/jquery-2.2.4.min.js"></script>
        <script src="js/jquery.mobile-1.4.5.min.js"></script>
        <title>JSP Page</title>
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
            Kategorie kategorien = new Kategorie();
            ArrayList<String> themen = kategorien.getNameKategorien();
        %>
        <table data-role="table" class="ui-responsive">
            <thead><tr><td></td></tr></thead>
            <tbody>
            <tr style="height:120px;">
                <td id="1" onclick="myFunction(this.id)" style="text-align: center; border: 5px outset #dbdbdb; width:120px;"><%=themen.get(0)%></td>
                <td id="2" onclick="myFunction(this.id)" style="text-align: center; border: 5px outset #dbdbdb; width:120px;"><%=themen.get(1)%></td>
                <td id="3" onclick="myFunction(this.id)" style="text-align: center; border: 5px outset #dbdbdb; width:120px;"><%=themen.get(2)%></td>
            </tr>
            <tr style="height:120px;">
                <td id="4" onclick="myFunction(this.id)" style="text-align: center; border: 5px outset #dbdbdb; width:120px;"><%=themen.get(3)%></td>
                <td id="5" onclick="myFunction(this.id)" style="text-align: center; border: 5px outset #dbdbdb; width:120px;"><%=themen.get(4)%></td>
                <td id="6" onclick="myFunction(this.id)" style="text-align: center; border: 5px outset #dbdbdb; width:120px;"><%=themen.get(5)%></td>
            </tr> 
            </tbody>
        </table>
            
        <button id="querybutton" onclick="sendCategories()" data-transition="slide">Kategorien auswählen</button>
            
            
            
        <script type="text/javascript">
            //ändert die Farbe der Kästchen
            function updateColorCaseClicked(clicked_id){
                 document.getElementsByTagName('td')[clicked_id].style.backgroundColor='#f98da4';   
            }

            function updateColorCaseUndClicked(clicked_id){
                 document.getElementsByTagName('td')[clicked_id].style.backgroundColor='#e8e8e8';   
            }

            //ändert den Rahmen der Kästchen und erzeugt somit ein Klick-Efferkt
            function myFunction(clicked_id){ 
                console.log("clicked_id: " + clicked_id);
                var auslesen = document.getElementsByTagName('td')[clicked_id].style.getPropertyValue("border-style"); // überprüft welcher Rand gesetzt ist inset oder outset
                    if(auslesen == "outset"){
                         if(clickedButton()<3){
                            updateColorCaseClicked(clicked_id); 
                            document.getElementsByTagName('td')[clicked_id].style.borderStyle='inset';
                            setButton();
                         }
                    }else{
                        updateColorCaseUndClicked(clicked_id);
                        document.getElementsByTagName('td')[clicked_id].style.borderStyle='outset';
                        setButton();
                     }
            }
            
            //Setzt den Button zum Abschicken der ausgewählten themen in Farbe
            function setButton(){
                if(clickedButton()==3){
                    document.getElementById('querybutton').style.backgroundColor='yellow';
                }
                else{
                    document.getElementById('querybutton').style.backgroundColor='black';
                }
            }
            
            function sendCategories(){
                var clickedTableCells = getClickedPosition();
                window.location = 'MultigameController?themafrage=thema1frage0&category1='+clickedTableCells[0]+'&category2='+clickedTableCells[1]+'&category3='+clickedTableCells[2];
            }


            // Gibt die Anzahl an geklickten Button zurück
           function clickedButton(){
                clickedanzahl=6;
                for(var i=0;i<document.getElementsByTagName('td').length;i++){
                    if(document.getElementsByTagName('td')[i].style.borderStyle=="outset"){     // Anzahl der nicht gedrückten Felder
                        clickedanzahl--;
                    }
                } 
                return clickedanzahl;
            }

            function getClickedPosition(){
                var clickedpositions =[];
                for(var i=0;i<document.getElementsByTagName('td').length;i++){
                    help =  document.getElementsByTagName('td')[i].style.getPropertyValue("border-style");
                    if(help == "inset"){
                        clickedpositions.push(document.getElementsByTagName('td')[i].innerHTML); 
                    }
                }
                return clickedpositions;
            }


        </script>   
        <div data-role="footer" data-theme="d" id="footer" style="margin-top: 20px;"><h1></h1></div>
      
    </body>
</html>
