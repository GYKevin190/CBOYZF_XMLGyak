package hu.domparse.CBOYZF;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMWriteCBOYZF {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("C:\\Egyetem\\CBOYZF_XMLGyak\\XMLTaskCBOYZF\\XMLCBOYZF.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList raktarList = doc.getElementsByTagName("raktár");
            NodeList gyarihibasList = doc.getElementsByTagName("Gyárihibás");
            NodeList brList = doc.getElementsByTagName("B_R");
            NodeList beszallitoList = doc.getElementsByTagName("beszállító");
            NodeList futarList = doc.getElementsByTagName("futár");
            NodeList ugyfelList = doc.getElementsByTagName("ügyfél");

            File outputFile = new File("C:\\Egyetem\\CBOYZF_XMLGyak\\XMLTaskCBOYZF\\XMLCBOYZF1.xml");
            FileWriter writer = new FileWriter(outputFile);
            PrintWriter consoleWriter = new PrintWriter(System.out, true);

            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<Csomag_követés_CBOYZF xmlns:xs=\"http://www.w3.org/2001/XMLSchema-instance\" xs:noNamespaceSchemaLocation=\"XMLSchemaCBOYZF.xsd\">\n");

            for (int i = 0; i < raktarList.getLength(); i++) {
                Node raktar = raktarList.item(i);
                String raktarString = nodeToString(raktar);
                writer.write("    " + raktarString);
                consoleWriter.println("    " + raktarString);
            }

            for (int i = 0; i < gyarihibasList.getLength(); i++) {
                Node gyarihibas = gyarihibasList.item(i);
                String gyarihibasString = nodeToString(gyarihibas);
                writer.write("    " + gyarihibasString);
                consoleWriter.println("    " + gyarihibasString);
            }

            for (int i = 0; i < brList.getLength(); i++) {
                Node br = brList.item(i);
                String brString = nodeToString(br);
                writer.write("    " + brString);
                consoleWriter.println("    " + brString);
            }

            for (int i = 0; i < beszallitoList.getLength(); i++) {
                Node beszallito = beszallitoList.item(i);
                String beszallitoString = nodeToString(beszallito);
                writer.write("    " + beszallitoString);
                consoleWriter.println("    " + beszallitoString);
            }

            for (int i = 0; i < futarList.getLength(); i++) {
                Node futar = futarList.item(i);
                String futarString = nodeToString(futar);
                writer.write("    " + futarString);
                consoleWriter.println("    " + futarString);
            }

            for (int i = 0; i < ugyfelList.getLength(); i++) {
                Node ugyfel = ugyfelList.item(i);
                String ugyfelString = nodeToString(ugyfel);
                writer.write("    " + ugyfelString);
                consoleWriter.println("    " + ugyfelString);
            }

            writer.write("</Csomag_követés_CBOYZF>");
            writer.close();

            //System.out.println("A XML tartalom sikeresen ki lett írva a fájlba: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String nodeToString(Node node) {
        return nodeToString(node, 0);
    }

    private static String nodeToString(Node node, int depth) {
        StringBuilder result = new StringBuilder();

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            result.append(getIndentation(depth));
            result.append("<");
            result.append(node.getNodeName());

            if (node.hasAttributes()) {
                for (int i = 0; i < node.getAttributes().getLength(); i++) {
                    Node attribute = node.getAttributes().item(i);
                    result.append(" ");
                    result.append(attribute.getNodeName());
                    result.append("=\"");
                    result.append(attribute.getNodeValue());
                    result.append("\"");
                }
            }

            result.append(">\n");
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            String text = node.getTextContent().trim();
            if (!text.isEmpty()) {
                result.append(getIndentation(depth));
                result.append(text);
                result.append("\n");
            }
        }

        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            result.append(nodeToString(childNode, depth + 1));
        }

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            result.append(getIndentation(depth));
            result.append("</");
            result.append(node.getNodeName());
            result.append(">\n");
        }

        return result.toString();
    }

    private static String getIndentation(int depth) {
        StringBuilder indentation = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indentation.append("    ");
        }
        return indentation.toString();
    }
}
