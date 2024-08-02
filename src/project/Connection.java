package project;

import java.sql.*;

public class Connection {
    private java.sql.Connection connection;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "Tt2004";
    public void connect() throws SQLException {
        this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public PreparedStatement prepareStatement(String sql) throws Exception {
        return connection.prepareStatement(sql);
    }
    public Statement createStatement() throws Exception {
        return connection.createStatement();
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

}
