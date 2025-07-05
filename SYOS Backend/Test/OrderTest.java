import model.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testConstructorAndGetters() {
        String orderID = "O100";
        String itemCode = "I200";
        int quantity = 3;
        float totalPrice = 45.5f;
        String billSerialNumber = "B123";

        Order order = new Order(orderID, itemCode, quantity, totalPrice, billSerialNumber);

        assertEquals(orderID, order.getOrderID());
        assertEquals(itemCode, order.getItemCode());
        assertEquals(quantity, order.getQuantity());
        assertEquals(totalPrice, order.getTotalPrice());
        assertEquals(billSerialNumber, order.getBillSerialNumber());
    }

    @Test
    void testSetters() {
        Order order = new Order("O001", "I001", 1, 10.0f, "B001");

        order.setOrderID("O002");
        order.setItemCode("I002");
        order.setQuantity(5);
        order.setTotalPrice(50.0f);
        order.setBillSerialNumber("B002");

        assertEquals("O002", order.getOrderID());
        assertEquals("I002", order.getItemCode());
        assertEquals(5, order.getQuantity());
        assertEquals(50.0f, order.getTotalPrice());
        assertEquals("B002", order.getBillSerialNumber());
    }
}
