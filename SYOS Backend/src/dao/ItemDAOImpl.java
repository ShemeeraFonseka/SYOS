package dao;

import db.DBConnection;
import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemDAOImpl implements ItemDAO {

    private static final Logger logger = Logger.getLogger(ItemDAOImpl.class.getName()); // Correct logger setup

    @Override
    public Float getItemPrice(String itemCode) {
        try (Connection conn = DBConnection.getInstance().getConnection()) {
            String sql = "SELECT pricePerUnit FROM Item WHERE itemCode = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, itemCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getFloat("pricePerUnit");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting item price for itemCode: " + itemCode, e);
        }
        return null;
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT itemCode, itemName, pricePerUnit,path, category FROM item";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Item item = new Item(
                        resultSet.getString("itemCode"),
                        resultSet.getString("itemName"),
                        resultSet.getFloat("pricePerUnit"),
                        resultSet.getString("path"),
                        resultSet.getString("category")
                );
                items.add(item);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Database error while fetching all items: ", e);
        }

        return items;
    }

    @Override
    public boolean isItemCodeValid(String itemCode) {
        String query = "SELECT COUNT(*) FROM item WHERE itemCode = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement checkStmt = connection.prepareStatement(query)) {

            checkStmt.setString(1, itemCode);
            try (ResultSet rs = checkStmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            // Handle the exception or rethrow it
            throw new RuntimeException("Error checking item code.", e);
        }
    }



}

