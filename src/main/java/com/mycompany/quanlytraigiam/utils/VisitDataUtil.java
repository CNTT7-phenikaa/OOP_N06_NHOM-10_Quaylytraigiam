/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlytraigiam.utils;
import com.mycompany.quanlytraigiam.entity.Visit;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Admin
 */
public class VisitDataUtil {
    private static final String FILE_PATH = "visit.xml";
    public static void saveVisits(List<Visit>visits){
        try
        {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc =builder.newDocument();
            Element root = doc.createElement("visits");
            doc.appendChild(root);
            
            for(Visit v : visits){
                Element visit = doc.createElement("visit");
                
                visit.appendChild(createElement(doc, "InmateId", v.getInmateID()));
                visit.appendChild(createElement(doc, "PrisonerName", v.getPrisonerName()));
                visit.appendChild(createElement(doc, "VisitorName", v.getVisitorName()));
                visit.appendChild(createElement(doc, "Relationship", v.getRelationship()));
                visit.appendChild(createElement(doc, "VisitDate", v.getVisitDate()));
                visit.appendChild(createElement(doc, "VisitTime", v.getVisitTime()));
                visit.appendChild(createElement(doc, "Notes", v.getNotes()));
                
                root.appendChild(visit);
            }
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.transform(new DOMSource(doc), new StreamResult(new File(FILE_PATH)));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static List<Visit> loadVisits(){
        List<Visit>visits = new ArrayList<>();
        try {
            File file = new File (FILE_PATH);
            if (!file.exists()) return visits;
            
            DocumentBuilder builer = DocumentBuilerFactory.newInstance().newDocumentBuilder();
            Document doc = builer.parse(file);
            NodeList nodes = doc.getElementsByTagName("visit");
            
            for (int i = 0; i < nodes.getLength(); i++){
                Element el = (Element) nodes.item(i);
                visits.add(new Visit(
                    getText(el, "InmateId"),
                    getText(el, "PrisonerName"),
                    getText(el, "VisitorName"),
                    getText(el, "Relationship"),
                    getText(el, "VisitDate"),
                    getText(el, "VisitTime"),
                    getText(el, "Notes")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return visits;
    }
    private static Element createElement (Document doc, String name, String value){
        Element e = doc.createElement(name);
        e.appendChild(doc.createTextNode(value));
        return e;
    }
    private static String getText (Element el, String tag){
        return el.getElementsByTagName(tag).item(0).getTextContent();
    }
}
