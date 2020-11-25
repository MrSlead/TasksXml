package transform;

import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;
import db.H2Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import xmlEntities.Article;
import xmlEntities.ArticlesXmlFormat;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ConverterToXml {
    private static Logger logger = LoggerFactory.getLogger(H2Repo.class);


    public void convertToXmlJAXB(Object dataForXmlFile) {
        if(dataForXmlFile instanceof ResultSet) {
            convertUsingResultSetJAXB((ResultSet) dataForXmlFile);
            transformFiles();
        }
    }

    public void convertToXmlDOM(Object dataForXmlFile) {
        if(dataForXmlFile instanceof ResultSet) {
            convertUsingResultSetDOM((ResultSet) dataForXmlFile);
            transformFiles();
        }
    }


    private void convertUsingResultSetJAXB(ResultSet resultSet) {
        List<Article> articleList = new ArrayList<>();

        try {
            while(resultSet.next()) {
                articleList.add(new Article(
                        resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5))
                );
            }

            ArticlesXmlFormat articlesXmlFormat = new ArticlesXmlFormat(articleList);

            StringWriter writer = new StringWriter();

            JAXBContext context = JAXBContext.newInstance(ArticlesXmlFormat.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(articlesXmlFormat, writer);

            // result in the folder '/target'
            //Files.write(Paths.get(getClass().getResource("/resultFirstTask.xml").toURI()), writer.toString().getBytes());

            // result in the folder '/resources'
            Files.write(Paths.get("./src/main/resources/resultFirstTask.xml"), writer.toString().getBytes());
            logger.info("The xml file 'resultFirstTask.xml' is filled");
        } catch (Exception e) {
            e.printStackTrace();
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


            logger.info("Сonversion completed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transformFiles() {
        // Second Task
        transformXmlFile("./src/main/resources/transform.xsl",
                "./src/main/resources/resultFirstTask.xml",
                "./src/main/resources/resultSecondTask.xml", 4);


        // Third Task
        transformXmlFile("./src/main/resources/transformToCSV.xsl",
                "./src/main/resources/resultSecondTask.xml",
                "./src/main/resources/resultThirdTask.txt", 0);
    }

    private void transformXmlFile(String xslFile, String inputFile, String outputFile, int indent) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslFile));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(indent));

            transformer.transform(
                    new StreamSource(inputFile),
                    new StreamResult(new File(outputFile)));

            String[] filePath = outputFile.split("/");
            String nameFile = filePath[filePath.length - 1];
            logger.info(String.format("Сonversion to '%s' completed", nameFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
