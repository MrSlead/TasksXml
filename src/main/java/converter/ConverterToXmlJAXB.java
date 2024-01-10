package converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xmlEntities.Article;
import xmlEntities.ArticlesXmlFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ConverterToXmlJAXB implements ConverterToXml {
    private static final Logger LOG = LoggerFactory.getLogger(ConverterToXmlJAXB.class);

    @Override
    public void convert(Object dataForConvert) {
        if(dataForConvert instanceof ResultSet) {
            convertUsingResultSetJAXB((ResultSet) dataForConvert);
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
            LOG.info("The xml file 'resultFirstTask.xml' is filled");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
