package learn.xml;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class XmlTask {
    private File xmlFile;
    private Document document;

    public File getXmlFile() {
        return xmlFile;
    }

    public void setXmlFile(File xmlFile) {
        this.xmlFile = xmlFile;
        parseXML();
    }



    private Node getFlatNode(String secondName, int firstName) {
        NodeList officiant = document.getDocumentElement().getElementsByTagName("officiant");
        for (int i = 0; i < officiant.getLength(); i++) {
            Node building = officiant.item(i);
            NamedNodeMap officiantAttributes = building.getAttributes();

            //officiant check
            if (officiantAttributes.getNamedItem("secondname").getNodeValue().equals(secondName) &&
                    officiantAttributes.getNamedItem("firstname").getNodeValue().equals(Integer.toString(firstName))) {

            }
        }
        return null;
    }

    public String earningsTotal(String officiantSecondName) {
        Node earingsTotal = document.getElementsByTagName("totalcost").item(0);
        String result = earingsTotal.getTextContent();
        saveXML();
        return result;
    }




    private void parseXML() {
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private void saveXML() {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "restaurant.dtd");
            transformer.transform(new DOMSource(document), new StreamResult(xmlFile));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
