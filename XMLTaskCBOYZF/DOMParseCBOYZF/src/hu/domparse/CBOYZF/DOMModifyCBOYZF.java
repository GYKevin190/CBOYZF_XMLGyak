package hu.domparse.CBOYZF;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import java.io.*;

public class DOMModifyCBOYZF {
    public static void main(String[] args) {
        try {
            // XML fájl beolvasása
            File xmlFile = new File("C:\\Egyetem\\CBOYZF_XMLGyak\\XMLTaskCBOYZF\\XMLCBOYZF.xml");
            // builder factoryk létrehozása
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            //létrehozom a doc-ot amit később a transformhoz használok fel
            Document doc = dBuilder.parse(xmlFile);

            // lekérjük egy adott típushoz tartozó összes elemet amit egy listában tárolunk el
            NodeList raktarList = doc.getElementsByTagName("raktár");
            //lekérjük azt az elemet a listából amelyiket módosítani szeretnénk, itt index alapján történik a módosítás
            Element raktar = (Element) raktarList.item(0);
            //az elemnek megkeressük azt a tagjét amit módosítani szeretnénk, majd a tartlmát (content)-et beállítjuk a megfelelőre
            raktar.getElementsByTagName("Város").item(0).setTextContent("Baskó");
            
            //a felső mintájára elvégzek még 4 módosítást.
            
            NodeList beszallítoList = doc.getElementsByTagName("beszállító");
            Element beszállító = (Element) beszallítoList.item(0);
            beszállító.getElementsByTagName("ár").item(0).setTextContent("999999");
            
            NodeList gyarihibasList = doc.getElementsByTagName("Gyárihibás");
            Element gyarihibas = (Element) gyarihibasList.item(1);
            gyarihibas.getElementsByTagName("márka").item(0).setTextContent("Huawei");
            
            NodeList futarList = doc.getElementsByTagName("futár");
            Element futar = (Element) futarList.item(1);
            futar.getElementsByTagName("Helyzet").item(0).setTextContent("ebédszünet");
            
            NodeList ugyfelList = doc.getElementsByTagName("ügyfél");
            Element ugyfel = (Element) ugyfelList.item(1);
            ugyfel.getElementsByTagName("VásároltTermék").item(0).setTextContent("Playstation 5");
            
            // Kiírjuk a módosított XML fájlt konzolra
            //A konzolra íratáshoz transformerFactoryt használok, mivel ez a leg egyszerűbb módja
            //Létrehozok a factoryból egy új példányt (instancet)
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            //Beállítom a transformert
            Transformer transformer = transformerFactory.newTransformer();
            //Megadom a forrás fájlt amit fent létrehoztam
            DOMSource source = new DOMSource(doc);
            //Megnyitom a streamet és konzolra kiíratom sys.out-al a fájlt
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
