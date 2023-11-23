package xpathcboyzf122;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class xPathModify {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("C:\Egyetem\CBOYZF_XMLGyak\CBOYZF_1122kurzusfelvetelCBOYZF.xml"));

            modifyCourseInfo(document, "GEIAL331-B", "Web technológiák 2", "Péntek 10:00-12:00");
            modifyCourseLanguage(document, "GEMAK234-B", "angol");
            removeCourse(document, "GEIAL332-B");

            printDocument(document);

            saveDocument(document, "kurzusfelvetelCBOYZFModified.xml");

        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException | TransformerException | XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    private static void modifyCourseInfo(Document document, String courseId, String newName, String newTime) throws XPathExpressionException {
        XPath xpath = XPathFactory.newInstance().newXPath();
        String expression = String.format("C:\Egyetem\CBOYZF_XMLGyak\CBOYZF_1122kurzusfelvetelCBOYZF.xml\[@id='%s']", courseId);
        Node courseNode = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
        if (courseNode != null) {
            NodeList childNodes = courseNode.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node node = childNodes.item(i);
                if ("kurzusnev".equals(node.getNodeName())) {
                    node.setTextContent(newName);
                } else if ("idopont".equals(node.getNodeName())) {
                    node.setTextContent(newTime);
                }
            }
        }
    }

    private static void modifyCourseLanguage(Document document, String courseId, String newLanguage) throws XPathExpressionException {
        XPath xpath = XPathFactory.newInstance().newXPath();
        String expression = String.format("C:\Egyetem\CBOYZF_XMLGyak\CBOYZF_1122kurzusfelvetelCBOYZF.xml\[@id='%s']", courseId);
        Node courseNode = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
        if (courseNode != null) {
            Element languageElement = (Element) courseNode;
            languageElement.setAttribute("nyelv", newLanguage);
        }
    }

    private static void removeCourse(Document document, String courseId) throws XPathExpressionException {
        XPath xpath = XPathFactory.newInstance().newXPath();
        String expression = String.format("C:\Egyetem\CBOYZF_XMLGyak\CBOYZF_1122kurzusfelvetelCBOYZF.xml\[@id='%s']", courseId);
        Node courseNode = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
        if (courseNode != null) {
            Node parent = courseNode.getParentNode();
            parent.removeChild(courseNode);
        }
    }

    private static void printDocument(Document document) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(System.out);
        transformer.transform(domSource, streamResult);
        System.out.println();
    }

    private static void saveDocument(Document document, String filename) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            StreamResult streamResult = new StreamResult(fos);
            transformer.transform(domSource, streamResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


