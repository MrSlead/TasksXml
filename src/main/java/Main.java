import db.H2Factory;
import db.H2Service;
import db.H2ServiceImpl;
import converter.ConverterToXml;
import converter.ConverterToXmlJAXB;
import transform.TransformXmlFile;

import java.sql.Connection;

public class Main {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        Connection connection = H2Factory.getConnection(JDBC_DRIVER, URL, USER, PASSWORD);

        // Preparation
        H2Service h2ServiceImpl = new H2ServiceImpl(connection);
        h2ServiceImpl.createTable();
        h2ServiceImpl.insertValuesInTable(10);
        h2ServiceImpl.showDatabase(false);

        // First Task
        ConverterToXml converterToXml = new ConverterToXmlJAXB();
        converterToXml.convert(h2ServiceImpl.showDatabase(true));

        TransformXmlFile transformXmlFile = new TransformXmlFile();

        // Second Task
        transformXmlFile.transformXmlFile("./src/main/resources/transform.xsl",
                "./src/main/resources/resultFirstTask.xml",
                "./src/main/resources/resultSecondTask.xml", 4);


        // Third Task
        transformXmlFile.transformXmlFile("./src/main/resources/transformToCSV.xsl",
                "./src/main/resources/resultSecondTask.xml",
                "./src/main/resources/resultThirdTask.txt", 0);

        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
