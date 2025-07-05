

import dao.ItemDAO;
import dao.ItemDAOImpl;
import model.Item;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemDAOImplTest {

    private ItemDAO itemDAO;

    @BeforeEach
    void setUp() {
        itemDAO = new ItemDAOImpl();
    }

    @Test
    void testGetItemPrice_validItemCode() {
        // Replace "ITEM001" with an actual itemCode existing in your test DB
        Float price = itemDAO.getItemPrice("fru001");
        assertNotNull(price, "Price should not be null for valid item code");
        assertTrue(price > 0, "Price should be greater than zero");
    }

    @Test
    void testGetItemPrice_invalidItemCode() {
        Float price = itemDAO.getItemPrice("INVALID_CODE");
        assertNull(price, "Price should be null for invalid item code");
    }

    @Test
    void testGetAllItems() {
        List<Item> items = itemDAO.getAllItems();
        assertNotNull(items, "Item list should not be null");
        assertFalse(items.isEmpty(), "Item list should not be empty");
    }

    @Test
    void testIsItemCodeValid_validCode() {
        // Replace "ITEM001" with an actual itemCode existing in your test DB
        assertTrue(itemDAO.isItemCodeValid("fru001"), "Item code should be valid");
    }

    @Test
    void testIsItemCodeValid_invalidCode() {
        assertFalse(itemDAO.isItemCodeValid("NON_EXISTENT_CODE"), "Item code should be invalid");
    }
}
