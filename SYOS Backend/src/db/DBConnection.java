package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private static DBConnection instance;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/syosstore?useGeneratedKeys=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private DBConnection() {
        // Private constructor to prevent instantiation
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}

