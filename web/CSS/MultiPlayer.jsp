<%-- 
    Document   : MultiPlayer
    Created on : 26.09.2016, 10:49:02
    Author     : Jonas
--%>
<%//@page import="de.projekt.model.DatenbankZugang"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
         <link href="haupt.css" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
        
        
        
        
        <h1 id="haupt" style="text-align: center;">Multiplayer</h1>
        <p style="text-align: center; font-family: calibri; font-size: 30px;"> Wähle 3 Kategorien aus</p>
        
        <table style="table-layout:fixed; margin-left:auto; margin-right:auto; border: 5px solid grey; ">
            <tr style="height:120px;">
                <td id="0" onclick="myFunction(this.id)"  style="text-align: center; border: 5px outset #dbdbdb; width:120px;"><b>Deutsch</b></td>
                <td id="1" onclick="myFunction(this.id)" style="text-align: center; border: 5px outset #dbdbdb;width:120px;"><b>Sport</b></td>
                <td id="2" onclick="myFunction(this.id)" style="text-align: center; border: 5px outset #dbdbdb;width:120px;"><b>Mathe</b></td>
            </tr>
            <tr style="height:120px;">
                <td id="3"onclick="myFunction(this.id)" style="text-align: center; border: 5px outset #dbdbdb;width:120px;"><b>Geschichte</b></td>
                <td id="4"onclick="myFunction(this.id)" style="text-align: center; border: 5px outset #dbdbdb;width:120px;"><b>Englisch</b></td>
                <td id="5"onclick="myFunction(this.id)" style="text-align: center; border: 5px outset #dbdbdb;width:120px;"><b>Politik</b></td>
            </tr> 
        </table>
            
           <button id="querybutton">Kategorien auswählen</button>
            
            
            
            
            
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
                   var auslesen =  document.getElementsByTagName('td')[clicked_id].style.getPropertyValue("border-style"); // überprüft welcher Rand gesetzt ist inset oder outset
                          
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
                
            </script>   
                <div data-role="footer" data-theme="c" id="footer" style="margin-top: 20px;"><h1></h1></div>
      
    </body>
</html>
