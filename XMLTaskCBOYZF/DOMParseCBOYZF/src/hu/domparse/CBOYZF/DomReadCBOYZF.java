package hu.domparse.CBOYZF;
//Szükséges importok használata,w3c ileltve DOM parserek behúzása
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class DomReadCBOYZF {

    public static void main(String[] args) {
    	//mivel IO művelet ezért a kivitelkezelés céljából try-catch blokba foglaljuk, itt még a parsert is dobhat exceptiont
        try {
            // XML fájl betöltése
            File xmlFile = new File("C:\\Egyetem\\CBOYZF_XMLGyak\\XMLTaskCBOYZF\\XMLCBOYZF.xml");
            //Factory builder létrehozása
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // A gyökérelem megkeresése
            Element rootElement = doc.getDocumentElement();

            // XML fájl tartalmának kiírása a konzolra 
            writeToConsole(rootElement, "");
            // XML fájl tartalmának kiírása fájlba 
            writeToFile(rootElement, "", "XMLCBOYZF1.xml");
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
        	//esetleges kivétel kiírása konzolra.
            e.printStackTrace();
        }
    }

    // Rekurzív metódus a konzolra történő kiírásra
    public static void writeToConsole(Node node, String indent) {
    	//Lekérjük a node típusát, megnézzük valid típus-e és ha igen, kiíratjuk a nevét, valamit közt (indent)-et rakunk bele
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            System.out.println(indent + "<" + node.getNodeName() + ">");
            indent += "    ";
        }
        
        //lekérjuk az elem "gyerek" elemeinek listáját, olyan típusok esetén lehet szükséges ilyesmi amikor egy típus más típusokat tartalmaz 
        NodeList childNodes = node.getChildNodes();
        //for ciklussal kiíratjuk ezeket az elemeket
        for (int i = 0; i < childNodes.getLength(); i++) {
        	//meghívjuk ugyan ezt a metódust a gyerek elemekre is, mivel ugyan azt a node struktúrát használják
            writeToConsole(childNodes.item(i), indent);
        }
        
        //Ugyan azt végezzük el mint a 41.sorban, csak ügyelve arra hogy ez az elem vége, ennek megfelelően indentelve
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            System.out.println(indent + "</" + node.getNodeName() + ">");
        }
    }

    // Rekurzív metódus az fájlba történő kiírásra
    public static void writeToFile(Node node, String indent, String filename) {
    	//mivel IO művelet ezért a kivitelkezelés céljából try-catch blokba foglaljuk
        try (FileWriter writer = new FileWriter(filename, true)) {
        	//Itt a léynegi művelet ugyan az mint a 41.sorban, a küönbség abban áll hogy a console helyett, a write.write parancsal írjuk fájlba.
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                writer.write(indent + "<" + node.getNodeName() + ">\n");
                indent += "    ";
            }

            NodeList childNodes = node.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
            	//a 65.sorban található magyarázat
                writeToFile(childNodes.item(i), indent, filename);
            }

            if (node.getNodeType() == Node.ELEMENT_NODE) {
            	//a 65.sorban található magyarázat
                writer.write(indent + "</" + node.getNodeName() + ">\n");
            }
        } catch (IOException e) {
        	//esetleges kivétel kiírása konzolra.
            e.printStackTrace();
        }
    }
}
