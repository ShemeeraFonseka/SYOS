

import dao.LoginDAO;
import dao.LoginDAOImpl;
import db.DBConnection;
import model.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class LoginDAOImplTest {

    private LoginDAO loginDAO;

    @BeforeEach
    void setUp() {

        loginDAO = new LoginDAOImpl(DBConnection.getInstance());
    }

    @Test
    void testLoginCustomer_validCredentials() {

        User user = loginDAO.loginCustomer("admin", "admin");

        assertNotNull(user, "User should not be null for valid credentials");
        assertEquals("admin", user.getUsername());

    }

    @Test
    void testLoginCustomer_invalidCredentials() {
        User user = loginDAO.loginCustomer("invalidUser", "wrongPassword");
        assertNull(user, "User should be null for invalid credentials");
    }
}
