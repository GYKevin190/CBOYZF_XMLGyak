package domCBOYZF1108;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class DomReadAlternativeCBOYZF
{
    
    public static void main(String[] args)
    {
        try
        {
            File inputFile = new File("CBOYZF_kurzusfelvetel.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            printNode(doc.getDocumentElement(), "");
            writeDocumentToFile(doc, "CBOYZF_kurzusfelvetel.xml");
            
            System.out.println("The content has been written to KLNSPG_orarend1.xml successfully.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void printNode(Node node, String indent)
    {
        System.out.print(indent + "Node: " + node.getNodeName());
        
        if (node.hasAttributes())
        {
            NamedNodeMap nodeMap = node.getAttributes();
            System.out.print(", Attributes: [");

            for (int i = 0; i < nodeMap.getLength(); i++)
            {
                Node attr = nodeMap.item(i);
                System.out.print(attr.getNodeName() + "=" + "\"" + attr.getNodeValue() + "\"");
                if (i < nodeMap.getLength() - 1)
                    System.out.print(", ");
            }

            System.out.print("]");
        }

        String textContent = node.getTextContent().trim();
        
        if (!textContent.isEmpty() && !node.hasChildNodes())
            System.out.print(", Content: \"" + textContent + "\"");

        System.out.println();

        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++)
            printNode(children.item(i), indent + "  ");
    }

    private static void writeDocumentToFile(Document doc, String filename) throws TransformerException
    {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filename));
        transformer.transform(source, result);
    }
}