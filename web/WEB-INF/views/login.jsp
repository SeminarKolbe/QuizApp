<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
        <link rel="stylesheet" href="css/design.css" />
        <script src="js/jquery-1.7.1.min.js"></script>
        <script src="js/jquery.mobile-1.4.5.min.js"></script> 
        <title>Odyssee - Login</title>
    </head>
    <div data-role="page">
        <div data-role="header" data-theme="b" id="header">
            <h1>L'Odyssee</h1>
        </div>
        <p><font color="red">${errorMessage}</font></p>
        <!--*****Start Login Interface*******-->
        <div id="LoginInterface" style="margin:10px">
            <form action="LoginControllerServlet" method="post" data-transition="slide">
                <label for="userName" class="ui-hid-accessible">Username:</label>
                <input type="text" data-clear-btn="true" name="userName" id="username" value="" placeholder="Username"/>
                <label for="password" class="ui-hid-accessible">Password:</label>
                <input type="password" data-clear-btn="true" name="password" id="password" value="" placeholder="Password"/>
                <button class="ui-btn ui-corner-all" >Login</button>
            </form>           
        
        
        <img src="images/logo.JPG">
        </div>
        <!--*****Ende Login Interface -->
        <div data-role="footer" data-theme="c" id="footer"><h1></h1></div>
    </div>
</body>
</html>
