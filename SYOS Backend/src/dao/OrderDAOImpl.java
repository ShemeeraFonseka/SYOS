package dao;

import db.DBConnection;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDAOImpl implements OrderDAO {
    private DBConnection dbConnection;

    public OrderDAOImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void saveOrder(Order order, String billSerialNumber) {
        String sql = "INSERT INTO `orderitem` (orderID, quantity, totalPrice, itemCode, billSerialNumber) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection(); // Use the injected dbConnection
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, order.getOrderID());
            stmt.setInt(2, order.getQuantity());
            stmt.setFloat(3, order.getTotalPrice());
            stmt.setString(4, order.getItemCode());
            stmt.setString(5, billSerialNumber);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
