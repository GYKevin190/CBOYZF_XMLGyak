package hu.domparse.CBOYZF;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class DomReadCBOYZF {
    public static void main(String[] args) {
        try {
        	// Megnyitjuk az XML fájlt
            File xmlFile = new File("C:\\Egyetem\\CBOYZF_XMLGyak\\XMLTaskCBOYZF\\XMLCBOYZF.xml");
            // Létrehozzuk a szükséges factory buildereket
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            // Parseljük a megnyitott XML dokumentumot
            Document doc = dBuilder.parse(xmlFile);
            // Megkeressük a gyökér elemet
            Element rootElement = doc.getDocumentElement();
            // Try-catch blokkban folytatjuk a kiírást, mivel a fájlba írás I/O művelet
            try (FileWriter writer = new FileWriter("XMLCBOYZF1.xml")) {
                writeToConsoleAndFile(writer, rootElement, "");
            }
        	} catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
        		// Kiírjuk a felmerülő kivételeket (parser, I/O, SAX exception)
            e.printStackTrace();
        	}
    }

    public static void writeToConsoleAndFile(FileWriter writer, Node node, String indent) {
    	// Try-catch blokkban folytatjuk a kiírást, mivel a fájlba írás I/O művelet
        try {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
            	// A node a gyökérelemünk, kiíratjuk a nevét a fájl elejére, az indent illetve < > jelek a formázáshoz szükségesek
                Element element = (Element) node;
                String attributes = getAttributesAsString(element);
                System.out.println(indent + "<" + node.getNodeName() + " " + attributes + ">" + " start");
                writer.write(indent + "<" + node.getNodeName() + " " + attributes + ">" + " start\n");
                indent += "    ";
            } else if (node.getNodeType() == Node.TEXT_NODE) {
            	// Ha szöveges node, kiírjuk a hozzá tartozó szöveget is, amit egy string változóban trim-elünk le
                Text textNode = (Text) node;
                String nodeValue = textNode.getWholeText().trim();
                // Megnézzük, nem-e üres az adott elem
                if (!nodeValue.isEmpty()) {
                	 // Konzolra írjuk az eredményt
                    System.out.println(indent + nodeValue);
                  // Fájlba írjuk az eredményt
                    writer.write(indent + nodeValue + "\n");
                }
            }
            
            // Megnézzük, az elemnek van-e gyerek eleme
            NodeList childNodes = node.getChildNodes();
            // Ha van, akkor azon is végigmegyünk rekurzívan ismét
            for (int i = 0; i < childNodes.getLength(); i++) {
                writeToConsoleAndFile(writer, childNodes.item(i), indent);
            }

            if (node.getNodeType() == Node.ELEMENT_NODE) {
            	// Beállítjuk megfelelően a formázást fájl esetére is
                indent = indent.substring(0, indent.length() - 4);
                // Konzolra írjuk az eredményt
                System.out.println(indent + "</" + node.getNodeName() + ">" + " end");
                // Fájlba írjuk az eredményt
                writer.write(indent + "</" + node.getNodeName() + ">" + " end\n");
            }
        } catch (IOException e) {
        	// Kiírjuk a felmerülő kivételeket
            e.printStackTrace();
        }
    }

    private static String getAttributesAsString(Element element) {
        // Létrehozunk egy StringBuilder objektumot az attribútumok összeállításához.
        StringBuilder attributes = new StringBuilder();

        // Ellenőrizzük, hogy az elem rendelkezik-e attribútumokkal.
        if (element.hasAttributes()) {
            // Lekérjük az elem attribútumait tartalmazó NamedNodeMap objektumot.
            NamedNodeMap attrMap = element.getAttributes();

            // Végigiterálunk az attribútumokon.
            for (int i = 0; i < attrMap.getLength(); i++) {
                // Lekérjük az aktuális attribútumot.
                Node attr = attrMap.item(i);

                // Hozzáadjuk az attribútum nevét és értékét a StringBuilder objektumhoz.
                attributes.append(attr.getNodeName()).append("=\"").append(attr.getNodeValue()).append("\" ");
            }
        }
        return attributes.toString();
    }
}
