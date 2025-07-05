package dao;

import db.DBConnection;
import model.User;

import java.sql.*;

public class RegisterDAOImpl implements RegisterDAO {

    private DBConnection dbConnection;

    public RegisterDAOImpl(DBConnection dbConnection) {
        this.dbConnection = DBConnection.getInstance();
    }

    @Override
    public boolean registerUser(User user) {
        // Check if the username already exists in the database
        String checkUsernameQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement checkStmt = connection.prepareStatement(checkUsernameQuery)) {

            checkStmt.setString(1, user.getUsername());

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // Username already exists, return false
                    System.out.println("Username is already taken. Please choose another one.");
                    return false; // Username conflict, registration failed
                }
            }

            // If the username is available, proceed to register the user
            String query = "INSERT INTO users (name, email, phone, address, username, password) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPhone());
                stmt.setString(4, user.getAddress());
                stmt.setString(5, user.getUsername());
                stmt.setString(6, user.getPassword());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            user.setUserID(generatedKeys.getInt(1));
                        }
                    }
                    return true; // Registration successful
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false in case of exception or failure
    }
}
