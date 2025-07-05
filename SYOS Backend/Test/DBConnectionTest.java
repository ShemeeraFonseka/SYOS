

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import db.DBConnection;
import org.junit.jupiter.api.Test;

public class DBConnectionTest {

    @Test
    public void testSingletonInstance() {
        DBConnection instance1 = DBConnection.getInstance();
        DBConnection instance2 = DBConnection.getInstance();

        assertNotNull(instance1, "Instance 1 should not be null");
        assertNotNull(instance2, "Instance 2 should not be null");
        assertSame(instance1, instance2, "Both instances should be the same (singleton)");
    }

    @Test
    public void testGetConnection() {
        DBConnection dbConnection = DBConnection.getInstance();
        try (Connection connection = dbConnection.getConnection()) {
            assertNotNull(connection, "Connection should not be null");
            assertFalse(connection.isClosed(), "Connection should be open");
        } catch (SQLException e) {
            fail("Failed to get a database connection: " + e.getMessage());
        }
    }
}
