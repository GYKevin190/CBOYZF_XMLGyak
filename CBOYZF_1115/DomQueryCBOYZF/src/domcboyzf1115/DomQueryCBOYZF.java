package domcboyzf1115;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;


public class DomQueryCBOYZF {
	public static void main(String[] args) {
        try {
            File xmlFile = new File("C:\Egyetem\CBOYZF_XMLGyak\CBOYZF_1108\\CBOYZF_kurzusfelvetel.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            List<String> kurzusNevek = getKurzusNevek(doc);
            Element hallgatoElement = getHallgatoElement(doc);

            System.out.println("Kurzusn�v: " + kurzusNevek);
            System.out.println("Hallgat� adatai:");
            printElement(hallgatoElement, 0);
            
            writeElementToFile(hallgatoElement, "hallgato_adatok.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> getKurzusNevek(Document doc) {
        List<String> kurzusNevek = new ArrayList<>();
        NodeList kurzusNodeList = doc.getElementsByTagName("kurzusnev");

        for (int i = 0; i < kurzusNodeList.getLength(); i++) {
            Node node = kurzusNodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                kurzusNevek.add(element.getTextContent());
            }
        }

        return kurzusNevek;
    }
    
    private static Element getHallgatoElement(Document doc) {
        NodeList hallgatoNodeList = doc.getElementsByTagName("hallgato");
        return (Element) hallgatoNodeList.item(0);
    }

    private static void printElement(Element element, int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("  ");
        }
        System.out.println(element.getTagName() + ": " + element.getTextContent());

        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                printElement((Element) node, indent + 1);
            }
        }
    }

    private static void writeElementToFile(Element element, String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writeElementToFile(element, writer, 0);
        writer.close();
    }

    private static void writeElementToFile(Element element, FileWriter writer, int indent) throws IOException {
        for (int i = 0; i < indent; i++) {
            writer.append("  ");
        }
        writer.append(element.getTagName()).append(": ").append(element.getTextContent()).append("\n");

        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                writeElementToFile((Element) node, writer, indent + 1);
            }
        }
    }

}