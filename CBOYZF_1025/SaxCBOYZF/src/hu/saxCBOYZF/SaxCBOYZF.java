package hu.saxCBOYZF;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxCBOYZF {
    public static void main(String[] args) {

        File xml = new File("C:\\Egyetem\\CBOYZF_XMLGyak\\CBOYZF_1011\\CBOYZF_kurzusfelvetel.xml");

        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            SaxHandler handler = new SaxHandler();
            saxParser.parse(xml, handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }

}

class SaxHandler extends DefaultHandler {

    private int indent = 0;

    private String formatAttributes(Attributes attributes) {
        int attrLength = attributes.getLength();
        if (attrLength == 0)
            return "";

        StringBuilder sb = new StringBuilder(", {");
        for (int i = 0; i < attrLength; i++) {
            sb.append(attributes.getLocalName(i) + "=" + attributes.getValue(i));
            if (i < attrLength - 1)
                sb.append(", ");
        }
        sb.append("}");
        return sb.toString();

    }

    private void indent() {
        for (int i = 0; i < indent; i++) {
            System.out.print("  ");
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        indent++;
        indent();
        System.out.print(qName + formatAttributes(attributes) + " start\n");
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        indent();
        indent--;
        System.out.print(qName + " end\n");
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String chars = new String(ch, start, length).trim();
        if (!chars.isEmpty()) {
            indent++;
            indent();
            indent--;
            System.out.print(chars + "\n");
        }
    }
}
