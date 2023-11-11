package hu.domparse.CBOYZF;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class DOMQueryCBOYZF {
    public static void main(String[] args) {
        try {
            // XML fájl beolvasása és DOM létrehozása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("C:\\\\Egyetem\\\\CBOYZF_XMLGyak\\\\XMLTaskCBOYZF\\\\XMLCBOYZF.xml"));

            // 1. Lekérdezés: "02"-es ID-jú raktár adatai
            String raktarId = "02";
            NodeList raktarList = document.getElementsByTagName("raktár");
            for (int i = 0; i < raktarList.getLength(); i++) {
                Element raktar = (Element) raktarList.item(i);
                String raktarIdAttribute = raktar.getAttribute("raktár_id");
                if (raktarIdAttribute.equals(raktarId)) {
                    String ar = raktar.getElementsByTagName("ár").item(0).getTextContent();
                    String raktarbaErkezes = raktar.getElementsByTagName("raktárba_érkezés").item(0).getTextContent();
                    System.out.println("Az '" + raktarId + "' ID-jú raktárban található termék ára: " + ar);
                    System.out.println("Raktárba érkezés időpontja: " + raktarbaErkezes);
                    break;
                }
            }
         // Lekérdezés 2: Az "12"-es ID-jú gyárihibás termék raktárának címe
            String gyariHibasId = "12";
            NodeList gyariHibasList = document.getElementsByTagName("Gyárihibás");
            for (int i = 0; i < gyariHibasList.getLength(); i++) {
                Element gyariHibas = (Element) gyariHibasList.item(i);
                if (gyariHibas.getAttribute("gyárihibás_id").equals(gyariHibasId)) {
                    String raktarIdOfGyariHibas = gyariHibas.getAttribute("raktár");
                    NodeList raktarListForGyariHibas = document.getElementsByTagName("raktár");
                    for (int j = 0; j < raktarListForGyariHibas.getLength(); j++) {
                        Element raktar = (Element) raktarListForGyariHibas.item(j);
                        if (raktar.getAttribute("raktár_id").equals(raktarIdOfGyariHibas)) {
                            Element cim = (Element) raktar.getElementsByTagName("cím").item(0);
                            String isz = cim.getElementsByTagName("Isz").item(0).getTextContent().trim();
                            String varos = cim.getElementsByTagName("Város").item(0).getTextContent().trim();
                            String utca = cim.getElementsByTagName("Utca").item(0).getTextContent().trim();
                            String hazszam = cim.getElementsByTagName("házszám").item(0).getTextContent().trim();
                            System.out.println("Lekérdezés 2:");
                            System.out.println("Cím: " + isz + " " + varos + " " + utca + " " + hazszam);
                            break;
                        }
                    }
                    break;
                }
            }
            
         // Lekérdezés 3: A "23"-as beszállítóhoz tartozó termék darabszáma
            String beszallitoId = "23";
            NodeList bRList = document.getElementsByTagName("B_R");
            int darabszam = 0;
            for (int i = 0; i < bRList.getLength(); i++) {
                Element bR = (Element) bRList.item(i);
                String beszallitoIdInBR = bR.getAttribute("beszállító");
                if (beszallitoIdInBR.equals(beszallitoId)) {
                    darabszam += Integer.parseInt(bR.getAttribute("darabszám"));
                }
            }

            System.out.println("Lekérdezés 3:");
            System.out.println("A \"23\"-as beszállítóhoz tartozó termék darabszáma: " + darabszam);
            
         // Lekérdezés 4: Azon ügyfeleknél lévő termék és telefonszámának kiírása, akiknek több telefonszámuk van
            NodeList ugyfelList = document.getElementsByTagName("ügyfél");
            for (int i = 0; i < ugyfelList.getLength(); i++) {
                Element ugyfel = (Element) ugyfelList.item(i);
                NodeList telefonszamList = ugyfel.getElementsByTagName("Telefonszám");
                
                if (telefonszamList.getLength() > 1) {
                    String nev = ugyfel.getElementsByTagName("VásároltTermék").item(0).getTextContent().trim();
                    
                    System.out.println("Lekérdezés 4:");
                    System.out.println("Termék neve: " + nev);
                    System.out.println("Telefonszám(ok):");
                    
                    for (int j = 0; j < telefonszamList.getLength(); j++) {
                        Element telefonszam = (Element) telefonszamList.item(j);
                        System.out.println(telefonszam.getTextContent().trim());
                    }
                }

            }
            
         // Lekérdezés 5: Azon futárok nevének kiírása, akiknél az áru értéke 2000 fölött van
            NodeList futarList = document.getElementsByTagName("futár");
            System.out.println("Lekérdezés 5:");
            for (int i = 0; i < futarList.getLength(); i++) {
                Element futar = (Element) futarList.item(i);
                int ar = Integer.parseInt(futar.getElementsByTagName("ár").item(0).getTextContent().trim());
                
                if (ar > 2000) {
                    String helyzet = futar.getElementsByTagName("Helyzet").item(0).getTextContent().trim();
                    String id = futar.getAttribute("Rendelés_szám");
                    System.out.println("A rendelés id-ja:" + id +" a futár helyzete: " + helyzet);
                }
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
