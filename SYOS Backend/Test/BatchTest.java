

import model.Batch;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BatchTest {

    @Test
    void testConstructor() {
        Date dateOfPurchase = new Date();
        int quantityReceived = 100;
        Date expiryDate = new Date(System.currentTimeMillis() + 86400000);
        String itemCode = "ITEM001";
        int stockQuantity = 90;

        Batch batch = new Batch(dateOfPurchase, quantityReceived, expiryDate, itemCode, stockQuantity);

        assertEquals(dateOfPurchase, batch.getDateOfPurchase());
        assertEquals(quantityReceived, batch.getQuantityRecieved());
        assertEquals(expiryDate, batch.getExpiryDate());
        assertEquals(itemCode, batch.getItemCOde());
        assertEquals(stockQuantity, batch.getStockQuantity());
    }

    @Test
    void testDateOfPurchaseGetterSetter() {
        Batch batch = new Batch(new Date(), 0, new Date(), "CODE", 0);
        Date newDate = new Date(System.currentTimeMillis() - 86400000);
        batch.setDateOfPurchase(newDate);
        assertEquals(newDate, batch.getDateOfPurchase());
    }

    @Test
    void testQuantityReceivedGetterSetter() {
        Batch batch = new Batch(new Date(), 0, new Date(), "CODE", 0);
        batch.setQuantityRecieved(120);
        assertEquals(120, batch.getQuantityRecieved());
    }

    @Test
    void testExpiryDateGetterSetter() {
        Batch batch = new Batch(new Date(), 0, new Date(), "CODE", 0);
        Date expiry = new Date(System.currentTimeMillis() + 604800000);
        batch.setExpiryDate(expiry);
        assertEquals(expiry, batch.getExpiryDate());
    }

    @Test
    void testItemCodeGetterSetter() {
        Batch batch = new Batch(new Date(), 0, new Date(), "OLD", 0);
        batch.setItemCOde("NEWCODE123");
        assertEquals("NEWCODE123", batch.getItemCOde());
    }

    @Test
    void testStockQuantityGetterSetter() {
        Batch batch = new Batch(new Date(), 0, new Date(), "CODE", 0);
        batch.setStockQuantity(200);
        assertEquals(200, batch.getStockQuantity());
    }
}
