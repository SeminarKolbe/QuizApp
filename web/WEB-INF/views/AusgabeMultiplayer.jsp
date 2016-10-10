<%-- 
    Document   : AusgabeMultiplayer
    Created on : 29.09.2016, 13:15:53
    Author     : Marin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="de.projekt.model.Multigame"%>
<%@page import="de.projekt.model.Player"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
        <link rel="stylesheet" href="css/design.css" />
        
        <script src="js/jquery-2.2.4.min.js"></script>
        <script src="js/jquery.mobile-1.4.5.min.js"></script>
        <title>Multiplayer</title>
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
        
        //falls Antworten bereits abgegeben next hochzählen für nächste Frage, wenn nicht Antworten übernehmen
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
            //wurde diese Antworte angekreuzt? ja dann wieder ankreuzen ; nein dann leer lassen
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
            private String markAnswers(ArrayList<Boolean> correct, int pos1){
                if(correct.get(pos1)){
                    return "✔";
                } else {
                    return "\u2718";
                }       
            }
        %>
    <body>
        <div data-role="header" data-theme="d" id="header" class="ui-header">
            <div class="ui-alt-icon">
            <a href="LoginControllerServlet" data-transition="slide" class="ui-btn-left ui-btn-corner-all ui-btn ui-icon-home ui-btn-icon-notext ui-shadow" data-direction="reverse" data-form="ui-icon" data-role="button"></a>
            <h1 class="ui-title">  Quizmania </h1>
            <a href="LogoutController" data-transition="slide" class="ui-btn-right ui-btn-corner-all ui-btn ui-icon-action ui-btn-icon-notext ui-shadow" data-direction="reverse" data-form="ui-icon" data-role="button"></a>  
            </div>
        </div>
        <div data-role="content" style="margin:10px">
            <h2><%=session.getAttribute("currentThema").toString()%></h2>
            <p>Du spielst gegen: <%=multigame.getotherPlayerName(player.getUser_id())%><p>
            <p> <b>Frage</b>: 
            <%=question%>
            </p>
            <form action="MultigameController?themafrage=<%=nextFrage%>" method="post" id="Answers" data-transition="slide">
                
                <input type="Checkbox" name="answer" value="a1"<%if(abgegeben!="not yet" && answerCheck(useranswers, "a1"))out.println(" checked=\"checked\"");%>><%=answers.get(0)%><%if(abgegeben!="not yet")out.println(markAnswers(correctedAnswers, 0));%>
                <input type="Checkbox" name="answer" value="a2"<%if(abgegeben!="not yet" && answerCheck(useranswers, "a2"))out.println(" checked=\"checked\"");%>><%=answers.get(1)%><%if(abgegeben!="not yet")out.println(markAnswers(correctedAnswers, 1));%>
                <input type="Checkbox" name="answer" value="a3"<%if(abgegeben!="not yet" && answerCheck(useranswers, "a3"))out.println(" checked=\"checked\"");%>><%=answers.get(2)%><%if(abgegeben!="not yet")out.println(markAnswers(correctedAnswers, 2));%>
                <input type="Checkbox" name="answer" value="a4"<%if(abgegeben!="not yet" && answerCheck(useranswers, "a4"))out.println(" checked=\"checked\"");%>><%=answers.get(3)%><%if(abgegeben!="not yet")out.println(markAnswers(correctedAnswers, 3));%>
                <input type="Checkbox" name="answer" value="a5"<%if(abgegeben!="not yet" && answerCheck(useranswers, "a5"))out.println(" checked=\"checked\"");%>><%=answers.get(4)%><%if(abgegeben!="not yet")out.println(markAnswers(correctedAnswers, 4));%>
             
                <%
                    //noch nicht abgegeben zeige den Button "Antwort prüfen", wenn doch zeige Button "nächste Frage"
                    if(abgegeben == "not yet") {
                        session.setAttribute("done", "done");
                %>
                <button>Antwort prüfen</button>
                <%}else{ session.setAttribute("done", "not yet");%>
                <button>nächste Frage</button>
                <%}%>
            </form>
        </div> 



       
        <div data-role="footer" data-theme="d" id="footer"><h1></h1></div>
    
    </body>
 
</html>


 