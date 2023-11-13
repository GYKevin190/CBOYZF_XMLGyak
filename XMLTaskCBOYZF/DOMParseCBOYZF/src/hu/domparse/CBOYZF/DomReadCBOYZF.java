package hu.domparse.CBOYZF;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringJoiner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class DomReadCBOYZF {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("C:\\Egyetem\\CBOYZF_XMLGyak\\XMLTaskCBOYZF\\XMLCBOYZF.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            File outputFile = new File("output2.xml");
            PrintWriter writer = new PrintWriter(new FileWriter(outputFile, true));

            // Kiírjuk az XML főgyökér elemét a konzolra és fájlba
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            StringJoiner rootAttributes = new StringJoiner(" ");
            NamedNodeMap rootAttributeMap = rootElement.getAttributes();

            for (int i = 0; i < rootAttributeMap.getLength(); i++) {
                Node attribute = rootAttributeMap.item(i);
                rootAttributes.add(attribute.getNodeName() + "=\"" + attribute.getNodeValue() + "\"");
            }
            
            
            
            System.out.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n");
            writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

            System.out.print("<" + rootName + " " + rootAttributes.toString() + "> \n");
            writer.print("<" + rootName + " " + rootAttributes.toString() + "> \n");

            NodeList raktarList = doc.getElementsByTagName("raktár");
            NodeList gyarihibasList = doc.getElementsByTagName("Gyárihibás");
            NodeList brList = doc.getElementsByTagName("B_R");
            NodeList beszallitoList = doc.getElementsByTagName("beszállító");
            NodeList futarList = doc.getElementsByTagName("futár");
            NodeList ugyfelList = doc.getElementsByTagName("ügyfél");

            // Kiírjuk az XML-t a konzolra megtartva az eredeti formázást
            printNodeList(raktarList, writer);
            System.out.println("");
            writer.println("");
            printNodeList(gyarihibasList, writer);
            System.out.println("");
            writer.println("");
            printNodeList(brList, writer);
            System.out.println("");
            writer.println("");
            printNodeList(beszallitoList, writer);
            System.out.println("");
            writer.println("");
            printNodeList(futarList, writer);
            System.out.println("");
            writer.println("");
            printNodeList(ugyfelList, writer);

            // Zárjuk le az XML gyökér elemét
            System.out.println("</" + rootName + ">");
            writer.append("</" + rootName + ">");

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Rekurzív függvény a NodeList tartalmának kiírására
    private static void printNodeList(NodeList nodeList, PrintWriter writer) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            printNode(node, 0, writer);
            System.out.println(""); // Üres sor hozzáadása az elemek között
            writer.println(""); // Üres sor hozzáadása a fájlban az elemek között
        }
    }

    // Rekurzív függvény a Node tartalmának kiírására
    private static void printNode(Node node, int indent, PrintWriter writer) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String nodeName = element.getTagName();
            StringJoiner attributes = new StringJoiner(" ");
            NamedNodeMap attributeMap = element.getAttributes();

            for (int i = 0; i < attributeMap.getLength(); i++) {
                Node attribute = attributeMap.item(i);
                attributes.add(attribute.getNodeName() + "=\"" + attribute.getNodeValue() + "\"");
            }

            System.out.print(getIndentString(indent));
            System.out.print("<" + nodeName + " " + attributes.toString() + ">");

            writer.print(getIndentString(indent));
            writer.print("<" + nodeName + " " + attributes.toString() + ">");

            NodeList children = element.getChildNodes();
            if (children.getLength() == 1 && children.item(0).getNodeType() == Node.TEXT_NODE) {
                System.out.print(children.item(0).getNodeValue());
                writer.print(children.item(0).getNodeValue());
            } else {
                System.out.println();
                writer.println();
                for (int i = 0; i < children.getLength(); i++) {
                    printNode(children.item(i), indent + 1, writer);
                }
                System.out.print(getIndentString(indent));
                writer.print(getIndentString(indent));
            }
            System.out.println("</" + nodeName + ">");
            writer.println("</" + nodeName + ">");
        }
    }

    // Segédmetódus az indentáláshoz
    private static String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("  "); // 2 spaces per indent level
        }
        return sb.toString();
    }
}
