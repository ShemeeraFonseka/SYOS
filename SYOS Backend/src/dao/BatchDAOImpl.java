package dao;

import db.DBConnection;
import model.Batch;
import service.ItemValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BatchDAOImpl implements BatchDAO{
    private static final Logger logger = Logger.getLogger(BatchDAOImpl.class.getName());
    private final DBConnection dbConnection;
    private final ItemValidator itemValidator;

    public BatchDAOImpl(DBConnection dbConnection, ItemValidator itemValidator) {
        this.dbConnection = DBConnection.getInstance();
        this.itemValidator = itemValidator;
    }

    @Override
    public void insertBatch(Batch batch, String itemCode) {
        if (!itemValidator.isValidItemCode(itemCode)) {
            logger.warning("Insertion failed: ItemCode '" + itemCode + "' does not exist.");
            throw new IllegalArgumentException("Invalid itemCode: " + itemCode);
        }

        String insertQuery = "INSERT INTO batch (itemCode, dateOfPurchase, quantityReceived, expiryDate, stockQuantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {


            insertStmt.setString(1, itemCode);
            insertStmt.setDate(2, new java.sql.Date(batch.getDateOfPurchase().getTime()));
            insertStmt.setInt(3, batch.getQuantityRecieved());
            insertStmt.setDate(4, new java.sql.Date(batch.getExpiryDate().getTime()));
            insertStmt.setInt(5, batch.getStockQuantity());

            insertStmt.executeUpdate();
            logger.info("Batch inserted successfully: ");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error during batch insertion: ", e);
            throw new RuntimeException("Batch insertion failed.", e);
        }
    }
}
