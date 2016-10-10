<%@page import="de.projekt.model.Player"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
        <link rel="stylesheet" href="css/design.css" type="text/css"/>
        <script src="js/jquery-2.2.4.min.js"></script>
        <script src="js/jquery.mobile-1.4.5.min.js"></script>
        <title>Spielmodus</title>
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
    
     <div data-role="content" align="center">
            <h2>Wähle deinen Spielmodus:</h2>

     </div>   
 <!-- Ausgabe der verschiedenen Spielmodien -->    
     <div class="ui-grid-a">
         <div class="ui-block-a">
            <a href="SpielModusServlet?category=3" data-transition="slide"> 
                <img src="images/Einzelspieler.jpg" alt="Einzelspieler" name="Einzelspieler" height="100%" width="100%"/>
            </a>
         </div>
         <div class="ui-block-b">
            <a href="SpielModusServlet?category=2" data-transition="slide"> 
                <img src="images/Mehrspieler.png" alt="Mehrspieler" name="Mehrspieler" class="img " height="120%" width="100%"/>
                </a>
         </div>
     </div>
 
         <div class="ui-grid-a">
             <div class="ui-block-a">  
            <a href="RankingController" data-transition="slide"> 
                <img src="images/Ranking.png" alt="Ranking" name="Ranking" height="100%" width="100%"/>
                </a>
             </div>
             <div class="ui-block-b">
            <a href="StatistikController" data-transition="slide"> 
                <img src="images/Statistik.jpg" alt="Statistik" name="Statistik" height="100%" width="100%"/>
                </a>
             </div>
         </div>
 

            <div data-role="footer" data-theme="d" id="footer"><h1></h1></div>
        </div>
    </body>
</html>
