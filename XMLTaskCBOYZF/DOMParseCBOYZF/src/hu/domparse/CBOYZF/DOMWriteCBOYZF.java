package hu.domparse.CBOYZF;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringJoiner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

public class DOMWriteCBOYZF {
  public static void main(String[] args) {
    try {
      // Create a new Document
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.newDocument();

      // Create the root element
      Element rootElement = doc.createElement("Csomag_követés_CBOYZF");
      rootElement.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema-instance");
      rootElement.setAttribute("xs:noNamespaceSchemaLocation", "XMLSchemaCBOYZF.xsd");
      doc.appendChild(rootElement);

      // Add raktárak
      addRaktar(doc, rootElement, "01", "500", "2023-10-10", "3881", "Abaújszántó", "Béke út", "1");
      addRaktar(doc, rootElement, "02", "1500", "2023-10-11", "3881", "Abaújszántó", "Béke út", "2");
      addRaktar(doc, rootElement, "03", "1500", "2023-10-12", "3860", "Encs", "Rákóczi út", "3");

      // Add gyárihibás elemek
      addGyarihibas(doc, rootElement, "11", "01", "100", "Samsung", "Telefon");
      addGyarihibas(doc, rootElement, "12", "02", "200", "Apple", "Airpods");
      addGyarihibas(doc, rootElement, "13", "03", "300", "Sony", "Playstation");

      // Add B_R kapcsoló tábla
      addBR(doc, rootElement, "5", "01", "21");
      addBR(doc, rootElement, "10", "01", "22");
      addBR(doc, rootElement, "25", "03", "23");

      // Add beszállítók
      addBeszallito(doc, rootElement, "21", "01", "2023-10-18", "50000", "Fólia");
      addBeszallito(doc, rootElement, "22", "02", "2023-10-16", "40000", "Boríték");
      addBeszallito(doc, rootElement, "23", "03", "2023-10-19", "30000", "Doboz");

      // Add futárok
      addFutar(doc, rootElement, "31", "01", "Úton", "2023-10-28", "1500");
      addFutar(doc, rootElement, "32", "02", "Áll", "2023-10-23", "4000");
      addFutar(doc, rootElement, "33", "03", "Felfüggesztve", "2023-10-29", "2500");

      // Add ügyfelek
      addUgyfel(doc, rootElement, "41", "31", "2023-10-14", "Iphone 13 pro max", "0620-714-9284",
        "3881", "Abaújszántó", "Rákóczi út", "3");
      addUgyfel(doc, rootElement, "42", "32", "2023-10-12", "Smasung galaxy A52", "0620-394-2132, 0630-153-4576",
        "3881", "Abaújszántó", "Béke út", "4");
      addUgyfel(doc, rootElement, "43", "33", "2023-10-18", "Xbox Series X", "0670-345-2376",
        "3881", "Abaújszántó", "Kazincy út", "19");

      // Transform and save to file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "2");

      DOMSource source = new DOMSource(doc);
      File myFile = new File("XMLCBOYZF1.xml");
      StreamResult file = new StreamResult(myFile);
      transformer.transform(source, file);

      // Now, print the XML content to the console and a new file
      printDocument(doc);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void addRaktar(Document doc, Element rootElement, String raktarId, String ar, String raktarbaErkezes,
    String isz, String varos, String utca, String hazszam) {
    Element raktar = doc.createElement("raktár");
    raktar.setAttribute("raktár_id", raktarId);

    Element arElement = createElement(doc, "ár", ar);
    Element raktarbaErkezesElement = createElement(doc, "raktárba_érkezés", raktarbaErkezes);

    Element cim = doc.createElement("cím");
    Element iszElement = createElement(doc, "Isz", isz);
    Element varosElement = createElement(doc, "Város", varos);
    Element utcaElement = createElement(doc, "Utca", utca);
    Element hazszamElement = createElement(doc, "házszám", hazszam);

    cim.appendChild(iszElement);
    cim.appendChild(varosElement);
    cim.appendChild(utcaElement);
    cim.appendChild(hazszamElement);

    raktar.appendChild(arElement);
    raktar.appendChild(raktarbaErkezesElement);
    raktar.appendChild(cim);

    rootElement.appendChild(raktar);
  }

  private static void addGyarihibas(Document doc, Element rootElement, String gyarihibasId, String raktar, String ar,
    String marka, String nev) {
    Element gyarihibas = doc.createElement("Gyárihibás");
    gyarihibas.setAttribute("gyárihibás_id", gyarihibasId);
    gyarihibas.setAttribute("raktár", raktar);

    Element arElement = createElement(doc, "ár", ar);
    Element markaElement = createElement(doc, "márka", marka);
    Element nevElement = createElement(doc, "név", nev);

    gyarihibas.appendChild(arElement);
    gyarihibas.appendChild(markaElement);
    gyarihibas.appendChild(nevElement);

    rootElement.appendChild(gyarihibas);
  }

  private static void addBR(Document doc, Element rootElement, String darabszam, String raktar, String beszallito) {
    Element br = doc.createElement("B_R");
    br.setAttribute("darabszám", darabszam);
    br.setAttribute("raktár", raktar);
    br.setAttribute("beszállító", beszallito);

    rootElement.appendChild(br);
  }

  private static void addBeszallito(Document doc, Element rootElement, String beszallitoId, String raktar,
    String varhatoErkezes, String ar, String csomagolasTipusa) {
    Element beszallito = doc.createElement("beszállító");
    beszallito.setAttribute("beszállító_id", beszallitoId);
    beszallito.setAttribute("raktár", raktar);

    Element varhatoErkezesElement = createElement(doc, "várható_érkezés", varhatoErkezes);
    Element arElement = createElement(doc, "ár", ar);
    Element csomagolasTipusaElement = createElement(doc, "csomagolás_típusa", csomagolasTipusa);

    beszallito.appendChild(varhatoErkezesElement);
    beszallito.appendChild(arElement);
    beszallito.appendChild(csomagolasTipusaElement);

    rootElement.appendChild(beszallito);
  }

  private static void addFutar(Document doc, Element rootElement, String rendelesSzam, String raktar, String helyzet,
    String varhatoErkezes, String ar) {
    Element futar = doc.createElement("futár");
    futar.setAttribute("Rendelés_szám", rendelesSzam);
    futar.setAttribute("raktár", raktar);

    Element helyzetElement = createElement(doc, "Helyzet", helyzet);
    Element varhatoErkezesElement = createElement(doc, "várható_érkezés", varhatoErkezes);
    Element arElement = createElement(doc, "ár", ar);

    futar.appendChild(helyzetElement);
    futar.appendChild(varhatoErkezesElement);
    futar.appendChild(arElement);

    rootElement.appendChild(futar);
  }

  private static void addUgyfel(Document doc, Element rootElement, String csomagszam, String futar, String vasarlasIdopontja,
    String vasaroltTermek, String telefonszam, String isz, String varos, String utca, String hazszam) {
    Element ugyfel = doc.createElement("ügyfél");
    ugyfel.setAttribute("Csomagszám", csomagszam);
    ugyfel.setAttribute("futár", futar);

    Element vasarlasIdopontjaElement = createElement(doc, "VásárlásIdőpontja", vasarlasIdopontja);
    Element vasaroltTermekElement = createElement(doc, "VásároltTermék", vasaroltTermek);
    Element telefonszamElement = createElement(doc, "Telefonszám", telefonszam);

    Element cim = doc.createElement("cím");
    Element iszElement = createElement(doc, "Isz", isz);
    Element varosElement = createElement(doc, "Város", varos);
    Element utcaElement = createElement(doc, "Utca", utca);
    Element hazszamElement = createElement(doc, "házszám", hazszam);

    cim.appendChild(iszElement);
    cim.appendChild(varosElement);
    cim.appendChild(utcaElement);
    cim.appendChild(hazszamElement);

    ugyfel.appendChild(vasarlasIdopontjaElement);
    ugyfel.appendChild(vasaroltTermekElement);
    ugyfel.appendChild(telefonszamElement);
    ugyfel.appendChild(cim);

    rootElement.appendChild(ugyfel);
  }

  private static Element createElement(Document doc, String name, String value) {
    Element element = doc.createElement(name);
    element.appendChild(doc.createTextNode(value));
    return element;
  }

  private static void printDocument(Document doc) {
    try {
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

      System.out.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
      writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

      System.out.print("<" + rootName + " " + rootAttributes.toString() + ">\n");
      writer.print("<" + rootName + " " + rootAttributes.toString() + ">\n");

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

  private static void printNodeList(NodeList nodeList, PrintWriter writer) {
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      printNode(node, 0, writer);
      System.out.println(""); // Üres sor hozzáadása az elemek között
      writer.println(""); // Üres sor hozzáadása a fájlban az elemek között
    }
  }

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