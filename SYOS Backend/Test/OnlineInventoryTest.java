import model.OnlineInventory;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class OnlineInventoryTest {

    @Test
    void testConstructorAndGetters() {
        String id = "OI123";
        Date date = new Date();
        int qtySold = 10;
        String itemCode = "I001";
        String batchID = "B001";
        String orderID = "O123";

        OnlineInventory oi = new OnlineInventory(id, date, qtySold, itemCode, batchID, orderID);

        assertEquals(id, oi.getOnlineInventoryID());
        assertEquals(date, oi.getDate());
        assertEquals(qtySold, oi.getQuantitySold());
        assertEquals(itemCode, oi.getItemCode());
        assertEquals(batchID, oi.getBatchID());
        assertEquals(orderID, oi.getOrderID());
    }

    @Test
    void testSetOnlineInventoryID() {
        OnlineInventory oi = new OnlineInventory("oldID", new Date(), 5, "I001", "B001", "O123");
        oi.setOnlineInventoryID("newID");
        assertEquals("newID", oi.getOnlineInventoryID());
    }

    @Test
    void testSetDate() {
        OnlineInventory oi = new OnlineInventory("OI001", new Date(), 5, "I001", "B001", "O123");
        Date newDate = new Date(System.currentTimeMillis() + 100000);
        oi.setDate(newDate);
        assertEquals(newDate, oi.getDate());
    }

    @Test
    void testSetQuantitySold() {
        OnlineInventory oi = new OnlineInventory("OI001", new Date(), 5, "I001", "B001", "O123");
        oi.setQuantitySold(20);
        assertEquals(20, oi.getQuantitySold());
    }

    @Test
    void testSetItemCode() {
        OnlineInventory oi = new OnlineInventory("OI001", new Date(), 5, "I001", "B001", "O123");
        oi.setItemCode("I002");
        assertEquals("I002", oi.getItemCode());
    }

    @Test
    void testSetBatchID() {
        OnlineInventory oi = new OnlineInventory("OI001", new Date(), 5, "I001", "B001", "O123");
        oi.setBatchID("B002");
        assertEquals("B002", oi.getBatchID());
    }

    @Test
    void testSetOrderID() {
        OnlineInventory oi = new OnlineInventory("OI001", new Date(), 5, "I001", "B001", "O123");
        oi.setOrderID("O999");
        assertEquals("O999", oi.getOrderID());
    }
}
