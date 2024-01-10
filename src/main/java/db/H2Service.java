package db;

import java.sql.ResultSet;

public interface H2Service {
    void dropTable();
    void createTable();
    void insertValuesInTable(int numberOfValuesToInsert);
    ResultSet showDatabase(boolean getOnlyResultSet);
}
