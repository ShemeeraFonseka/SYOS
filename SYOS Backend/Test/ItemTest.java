import model.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void testConstructorAndGetters() {
        Item item = new Item("I001", "Apple", 2.5f, "/images/apple.jpg", "Fruit");

        assertEquals("I001", item.getItemCode());
        assertEquals("Apple", item.getItemName());
        assertEquals(2.5f, item.getPricePerUnit());
        assertEquals("/images/apple.jpg", item.getPath());
        assertEquals("Fruit", item.getCategory());
    }

    @Test
    void testSetItemCode() {
        Item item = new Item("I001", "Apple", 2.5f, "/images/apple.jpg", "Fruit");
        item.setItemCode("I002");
        assertEquals("I002", item.getItemCode());
    }

    @Test
    void testSetItemName() {
        Item item = new Item("I001", "Apple", 2.5f, "/images/apple.jpg", "Fruit");
        item.setItemName("Banana");
        assertEquals("Banana", item.getItemName());
    }

    @Test
    void testSetPricePerUnit() {
        Item item = new Item("I001", "Apple", 2.5f, "/images/apple.jpg", "Fruit");
        item.setPricePerUnit(3.0f);
        assertEquals(3.0f, item.getPricePerUnit());
    }

    @Test
    void testSetPath() {
        Item item = new Item("I001", "Apple", 2.5f, "/images/apple.jpg", "Fruit");
        item.setPath("/images/banana.jpg");
        assertEquals("/images/banana.jpg", item.getPath());
    }

    @Test
    void testSetCategory() {
        Item item = new Item("I001", "Apple", 2.5f, "/images/apple.jpg", "Fruit");
        item.setCategory("Snack");
        assertEquals("Snack", item.getCategory());
    }
}
