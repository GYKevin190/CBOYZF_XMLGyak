package xpathcboyzf;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

public class xPathCBOYZF {

	public static void main(String[] args) {
        try {
        	InputStream inputStream = new FileInputStream("C:\Egyetem\CBOYZF_XMLGyak\CBOYZF_1122kurzusfelvetelCBOYZF.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            XPathExpression expr = xPath.compile("/class/student");
            //XPathExpression expr = xPath.compile("/class/student[@id='02']");
            //XPathExpression expr = xPath.compile("//student");
            //XPathExpression expr = xPath.compile("/class/student[position() = 2]");
            //XPathExpression expr = xPath.compile("/class/student[last()]");
            //XPathExpression expr = xPath.compile("/class/student[position() = last() - 1]");
            //XPathExpression expr = xPath.compile("/class/student[position() <= 2]");
            //XPathExpression expr = xPath.compile("/class/*");
            //XPathExpression expr = xPath.compile("//student[@*]");
            //XPathExpression expr = xPath.compile("//*");
            //XPathExpression expr = xPath.compile("/class//*[kor > 20]");
            //XPathExpression expr = xPath.compile("//student/(keresztnev | vezeteknev)");
            
            NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    System.out.println("ID: " + element.getAttribute("id"));
                    System.out.println("Keresztnév: " + element.getElementsByTagName("keresztnev").item(0).getTextContent());
                    System.out.println("Vezetéknév: " + element.getElementsByTagName("vezeteknev").item(0).getTextContent());
                    System.out.println("Becenév: " + element.getElementsByTagName("becenev").item(0).getTextContent());
                    System.out.println("Kor: " + element.getElementsByTagName("kor").item(0).getTextContent());
                    System.out.println();
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
        }
    }
}
