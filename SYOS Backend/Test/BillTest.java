

import model.Bill;
import model.Order;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BillTest {

    @Test
    void testConstructor() {
        String serial = "BILL-001";
        Date billDate = new Date();
        String transactionType = "Cash";
        float totalAmount = 1000f;
        float discount = 50f;
        float cashTendered = 1000f;
        float cashChange = 50f;
        Integer customerID = 101;
        List<Order> orders = Arrays.asList(
                new Order("I001", "Item A", 2, 10.0f, "Confirmed"),
                new Order("I002", "Item B", 1, 20.0f, "Confirmed")
        );
        float amountAfterDiscount = 950f;

        Bill bill = new Bill(serial, billDate, transactionType, totalAmount, discount,
                cashTendered, cashChange, customerID, orders, amountAfterDiscount);

        assertEquals(serial, bill.getBillSerialNumber());
        assertEquals(billDate, bill.getBillDate());
        assertEquals(transactionType, bill.getTransactionType());
        assertEquals(totalAmount, bill.getTotalAmount());
        assertEquals(discount, bill.getDiscount());
        assertEquals(cashTendered, bill.getCashTendered());
        assertEquals(cashChange, bill.getCashChange());
        assertEquals(customerID, bill.getCustomerID());
        assertEquals(orders, bill.getOrders());
        assertEquals(amountAfterDiscount, bill.getAmountAfterDiscount());
    }

    @Test
    void testBillID() {
        Bill bill = new Bill();
        bill.setBillID(1);
        assertEquals(1, bill.getBillID());
    }

    @Test
    void testBillSerialNumber() {
        Bill bill = new Bill();
        bill.setBillSerialNumber("B123");
        assertEquals("B123", bill.getBillSerialNumber());
    }

    @Test
    void testBillDate() {
        Bill bill = new Bill();
        Date date = new Date();
        bill.setBillDate(date);
        assertEquals(date, bill.getBillDate());
    }

    @Test
    void testTransactionType() {
        Bill bill = new Bill();
        bill.setTransactionType("Credit");
        assertEquals("Credit", bill.getTransactionType());
    }

    @Test
    void testTotalAmount() {
        Bill bill = new Bill();
        bill.setTotalAmount(1500f);
        assertEquals(1500f, bill.getTotalAmount());
    }

    @Test
    void testDiscount() {
        Bill bill = new Bill();
        bill.setDiscount(100f);
        assertEquals(100f, bill.getDiscount());
    }

    @Test
    void testCashTendered() {
        Bill bill = new Bill();
        bill.setCashTendered(2000f);
        assertEquals(2000f, bill.getCashTendered());
    }

    @Test
    void testCashChange() {
        Bill bill = new Bill();
        bill.setCashChange(500f);
        assertEquals(500f, bill.getCashChange());
    }

    @Test
    void testCustomerID() {
        Bill bill = new Bill();
        bill.setCustomerID(303);
        assertEquals(303, bill.getCustomerID());
    }

    @Test
    void testOrders() {
        Bill bill = new Bill();
        List<Order> orderList = Arrays.asList(
                new Order("I001", "Item X", 2, 100.0f, "Confirmed") // ✅ correct constructor
        );
        bill.setOrders(orderList);
        assertEquals(orderList, bill.getOrders());
    }


    @Test
    void testAmountAfterDiscount() {
        Bill bill = new Bill();
        bill.setAmountAfterDiscount(1400f);
        assertEquals(1400f, bill.getAmountAfterDiscount());
    }
}
