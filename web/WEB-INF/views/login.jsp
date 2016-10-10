<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
        <link rel="stylesheet" href="css/design.css" />
        <script src="js/jquery-2.2.4.min.js"></script>
        <script src="js/jquery.mobile-1.4.5.min.js"></script> 
        <title>Odyssee - Login</title>
    </head>
    <div data-role="page">
        <div data-role="header" data-theme="d" id="header" class="ui-header">
            <h1 class="ui-title">Quizmania</h1>
        </div>
        <p><font color="red">${errorMessage}</font></p>
        <!--*****Start Login Interface*******-->
        <div id="LoginInterface" style="margin:10px">
            <form action="LoginControllerServlet" method="post" data-transition="slide">
                <label for="userName" class="ui-hid-accessible">Username:</label>
                <input type="text" data-clear-btn="true" name="userName" id="username" value="" placeholder="Username" required/>
                <label for="password" class="ui-hid-accessible">Password:</label>
                <input type="password" data-clear-btn="true" name="password" id="password" value="" placeholder="Password" required/>
                <button class="ui-btn ui-corner-all" >Login</button>
            </form>           
        
        
        </div>
        <!--*****Ende Login Interface -->
        <div data-role="footer" data-theme="d" id="footer"><h1></h1></div>
    </div>
</body>
</html>
