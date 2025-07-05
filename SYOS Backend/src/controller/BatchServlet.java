package controller;

import dao.BatchDAOImpl;
import dao.ItemDAO;
import dao.ItemDAOImpl;
import db.DBConnection;
import model.Batch;
import service.ItemValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BatchServlet extends HttpServlet {

    private BatchDAOImpl batchDAO;

    @Override
    public void init() throws ServletException {
        ItemDAO itemDAO = new ItemDAOImpl(); // Ensure constructor requires no args
        ItemValidator itemValidator = new ItemValidator(itemDAO);
        DBConnection dbConnection = DBConnection.getInstance();
        batchDAO = new BatchDAOImpl(dbConnection, itemValidator);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");

        String itemCode = request.getParameter("itemCode");
        String dateOfPurchaseStr = request.getParameter("dateOfPurchase");
        String quantityReceivedStr = request.getParameter("quantityReceived");
        String expiryDateStr = request.getParameter("expiryDate");
        String stockQuantityStr = request.getParameter("stockQuantity");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date dateOfPurchase = formatter.parse(dateOfPurchaseStr);
            int quantityReceived = Integer.parseInt(quantityReceivedStr);
            Date expiryDate = formatter.parse(expiryDateStr);
            int stockQuantity = Integer.parseInt(stockQuantityStr);

            Batch batch = new Batch(dateOfPurchase, quantityReceived, expiryDate, itemCode, stockQuantity);

            batchDAO.insertBatch(batch, itemCode);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"Batch inserted successfully\"}");

        } catch (ParseException | IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid input: " + e.getMessage() + "\"}");

        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Batch insertion failed: " + e.getMessage() + "\"}");
        }
    }
}
