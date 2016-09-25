<%-- 
    Document   : AusgabeMultiplayer
    Created on : 04.07.2016, 18:53:29
    Author     : Jonas
--%>


<%@page import="de.projekt.model.AntwortenUebergabe"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="http://code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.css" />
        <script src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
        <script src="http://code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.js"></script>
        <title>Multiplayer</title>
        </style>
        <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
    </head>
    <body>
         <%
         int right= 0;
         int wrong =0;
         boolean lantwort= true;
         int i=0;
         int [] santworten ={0};
         String res="0";
         int gesamteantworten=5;
         
         String namegegner = request.getAttribute("namegegner").toString();
         String frage = request.getAttribute("frage").toString();
         String antwort1 = request.getAttribute("antwort1").toString();
         String antwort2 = request.getAttribute("antwort2").toString();
         String antwort3 = request.getAttribute("antwort3").toString();
         String antwort4 = request.getAttribute("antwort4").toString();
         String antwort5 = request.getAttribute("antwort5").toString();
         String checkanswer = request.getAttribute("checkanswer").toString();
         
        
         if(checkanswer.equals("0"))
            res="1";
         
         
         String correct1 = request.getAttribute("correct1").toString();
         String correct2 = request.getAttribute("correct2").toString();
        
         String correct3 = request.getAttribute("correct3").toString();
         String correct4 = request.getAttribute("correct4").toString();
         String correct5 = request.getAttribute("correct5").toString();
        //______________Zum Häckchen setzen in den alten kästchen____________
        String abgegeben = request.getAttribute("abgegeben").toString();
          
          
          
          if(antwort5.equals("")){
              gesamteantworten--;    
          }
          if(abgegeben!=null){
                    
                AntwortenUebergabe help =(AntwortenUebergabe) request.getAttribute("santworten");
                if(antwort5.equals("")){
                    lantwort = false;
                }
                santworten = help.getSantworten();
                 
                 
          }
         //String user1 = (String)request.getAttribute("user1");
         //String user2 = (String)request.getAttribute("user2");
         //String user3 = (String)request.getAttribute("user3");
         //String user4 = (String)request.getAttribute("user4");
         //String user5 = (String)request.getAttribute("user5");
    %>
        
    
        
    <%! // gibt zurück, ob die der Benutzer ein Häckchen in das Kästschen an der stelle b gesetzt hat
        public boolean answerCheck(int[] a, int b){
            for(int i=0;i<a.length;i++){
                    if(a[i]==b){
                     return true;
                    }
            }
            return false;
        }       
        
        //Gibt einen Hacken zurückfalls die richtige Antwort angekreuz wurde, oder keine falsche antwort. Sonst wird ein Kreuz ausgegeben
        public String correct(int pos,String correct, int [] santworten){
              if(correct.equals("1")&&answerCheck(santworten,pos)==true){ 
                return "✔";
            }if(!correct.equals("1")&& answerCheck(santworten,pos)==false){
               return "✔";
              }else
              return "\u2718";
        }
        %>
        
          <div data-role="header" data-theme="b" id="header">            
                <form action="LoginControllerServlet" method="post" class="ui-btn-left">
                    <button data-theme="a">Back</button>
                </form>    
                <h1>  L'Odyssee </h1>           
                <form action="LogoutController" method=""post class="ui-btn-right">
                    <button data-theme="a">Logout</button>
                </form>     
            </div>
        <div data-role="content" style="margin:10px">
        
        <p><%out.println("Du spielst gegen: "+namegegner);%><p>
        
        <p> <b>Frage</b>: <%out.println(frage);%>
      
       
        <form action="VerarbeitungsControllerMulti?res=<%out.print(res);%>" method="post">
            <fieldset data-role="controlgroup" data-theme="a">
                <label>
                <input name="antwort" type="Checkbox" value="a1"<%if(abgegeben!=null &&answerCheck(santworten,1))out.println("checked=\"checked\"");
                out.println(antwort1);
                if(checkanswer.equals("1"))out.println(correct(1,correct1,santworten));%> 
                </label><br>
                
                <input name="antwort" type="Checkbox" value="a2"<%if(abgegeben!=null &&answerCheck(santworten,2))out.println("checked=\"checked\"");%>><%out.println(antwort2);%><% if(checkanswer.equals("1"))out.println(correct(2,correct2,santworten));%> <br>
                <input name="antwort" type="Checkbox" value="a3"<%if(abgegeben!=null &&answerCheck(santworten,3))out.println("checked=\"checked\"");%>><%out.println(antwort3);%><% if(checkanswer.equals("1"))out.println(correct(3,correct3,santworten));%><br>
                <input name="antwort" type="Checkbox" value="a4"<%if(abgegeben!=null &&answerCheck(santworten,4))out.println("checked=\"checked\"");%>><%out.println(antwort4);%><% if(checkanswer.equals("1"))out.println(correct(4,correct4,santworten));%><br>
                <input name="antwort" type="Checkbox" value="a5"<%if(abgegeben!=null &&answerCheck(santworten,5))out.println("checked=\"checked\"");%>><%out.println(antwort5);%><% if(checkanswer.equals("1"))out.println(correct(5,correct5,santworten));%><br>
        
        <% /* if(!antwort5.equals("")){
            out.println("<input name=\"antwort\" type=\"Checkbox\" value=\"a5\">"+antwort5); 
            if(checkanswer.equals("1"+ ""))
                out.println(correct(5,correct5, santworten));
            }
            */
         %>
            </fieldset>
        </p>
       
            <button>nächste Frage</button>
            
        </form>
    </div>          
        <div data-role="footer" data-theme="c" id="footer"><h1></h1></div>
       
    </body>
</html>
