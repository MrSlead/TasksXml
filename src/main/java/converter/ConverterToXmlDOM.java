package converter;

import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.ResultSet;

public class ConverterToXmlDOM implements ConverterToXml {
    private static final Logger LOG = LoggerFactory.getLogger(ConverterToXmlJAXB.class);

    @Override
    public void convert(Object dataForConvert) {
        if(dataForConvert instanceof ResultSet) {
            convertUsingResultSetDOM((ResultSet) dataForConvert);
        }
    }

    private void convertUsingResultSetDOM(ResultSet resultSet) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            //root elements
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("articles");
            doc.appendChild(rootElement);

            while(resultSet.next()) {
                Element element = doc.createElement("article");
                rootElement.appendChild(element);
                element.setAttribute("id_art", resultSet.getString(1));
                element.setAttribute("name", resultSet.getString(2));
                element.setAttribute("code", resultSet.getString(3));
                element.setAttribute("username", resultSet.getString(4));
                element.setAttribute("guid", resultSet.getString(5));
            }

            //write the content into xml file
            TransformerFactory transformerFactory =  TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "4");

            DOMSource source = new DOMSource(doc);

            StreamResult result =  new StreamResult(
                    new File("./src/main/resources/resultFirstTask.xml"));
            transformer.transform(source, result);

            LOG.info("The xml file 'resultFirstTask.xml' is filled");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
