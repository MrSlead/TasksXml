import db.H2Factory;
import db.H2Repo;
import transform.ConverterToXml;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        Connection connection = H2Factory.getConnection(JDBC_DRIVER, URL, USER, PASSWORD);

        H2Repo h2Repo = new H2Repo(connection);
        h2Repo.createTable();
        h2Repo.insertValuesInTable(1100);
        h2Repo.showDatabase(false);

        ConverterToXml converterToXml = new ConverterToXml();
        //converterToXml.convertToXmlDOM(h2Repo.showDatabase(true));
        converterToXml.convertToXmlJAXB(h2Repo.showDatabase(true));

        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
