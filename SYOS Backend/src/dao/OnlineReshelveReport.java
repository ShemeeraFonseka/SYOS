package dao;

import db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OnlineReshelveReport {

    public static class ReshelveEntry {
        private String itemName;
        private String itemCode;
        private int totalQuantity;

        public ReshelveEntry(String itemName, String itemCode, int totalQuantity) {
            this.itemName = itemName;
            this.itemCode = itemCode;
            this.totalQuantity = totalQuantity;
        }

        public String getItemName() {
            return itemName;
        }

        public String getItemCode() {
            return itemCode;
        }

        public int getTotalQuantity() {
            return totalQuantity;
        }
    }

    public List<ReshelveEntry> generateReshelveReport() {
        List<ReshelveEntry> reportList = new ArrayList<>();

        String query = "SELECT " +
                "    i.itemName, " +
                "    oi.itemCode, " +
                "    SUM(oi.quantitySold) AS totalQuantity " +
                "FROM onlineinventory oi " +
                "JOIN item i ON oi.itemCode = i.itemCode " +
                "WHERE DATE(oi.date) = CURRENT_DATE " +
                "GROUP BY i.itemName, oi.itemCode;";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String itemName = rs.getString("itemName");
                String itemCode = rs.getString("itemCode");
                int totalQuantity = rs.getInt("totalQuantity");

                reportList.add(new ReshelveEntry(itemName, itemCode, totalQuantity));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reportList;
    }
}
