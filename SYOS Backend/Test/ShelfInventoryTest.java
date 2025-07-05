import model.ShelfInventory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShelfInventoryTest {

    @Test
    void testConstructorAndGetters() {
        String shelfInventoryID = "S001";
        int shelfQuantity = 25;
        String itemCode = "I100";

        ShelfInventory shelfInventory = new ShelfInventory(shelfInventoryID, shelfQuantity, itemCode);

        assertEquals(shelfInventoryID, shelfInventory.getShelfInventoryID());
        assertEquals(shelfQuantity, shelfInventory.getShelfQuantity());
        assertEquals(itemCode, shelfInventory.getItemCode());
    }

    @Test
    void testSetters() {
        ShelfInventory shelfInventory = new ShelfInventory("S001", 10, "I001");

        shelfInventory.setShelfInventoryID("S002");
        shelfInventory.setShelfQuantity(50);
        shelfInventory.setItemCode("I002");

        assertEquals("S002", shelfInventory.getShelfInventoryID());
        assertEquals(50, shelfInventory.getShelfQuantity());
        assertEquals("I002", shelfInventory.getItemCode());
    }
}
