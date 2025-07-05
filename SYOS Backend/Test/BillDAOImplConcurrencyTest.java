import dao.BillDAOImpl;
import model.Bill;
import model.Order;
import org.junit.jupiter.api.Test;
import service.OrderProcessingService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BillDAOImplConcurrencyTest {
    @Test
    public void testConcurrentSaveBill() throws InterruptedException, ExecutionException {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        BillDAOImpl billDAO = new BillDAOImpl(new MockOrderProcessingService());

        List<Future<Integer>> results = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            results.add(executor.submit(() -> {
                Bill bill = new Bill();
                String serial = billDAO.generateBillSerialNumber();
                bill.setBillSerialNumber(serial);
                bill.setTransactionType("ONSITE");
                bill.setTotalAmount(100.0f);
                bill.setDiscount(10.0f);
                bill.setCashTendered(100.0f);
                bill.setCashChange(0.0f);
                bill.setAmountAfterDiscount(90.0f);
                int billID = billDAO.saveBill(bill, 1);

                // Output for clarity
                System.out.println("Thread " + Thread.currentThread().getName()
                        + " saved Bill ID: " + billID);

                return billID;
            }));
        }

        for (Future<Integer> result : results) {
            int billID = result.get();
            assertTrue(billID > 0, "Bill was not saved correctly.");
        }

        executor.shutdown();
    }

    static class MockOrderProcessingService extends OrderProcessingService {
        public MockOrderProcessingService() {
            super(null);
        }

        @Override
        public void processOrders(List<Order> orders, String billSerialNumber, String transactionType) {

        }
    }
}
