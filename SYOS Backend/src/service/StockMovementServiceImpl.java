package service;

import db.DBConnection;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class StockMovementServiceImpl implements StockMovementService{
    @Override
    public void moveStock(Order order, String transactionType) {
        int quantityNeeded = order.getQuantity();
        if (transactionType.equalsIgnoreCase("ONSITE")) {
            moveStockFromStoreToShelf(order.getItemCode(), quantityNeeded, order.getOrderID());
        } else if (transactionType.equalsIgnoreCase("ONLINE")) {
            moveStockFromStoreToOnline(order.getItemCode(), quantityNeeded, order.getOrderID());
        }
    }

    public void moveStockFromStoreToShelf(String itemCode, int quantityNeeded,String orderID) {
        Timestamp currentTimestamp = Timestamp.valueOf(LocalDateTime.now());
        String fetchStoreStockQuery = "SELECT batchID, stockQuantity, expiryDate " +
                "FROM batch " +
                "WHERE itemCode = ? AND stockQuantity > 0 " +
                "ORDER BY expiryDate ASC";

        String updateStoreStockQuery = "UPDATE batch SET stockQuantity = stockQuantity - ? WHERE batchID = ?";

        String insertShelfStockQuery = "INSERT INTO shelfinventory (shelfInventoryID, date, quantitySold, itemCode, batchID,orderID) " +
                "VALUES (?, ?, ?, ?,?,?)";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement fetchStmt = connection.prepareStatement(fetchStoreStockQuery);
             PreparedStatement updateBatchStmt = connection.prepareStatement(updateStoreStockQuery);
             PreparedStatement insertShelfStmt = connection.prepareStatement(insertShelfStockQuery)) {

            fetchStmt.setString(1, itemCode);
            ResultSet resultSet = fetchStmt.executeQuery();

            int remainingQuantity = quantityNeeded;

            while (resultSet.next() && remainingQuantity > 0) {
                String batchID = resultSet.getString("batchID");
                int availableQuantity = resultSet.getInt("stockQuantity");
                java.sql.Date expiryDate = resultSet.getDate("expiryDate");

                // Determine quantity to move
                int quantityToMove = Math.min(remainingQuantity, availableQuantity);

                // Reduce stock in the store
                updateBatchStmt.setInt(1, quantityToMove);
                updateBatchStmt.setString(2, batchID);
                updateBatchStmt.executeUpdate();

                // Add stock to the shelf
                insertShelfStmt.setString(1, UUID.randomUUID().toString());
                insertShelfStmt.setTimestamp(2,currentTimestamp);
                insertShelfStmt.setInt(3, quantityToMove);
                insertShelfStmt.setString(4, itemCode);
                insertShelfStmt.setString(5, batchID);
                insertShelfStmt.setString(6, orderID);
                insertShelfStmt.executeUpdate();

                remainingQuantity -= quantityToMove;
            }

            if (remainingQuantity > 0) {
                System.out.println("Not enough stock in the store to replenish the shelf.");
            }
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void moveStockFromStoreToOnline(String itemCode, int quantityNeeded,String orderID) {
        Timestamp currentTimestamp = Timestamp.valueOf(LocalDateTime.now());
        // Query to fetch available store stock ordered by expiry date (earliest expiry first)
        String fetchStoreStockQuery = "SELECT batchID, stockQuantity, expiryDate " +
                "FROM batch " +
                "WHERE itemCode = ? AND stockQuantity > 0 " +
                "ORDER BY expiryDate ASC";  // Prefer earlier expiry date

        // Query to update the stock quantity in the store after movement
        String updateStoreStockQuery = "UPDATE batch SET stockQuantity = stockQuantity - ? WHERE batchID = ?";

        // Query to insert into the online inventory
        String insertOnlineStockQuery = "INSERT INTO onlineinventory (onlineInventoryID,date, quantitySold, itemCode, batchID,orderID) " +
                "VALUES (?, ?, ?, ?,?,?)";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement fetchStmt = connection.prepareStatement(fetchStoreStockQuery);
             PreparedStatement updateBatchStmt = connection.prepareStatement(updateStoreStockQuery);
             PreparedStatement insertOnlineStmt = connection.prepareStatement(insertOnlineStockQuery)) {

            fetchStmt.setString(1, itemCode);
            ResultSet resultSet = fetchStmt.executeQuery();

            int remainingQuantity = quantityNeeded;

            // Process each batch to move stock to online inventory
            while (resultSet.next() && remainingQuantity > 0) {
                String batchID = resultSet.getString("batchID");
                int availableQuantity = resultSet.getInt("stockQuantity");
                java.sql.Date expiryDate = resultSet.getDate("expiryDate");

                // Ensure there's enough stock to move
                if (availableQuantity <= 0) {
                    continue; // Skip if there's no stock available
                }

                // Determine the quantity to move based on the available stock and the remaining required quantity
                int quantityToMove = Math.min(remainingQuantity, availableQuantity);

                // Update the stock in the store (reduce stock quantity)
                updateBatchStmt.setInt(1, quantityToMove);
                updateBatchStmt.setString(2, batchID);
                updateBatchStmt.executeUpdate();

                // Add the moved stock to the online inventory
                insertOnlineStmt.setString(1, UUID.randomUUID().toString());
                insertOnlineStmt.setTimestamp(2,currentTimestamp);
                insertOnlineStmt.setInt(3, quantityToMove);
                insertOnlineStmt.setString(4, itemCode);
                insertOnlineStmt.setString(5, batchID);
                insertOnlineStmt.setString(6, orderID);
                insertOnlineStmt.executeUpdate();

                // Reduce the remaining quantity needed to be moved
                remainingQuantity -= quantityToMove;
            }

            // Check if the quantity has been fully moved
            if (remainingQuantity > 0) {
                System.out.println("Not enough stock in the store to move to online inventory.");
            }

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
