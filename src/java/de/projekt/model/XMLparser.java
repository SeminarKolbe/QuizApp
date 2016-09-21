
package de.projekt.model;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
//...





public class XMLparser extends DatenbankZugang {
    
    List <String> idList= new ArrayList<>(); 
    List <String> validPoolIDs = new ArrayList<>();
    List <ID> idObjects = new ArrayList<>();
    List <ID> uniqueIdObjects = new ArrayList<>();
    List <Question> questionList = new ArrayList<>();
    List <Question> uniqueQuestionList = new ArrayList<>();
    List <File> poolFileList = new ArrayList<>();
    List <String> uniqueValidPoolIDs = new ArrayList<>();
    
    
    
    public void parsing() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, ClassNotFoundException, SQLException{

    //ParseAttempt5 obj = new ParseAttempt5(); 

    // Kompletten Ziel-Folder parsen
    File folder = new File("C:\\Users\\Marin\\PowerFolders\\Studium\\10.SemesterSS_16\\Seminar Kolbe\\Anwendung\\parser");  
    File[] listOfFiles = folder.listFiles();  
    
    
    //******Array-Liste mit Fragen erstellen, die laut Pool-Dateien erlaubt sind
    for (File file : listOfFiles) { 
        String fn = file.getName();
        
        if(fn.endsWith(".xml")||fn.endsWith(".XML")){
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            XPath xPath = XPathFactory.newInstance().newXPath();
            
            String expr = "/ContentObject/MetaData/General/Title";
            String thema = xPath.compile(expr).evaluate(document);
            
            if(!thema.isEmpty()){
                
                NodeList pageObjectList = document.getElementsByTagName("Question");
                for(int i=0; i<pageObjectList.getLength(); i++){
                    String qstID = pageObjectList.item(i).getAttributes().getNamedItem("QRef").getNodeValue();
                    validPoolIDs.add(qstID);
                    
                    // TODO --> Thema speichern
                    ID newID = new ID();
                    newID.setId(qstID);
                    newID.setTitle(thema);
                    idObjects.add(newID);
                }
            } 
        }
    }
    //**************************************************************************


    //*** Duplikate aus Fragen-Pool entfernen***********************************
    uniqueIdObjects = idObjects.parallelStream().distinct().collect(Collectors.toList());
    uniqueValidPoolIDs = validPoolIDs.parallelStream().distinct().collect(Collectors.toList());
    
        // Testausgabe von unique ID-Liste 
    
   
    //**************************************************************************
    
 
    
    //*******Questiontype, Frage, Antworten, Lösungen extrahieren***************  
    for (File file : listOfFiles) { 
        String fn = file.getName();
        
        if(fn.endsWith(".xml")||fn.endsWith(".XML")){
            
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            XPath xPath = XPathFactory.newInstance().newXPath();
           
            
            // item-List aus einem Dokument erstellen 
            String expr = "/questestinterop/item";
            NodeList itemList = (NodeList) xPath.compile(expr).evaluate(document, XPathConstants.NODESET);       
            
            // Nur-Fragen-XML Dateien sollen weiter beachten werden
            if (itemList.getLength()>0){
               
                
                // Durchlaufe die <item> Elemente
                for(int i=0; i<itemList.getLength(); i++){
                    Node node = itemList.item(i);
                    String attr = node.getAttributes().getNamedItem("ident").getNodeValue();
                   
                    
                    // Prüfung, ob <item ident=[...]> im Pool ist
                    if(uniqueValidPoolIDs.contains(attr)){

                        String expr1 = "/questestinterop/item["+(i+1)+"]/itemmetadata/qtimetadata/qtimetadatafield[2]/fieldentry";
                        String questionType = xPath.compile(expr1).evaluate(document);
                        //if(questionType.equals("MULTIPLE CHOICE QUESTION"))
                        if(questionType.charAt(0) == 'M' || questionType.charAt(0) == 'm'  )
                            questionType="m";
                        else
                            questionType="s";
                          
                       
                        
                        String expr2 = "/questestinterop/item["+(i+1)+"]/presentation/flow/material/mattext";
                        String question = xPath.compile(expr2).evaluate(document);
                        
                                
                        String expr3 = "/questestinterop/item["+(i+1)+"]/presentation/flow/response_lid/render_choice/response_label["+1+"]/material/mattext";
                        String answer1 = xPath.compile(expr3).evaluate(document);
                     
                        
                        String expr4 = "/questestinterop/item["+(i+1)+"]/presentation/flow/response_lid/render_choice/response_label["+2+"]/material/mattext";
                        String answer2 = xPath.compile(expr4).evaluate(document);
                        
                        
                        String expr5 = "/questestinterop/item["+(i+1)+"]/presentation/flow/response_lid/render_choice/response_label["+3+"]/material/mattext";
                        String answer3 = xPath.compile(expr5).evaluate(document);
                        
                        
                        String expr6 = "/questestinterop/item["+(i+1)+"]/presentation/flow/response_lid/render_choice/response_label["+4+"]/material/mattext";
                        String answer4 = xPath.compile(expr6).evaluate(document);
                       
                        
                        String expr7 = "/questestinterop/item["+(i+1)+"]/presentation/flow/response_lid/render_choice/response_label["+5+"]/material/mattext";
                        String answer5 = xPath.compile(expr7).evaluate(document);
                        
                        
                            // Speicherung von allen korrekten Antwortnummern im Array res[] //
                            int res[] = new int[5];
                            for(int j=0; j<res.length; j++){
                                String expr8 = "/questestinterop/item["+(i+1)+"]/resprocessing/respcondition["+(j+1)+"]/setvar";    
                                String temp = xPath.compile(expr8).evaluate(document);

                                if("1".equals(temp)){
                                    res[j]=1;
                                   
                                    
                                }
                                else {
                                    res[j]=0;
                                }
                            }
                            
                            // Herausfinden des Themas einer Frage ///
                            String title = "Fehler - Titel erzeugen fehlgeschlagen";
                            for(ID iterID : uniqueIdObjects ){
                                if(iterID.getId() == null ? attr == null : iterID.getId().equals(attr))
                                    title = iterID.getTitle();
                            }
    
                        // Erzeugtes Question Objekt mit allen Informationen in die Liste der Fragen einfügen
                        Question questionBean = new Question(questionType, question, answer1, answer2, answer3, answer4, answer5, title ,res);
                        questionList.add(questionBean);
                        
                    
                    }
                    else{
                        System.out.println(attr+": Keine zulässige Frage");
                    }

                    
                }     
            }
                 
        }
                   
    }

    // Duplikate aus der endgültigen QuestionListe entfernen 
    uniqueQuestionList = questionList.parallelStream().distinct().collect(Collectors.toList());
     insertDB();
    }
    
    public void insertDB()throws ClassNotFoundException, SQLException {
        connect();
        
        Statement stmt= con.createStatement();
       
        for(Question q : uniqueQuestionList){
            stmt.executeUpdate("INSERT INTO karten (thema, frage, antwort, antwort2, antwort3, antwort4, antwort5, richtig, richtig2, richtig3, richtig4, richtig5, punkte, singlemulti) VALUES ('"+q.getThema()+"','"+q.getFrage()+"','"+q.getAntwort1()+"','"+q.getAntwort2()+"','"+q.getAntwort3()+"','"+q.getAntwort4()+"','"+q.getAntwort5()+"',"+q.getCorrect1()+","+q.getCorrect2()+","+q.getCorrect3()+","+q.getCorrect4()+","+q.getCorrect5()+",10,'"+q.getQstType()+"')");
        }
        close();
        }
    
}
