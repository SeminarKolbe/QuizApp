<%-- 
    Document   : AusgabeMultiplayer
    Created on : 29.09.2016, 13:15:53
    Author     : Marin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="de.projekt.model.Multigame"%>
<%@page import="de.projekt.model.Player"%>
<%@page import="de.projekt.model.AntwortenUebergabe"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset=UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.0.1.min.css" />
        <link rel="stylesheet" href="css/design.css" />
        <script src="js/jquery-1.7.1.min.js"></script>
        <script src="js/jquery.mobile-1.0.1.min.js"></script>
        <title>Multiplayer</title>
        <script src="js/jquery-1.10.2.js"></script>
    </head>
    <%
        ArrayList<String> useranswers = new ArrayList<String>();
        Multigame multigame = (Multigame) session.getAttribute("multigame");
        Player player = (Player) session.getAttribute("player");
        int round = multigame.getRound();
        String question = request.getAttribute("question").toString();
        int next = (int) request.getAttribute("next");
        ArrayList<String> answers = (ArrayList<String>) request.getAttribute("answers");
        ArrayList<Boolean> correctedAnswers = (ArrayList<Boolean>) request.getAttribute("correctedAnswers");
        String abgegeben = session.getAttribute("done").toString();
        
        if(abgegeben == "done") {
            useranswers = (ArrayList<String>) request.getAttribute("useranswers");
        } else {
            next -= 1;
        }
        
        String nextFrage = "";
        switch(next){
            case 1:
                nextFrage = "thema1frage1";
                break;
            case 2:
                nextFrage = "thema1frage2";
                break;
            case 3:
                nextFrage = "thema1frage3";
                break;
            case 4:
                nextFrage = "thema2frage1";
                break;
            case 5:
                nextFrage = "thema2frage2";
                break;
            case 6:
                nextFrage = "thema2frage3";
                break;
            case 7:
                nextFrage = "thema3frage1";
                break;
            case 8:
                nextFrage = "thema3frage2";
                break;
            case 9:
                nextFrage = "thema3frage3";
                break;
            case 10:
                nextFrage = "endround";
                break;
        }
        %>
        
        <%!
            private boolean answerCheck(ArrayList<String> a, String b){
                if(!a.isEmpty()){
                    for(int i=0; i < a.size() ; i++){
                        if(a.get(i).equals(b)){
                        return true;
                        }
                    }
                }
                return false;
            }     

        //Gibt einen Hacken zurückfalls die richtige Antwort angekreuz wurde, oder keine falsche antwort. Sonst wird ein Kreuz ausgegeben
            private String correct(ArrayList<Boolean> correct, int pos1){
                if(correct.get(pos1)){
                    return "✔";
                } else {
                    return "\u2718";
                }       
            }
        %>
    <body>
        <div data-role="header" data-theme="b" id="header">            
                <form action="LoginControllerServlet" method="post" class="ui-btn-left">
                    <button data-theme="a">Back</button>
                </form>    
                <h1>  L'Odyssee </h1>           
                <form action="LogoutController" method="post" class="ui-btn-right">
                    <button data-theme="a">Logout</button>
                </form>     
        </div>
        <div data-role="content" style="margin:10px">
            <h2><%out.println(session.getAttribute("currentThema").toString());%></h2>
            <p><%out.println("Du spielst gegen: " + multigame.getotherPlayerName(player.getUser_id()));%><p>
            <p> <b>Frage</b>: 
            <%out.println(question);%>
            </p>
            <form action="MultiplayerController?themafrage=<%out.println(nextFrage);%>" method="post" name="Answers">
                
                <input type="Checkbox" name="answer" value="a1"<%if(abgegeben!="not yet" && answerCheck(useranswers, "a1"))out.println(" checked=\"checked\"");%>><%out.println(answers.get(0));if(abgegeben!="not yet")out.println(correct(correctedAnswers, 0));%> <br>
                <input type="Checkbox" name="answer" value="a2"<%if(abgegeben!="not yet" && answerCheck(useranswers, "a2"))out.println(" checked=\"checked\"");%>><%out.println(answers.get(1));if(abgegeben!="not yet")out.println(correct(correctedAnswers, 1));%> <br>
                <input type="Checkbox" name="answer" value="a3"<%if(abgegeben!="not yet" && answerCheck(useranswers, "a3"))out.println(" checked=\"checked\"");%>><%out.println(answers.get(2));if(abgegeben!="not yet")out.println(correct(correctedAnswers, 2));%> <br>
                <input type="Checkbox" name="answer" value="a4"<%if(abgegeben!="not yet" && answerCheck(useranswers, "a4"))out.println(" checked=\"checked\"");%>><%out.println(answers.get(3));if(abgegeben!="not yet")out.println(correct(correctedAnswers, 3));%> <br>
                <input type="Checkbox" name="answer" value="a5"<%if(abgegeben!="not yet" && answerCheck(useranswers, "a5"))out.println(" checked=\"checked\"");%>><%out.println(answers.get(4));if(abgegeben!="not yet")out.println(correct(correctedAnswers, 4));%> <br>
             
                <%if(abgegeben == "not yet") {
                    session.setAttribute("done", "done");
                %>
                <button>Antwort prüfen</button>
                <%}else{ session.setAttribute("done", "not yet");%>
                <button>nächste Frage</button>
                <%}%>
            </form>
        </div> 
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
                    var secondsSpan = clock.querySelector('.seconds');

                    function updateClock() {
                        var t = getTimeRemaining(endtime);
                        
                        secondsSpan.innerHTML = ('0' + t.seconds).slice(-2);
                        //countdown ist abgelaufen
                        if (t.total <= 0) {
                            clearInterval(timeinterval);
                            document.Answers.submit();
                        }
                    }

                    updateClock();
                    var timeinterval = setInterval(updateClock, 1000);
                }

                var deadline = new Date(Date.parse(new Date()) + 15 * 1000);
                initializeClock('clockdiv', deadline);
            </script>
        <h3>Verbleibende Zeit</h3>
        <div id="clockdiv">
          <div>
            <span class="seconds"></span>
            <div class="smalltext">Sekunden</div>
          </div>
        </div>
        <div data-role="footer" data-theme="c" id="footer"><h1></h1></div>
    </body>
</html>


