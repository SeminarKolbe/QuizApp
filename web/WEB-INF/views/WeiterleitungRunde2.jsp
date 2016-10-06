<%-- 
    Document   : WeiterleitungRunde2
    Created on : 05.10.2016, 23:25:42
    Author     : Marin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
        <link rel="stylesheet" href="css/design.css" />
        <script src="js/jquery.mobile-1.4.5.min.js"></script>
        <script src="js/jquery-1.12.4.min.js"></script>
        <title>Auf zur Runde 2</title>
    </head>
    <body>
        <h1>Auf zur Runde 2!</h1>
        
        <h3>Folgende Themen hast du für diese Runde ausgewählt!</h3>
        <label>Thema 1:</label><p>${requestScope.thema1}</p>
        <label>Thema 2:</label><p>${requestScope.thema2}</p>
        <label>Thema 3:</label><p>${requestScope.thema3}</p>
        
        <label>In 15 Sekunden geht´s los! Bereite dich vor!</label>
        
        <form action="MultiplayerController?themafrage=thema1frage1" method="post" name="nextRound">
            <button>Zur Runde 2</button>
        </form>
        
        <script>               
            function getTimeRemaining(endtime) {
                var t = Date.parse(endtime) - Date.parse(new Date());
                var seconds = Math.floor((t / 1000) % 60);
                return {
                    'total': t,
                    'seconds': seconds
                };
            }

            function initializeClock(id, endtime) {
                var clock = document.getElementById(id);
                var secondsSpan = $(".seconds");

                function updateClock() {
                    var t = getTimeRemaining(endtime);

                    secondsSpan.innerHTML = ('0' + t.seconds).slice(-2);
                    //Zeit ist abgelaufen
                    if (t.total <= 0) {
                        clearInterval(timeinterval);
                        document.nextRound.submit();
                    }
               }

                updateClock();
                var timeinterval = setInterval(updateClock, 1000);
            }

            var deadline = new Date(Date.parse(new Date()) + 15 * 1000);
            initializeClock('#clockdiv', deadline);
        </script>
        <h3>Verbleibende Zeit bis zur automatischen Weiterleitung</h3>
        <div id="clockdiv">
          <div>
            <span class="seconds"></span>
            <div class="smalltext">Sekunden</div>
          </div>
        </div>
    </body>
</html>
