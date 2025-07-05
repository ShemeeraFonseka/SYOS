import model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testConstructorAndGetters() {
        int userID = 1;
        String name = "John Doe";
        String email = "john@example.com";
        String phone = "1234567890";
        String address = "123 Main St";
        String username = "johndoe";
        String password = "password123";

        User user = new User(userID, name, email, phone, address, username, password);

        assertEquals(userID, user.getUserID());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(phone, user.getPhone());
        assertEquals(address, user.getAddress());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
    }

    @Test
    void testSetters() {
        User user = new User(1, "Initial", "init@example.com", "0000000000", "Init Address", "inituser", "initpass");

        user.setUserID(2);
        user.setName("Jane Smith");
        user.setEmail("jane@example.com");
        user.setPhone("0987654321");
        user.setAddress("456 Elm St");
        user.setUsername("janesmith");
        user.setPassword("newpassword");

        assertEquals(2, user.getUserID());
        assertEquals("Jane Smith", user.getName());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals("0987654321", user.getPhone());
        assertEquals("456 Elm St", user.getAddress());
        assertEquals("janesmith", user.getUsername());
        assertEquals("newpassword", user.getPassword());
    }
}
