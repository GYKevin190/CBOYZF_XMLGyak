package hu.domparse.CBOYZF;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class DomReadCBOYZF {
    public static void main(String[] args) {
        try {
        	//megnyitjuk az XML filet
            File xmlFile = new File("C:\\Egyetem\\CBOYZF_XMLGyak\\XMLTaskCBOYZF\\XMLCBOYZF.xml");
            //létrehozzuk a szükséges factory buildereket
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            //parseljük a megnyitott xml dokumentumot
            Document doc = dBuilder.parse(xmlFile);
            //megkeressük a gyökér elemet
            Element rootElement = doc.getDocumentElement();
            //try-catch blokban folytatjuk a kiírást, mivel a fájlba írás I/O művelet
            try (FileWriter writer = new FileWriter("XMLCBOYZF1.xml")) {
                writeToConsoleAndFile(writer, rootElement, "");
            }
        	} catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
        		//kiírjuk a felmerülő kivételeket (parser,Io,SAX exception)
            e.printStackTrace();
        	}
    }

    public static void writeToConsoleAndFile(FileWriter writer, Node node, String indent) {
    	//try-catch blokban folytatjuk a kiírást, mivel a fájlba írás I/O művelet
        try {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
            	// a node a gyökérelemünk, kiíratjuk a nevét a fájl elejére, az indent illet < > jelek a formázáshoz szükségesek
                System.out.println(indent + "<" + node.getNodeName() + ">");
                writer.write(indent + "<" + node.getNodeName() + ">\n");
                indent += "    ";
            } else if (node.getNodeType() == Node.TEXT_NODE) {
            	//ha szzöveges node kiírjuk a hozzá tartozó szöveget is, amit egy string változóban trim-elünk le
                Text textNode = (Text) node;
                String nodeValue = textNode.getWholeText().trim();
                //megnézzük nem-e ürés az adott elem
                if (!nodeValue.isEmpty()) {
                	 //konzolra írjuk az eredményt
                    System.out.println(indent + nodeValue);
                  //fájlba írjuk az eredményt
                    writer.write(indent + nodeValue + "\n");
                }
            }
            
            //megnézzük az elemenek van-e gyerek elem-e
            NodeList childNodes = node.getChildNodes();
            //ha van akkor azon is végigmegyünk megint, rekurzívan ismét.
            for (int i = 0; i < childNodes.getLength(); i++) {
                writeToConsoleAndFile(writer, childNodes.item(i), indent);
            }

            if (node.getNodeType() == Node.ELEMENT_NODE) {
            	//beállítjuk megfelelően a formázást fájl esetére is
                indent = indent.substring(0, indent.length() - 4); // Visszaállítjuk az indentálást
                //konzolra írjuk az eredményt
                System.out.println(indent + "</" + node.getNodeName() + ">");
                //fájlba írjuk az eredményt
                writer.write(indent + "</" + node.getNodeName() + ">\n");
            }
        } catch (IOException e) {
        	//kiírjuk a felmerülő kivételeket
            e.printStackTrace();
        }
    }
}
