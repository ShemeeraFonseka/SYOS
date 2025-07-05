package controller;

import com.google.gson.Gson;
import controller.BillServlet;
import dao.BillDAOImpl;
import model.Bill;
import model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import service.OrderProcessingService;
import service.StockMovementService;
import service.StockMovementServiceImpl;

import javax.servlet.http.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BillServletTest {

    private BillServlet billServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter responseWriter;
    private Gson gson;

    @BeforeEach
    public void setup() throws Exception {
        billServlet = new BillServlet();
        billServlet.init();
        gson = new Gson();
    }

    @Test
    public void testConcurrentDoPost() throws InterruptedException {
        int threadCount = 10; // number of concurrent requests
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1); // ensures threads start together

        List<Future<String>> results = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            results.add(executor.submit(() -> {
                try {
                    // Create mock request and response
                    HttpServletRequest request = mock(HttpServletRequest.class);
                    HttpServletResponse response = mock(HttpServletResponse.class);

                    StringWriter responseWriter = new StringWriter();
                    when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

                    // Prepare Bill JSON
                    Bill bill = new Bill();
                    bill.setTransactionType("ONSITE");
                    bill.setTotalAmount(200f);
                    bill.setDiscount(20f);
                    bill.setCashTendered(200f);
                    bill.setCashChange(0f);
                    bill.setAmountAfterDiscount(180f);
                    bill.setCustomerID(1);
                    bill.setOrders(new ArrayList<>()); // empty order list

                    String billJson = gson.toJson(bill);
                    BufferedReader reader = new BufferedReader(new StringReader(billJson));
                    when(request.getReader()).thenReturn(reader);
                    when(request.getMethod()).thenReturn("POST");

                    latch.await(); // wait for all threads to be ready
                    billServlet.doPost(request, response);
                    return responseWriter.toString();
                } catch (Exception e) {
                    return "Exception: " + e.getMessage();
                }
            }));
        }

        latch.countDown(); // let all threads proceed
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        // Validate each result
        for (Future<String> future : results) {
            try {
                String result = future.get();
                assert result.contains("\"status\":\"success\"") : "Failed response: " + result;
                System.out.println("Thread response: " + result);
            } catch (Exception e) {
                System.err.println("Error in thread: " + e.getMessage());
            }
        }
    }

    @Test
    public void testDoGet() throws Exception {
        // Mock request and response
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Mock PrintWriter
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Create servlet and call doGet
        BillServlet servlet = new BillServlet();
        servlet.doGet(request, response);

        writer.flush(); // Ensure output is written

        // Assert response content
        String output = stringWriter.toString();
        System.out.println(output); // for debugging or verification
        // Add assertions like:
        // assertTrue(output.contains("expected content"));
    }
}
