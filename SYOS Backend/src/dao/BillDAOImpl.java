package dao;

import db.DBConnection;
import model.Bill;
import service.OrderProcessingService;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class BillDAOImpl implements BillDAO{
    private final OrderProcessingService orderProcessingService;

    public BillDAOImpl(OrderProcessingService orderProcessingService) {
        this.orderProcessingService = orderProcessingService;
    }

    @Override
    public String generateBillSerialNumber() {
        return UUID.randomUUID().toString();
    }

    @Override
    public int saveBill(Bill bill, Integer customerID) {
        try (Connection conn = DBConnection.getInstance().getConnection()) {
            // Insert the bill into the database and return the generated ID
            String sql = "INSERT INTO bill (billSerialNumber, billDate, transactionType, totalAmount, discount, " +
                    "cashTendered, cashChange, customerID, amountAfterDiscount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            int generatedId = -1;

            try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                Timestamp currentTimestamp = Timestamp.valueOf(LocalDateTime.now());

                stmt.setString(1, bill.getBillSerialNumber());
                stmt.setTimestamp(2, currentTimestamp);
                stmt.setString(3, bill.getTransactionType());
                stmt.setFloat(4, bill.getTotalAmount());
                stmt.setFloat(5, bill.getDiscount());
                stmt.setFloat(6, bill.getCashTendered());
                stmt.setFloat(7, bill.getCashChange());
                stmt.setObject(8, customerID, java.sql.Types.INTEGER);
                stmt.setFloat(9, bill.getAmountAfterDiscount());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating bill failed, no rows affected.");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                        bill.setBillID(generatedId); // Set the generated ID to the bill object
                    } else {
                        throw new SQLException("Creating bill failed, no ID obtained.");
                    }
                }
            }

            // Process orders after the bill is successfully created
            orderProcessingService.processOrders(bill.getOrders(), bill.getBillSerialNumber(), bill.getTransactionType());

            return generatedId;

        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Or throw an exception
        }
    }
}
