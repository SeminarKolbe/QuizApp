<%-- 
    Document   : categoryAdmin
    Created on : 19.06.2016, 17:52:39
    Author     : Jonas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
        <link rel="stylesheet" href="css/design.css" />
        <script src="js/jquery-1.7.1.min.js"></script>
        <script src="js/jquery.mobile-1.4.5.min.js"></script>
        <title>Admin-Berreich</title>
    </head>
    <body>
        
        
        <div data-role="header" data-theme="b" id="header">            
                <h1>L'Odyssee            
                   <form action="LogoutController" method=""post class="ui-btn-right">
                        <button data-theme="a">Logout</button>
                   </form>
                 </h1>
        </div>
<div data-role="content" style="margin:10px">
       <p> Bitte laden Sie Fragen im XML-Format hoch</p>
       
    
       
       <form method="POST" action="ControllerAdmin" enctype="multipart/form-data" rel="external" data-ajax="false">
            
          File:<input type="file" name="file" id="file" /> <br/>
            <input type="submit" value="Upload" name="upload" id="upload" />
        </form>
       
</div> 
       <div data-role="footer" data-theme="c" id="footer"><h1></h1></div>
       
    </body>
</html>
