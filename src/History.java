import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class History {

  private String fileName;
  private File file;
  private DocumentBuilderFactory documentBuilderFactory;
  private DocumentBuilder documentBuilder;
  private Document doc;
  private Node  rootElement;

  History() {

    fileName = "history.dat";

    file = new File(fileName);

    documentBuilderFactory = DocumentBuilderFactory.newInstance();
    try {
      documentBuilder = documentBuilderFactory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }

    if (!file.exists()) {
      doc = documentBuilder.newDocument();

      Element rootElement = doc.createElement("history");
      doc.appendChild(rootElement);

      try {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        // send DOM to file
        transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(fileName)));

      } catch (TransformerException te) {
        System.out.println(te.getMessage());
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }

    try {

      doc = documentBuilder.parse(file);
      rootElement = doc.getFirstChild();
      System.out.print(rootElement.getLocalName());

    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void addItem(String equation, String result) {

    Element item = doc.createElement("item");

    Element e;
    e = doc.createElement("equation");
    e.setTextContent(equation);
    item.appendChild(e);

    e = doc.createElement("result");
    e.setTextContent(result);
    item.appendChild(e);

    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    Date date = new Date(System.currentTimeMillis());
    e = doc.createElement("date");
    e.setTextContent(formatter.format(date));
    item.appendChild(e);

    rootElement.appendChild(item);

    try {

      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(fileName)));

    } catch (TransformerException te) {
      System.out.println(te.getMessage());
    } catch (IOException IOe) {
      System.out.println(IOe.getMessage());
    }
  }

  public int getLength() {
    try {
      doc = documentBuilder.parse(file);
      doc.getDocumentElement().normalize();

      NodeList list = doc.getElementsByTagName("item");
      return list.getLength();

    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return -1;
  }


  public ArrayList<String> getItem(int i) {
    ArrayList<String> arrayListItem = new ArrayList<>();

    try {
      doc = documentBuilder.parse(file);
      doc.getDocumentElement().normalize();

      NodeList list = doc.getElementsByTagName("item");
      Node item = list.item(i);

      Element e = (Element) item;
      arrayListItem.add(e.getElementsByTagName("equation").item(0).getTextContent());
      arrayListItem.add(e.getElementsByTagName("result").item(0).getTextContent());
      arrayListItem.add(e.getElementsByTagName("date").item(0).getTextContent());

      return arrayListItem;

    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return arrayListItem;

  }


}
