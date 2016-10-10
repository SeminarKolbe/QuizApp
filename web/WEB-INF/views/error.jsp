<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
        <link rel="stylesheet" href="css/design.css" />
        <script src="js/jquery-1.7.1.min.js"></script>
        <script src="js/jquery.mobile-1.4.5.min.js"></script>
        <title>Fehler!</title>
    </head>
    <body>
        <h1>Fehler</h1>
        <p>Beim Verarbeiten Ihrer Anfrage trat der folgende Fehler auf:</p>
        <p style="color:red;">
            <c:out value = "${requestScope.error}"/>
        </p>
    </body>
</html>
