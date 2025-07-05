package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.BillDAO;
import dao.BillDAOImpl;
import model.Bill;
import model.Order;
import service.OrderProcessingService;
import service.StockMovementService;
import service.StockMovementServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class BillServlet extends HttpServlet {
    private BillDAOImpl billDAO;

    @Override
    public void init() throws ServletException {
        StockMovementService stockMovementService = new StockMovementServiceImpl();
        OrderProcessingService orderProcessingService = new OrderProcessingService(stockMovementService);
        billDAO = new BillDAOImpl(orderProcessingService);
    }


    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set CORS headers
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        try {
            // Read JSON body
            StringBuilder jsonBuilder = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }
            }

            // Parse JSON to Bill object
            Type billType = new TypeToken<Bill>() {}.getType();
            Bill bill = gson.fromJson(jsonBuilder.toString(), billType);

            // Set current date/time for the bill
            bill.setBillDate(new Date());

            // Generate a unique serial number
            String billSerialNumber = billDAO.generateBillSerialNumber();
            bill.setBillSerialNumber(billSerialNumber);

            // Save the bill and get the generated ID
            int billId = billDAO.saveBill(bill, bill.getCustomerID());

            // Respond with success and include both billSerialNumber and billID
            PrintWriter out = response.getWriter();
            out.print(String.format("{\"status\":\"success\", \"billSerialNumber\":\"%s\", \"billId\":%d}",
                    billSerialNumber, billId));
            out.flush();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2>BillServlet is working!</h2>");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}