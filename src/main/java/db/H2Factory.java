package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class H2Factory {
    private static Connection connection;

    public static Connection getConnection(String jdbcDriver, String url, String user, String password) {
        if(connection == null) {
            try {
                Class.forName(jdbcDriver);
                connection = DriverManager.getConnection(url, user, password);

                return connection;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
