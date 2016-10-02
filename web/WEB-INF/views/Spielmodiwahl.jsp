<%@page import="de.projekt.model.Player"%>
<%@page import="de.projekt.model.Multi"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.0.1.min.css" />
        <script src="js/jquery-1.11.1.min.js"></script>
        <script src="js/jquery.mobile-1.4.5.min.js"></script>
        <title>Spielmodus</title>
        <link rel="stylesheet" href="css/design.css" type="text/css"/>
    </head>
    <body>
        <div data-role="page">
             <div data-role="header" data-theme="b" id="header">            
                <form action="LoginControllerServlet" method="post" class="ui-btn-left">
                    <button data-theme="a">Back</button>
                </form>    
                <h1>  L'Odyssee </h1>           
                <form action="LogoutController" method="post" class="ui-btn-right">
                    <button data-theme="a">Logout</button>
                </form>     
            </div>
     <div data-role="content" align="center">
            <h2>Wähle deinen Spielmodus:</h2>
            <% /* List<String> anfrage =new ArrayList<String>();
                int [] anfrageid= new int[100];
               
                request.setAttribute("player",request.getParameter("player"));
                Player ueber=((Player) session.getAttribute("player"));
                Multi loeschen=new Multi(ueber);
                
                
                try{
                     anfrage = (List<String>)request.getAttribute("anfrage");
                     anfrageid =(int[])request.getAttribute("anfrageid");
                }catch(Exception e){
                    System.out.println(e);
                }    
            */
            %>
     </div>   
 <!-- Ausgabe der verschiedenen Spielmodien -->    
     <div align="center"> 
            <a href="SpielModusServlet?category=3"> 
                <img src="Bilder/Einzelspieler.jpg" alt="Einzelspieler" width="50%" height="50%" name="Einzelspieler"/>
            </a>
            
            <a href="SpielModusServlet?category=2"> 
                <img src="Bilder/Mehrspieler.png" alt="Mehrspieler" width="50%" height="50%" name="Mehrspieler"/>
                </a>
            
            <a href="RankingController"> 
                <img src="Bilder/Ranking.png" alt="Ranking" width="50%" height="50%" name="Ranking"/>
                </a>
            
            <a href="StatistikController"> 
                <img src="Bilder/Statistik.jpg" alt="Statistik" width="50%" height="50%" name="Statistik"/>
                </a>
     </div>
 
<!-- Guckt ob die Ergebnisse eines Multiplayergames sichtbar sind -->
        <div data-role="content" style="margin:10px">   
            <% // Guckt ob eine eine Anfrage vom gegner fertig ist und gibt das ergebniss aus, gleichzeitig wird die ANfrage gelöscht
                /*out.println("<form action=\"ErgebnisController\" method=\"post\"><label>Spieler(in):</label><select name=\"endauswahl\"size=\"1\">");
                    for(int g=0;g<anfrageid.length;g++){
                    if(loeschen.getErgebnisAll(anfrageid[g],ueber.getName())){
                        out.println("<option value=\""+anfrageid[g]+"\">Ergebnis von Spiel: "+anfrageid[g]+" ansehen</option>");    
                    }    
                }
                out.println(" </select><button>Ergebnis checken</button></form>");
            */
            %>
            
            
            
            <%  // Guck ob ein Gegner eine Anfrage gesendet hat
                /*if(anfrage.size()>0){
               
               out.println("<form action=\"VerarbeitungsControllerMulti\" method=\"post\"><label>Spieler(in):</label><select name=\"top5\"size=\"1\">"); 
               for(int i=0;i<anfrage.size();i++){
                   out.println("<option value=\""+anfrageid[i]+"\">"+anfrage.get(i)+"</option>");
               } 
               out.println(" </select><button>Los geht's</button></form>");  
            }*/%>
     </div>
            <div data-role="footer" data-theme="c" id="footer"><h1></h1></div>
        </div>
    </body>
</html>
