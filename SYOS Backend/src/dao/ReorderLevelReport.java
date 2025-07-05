package dao;

import db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReorderLevelReport {

    public static class ReorderEntry {
        private String itemName;
        private String itemCode;
        private int totalStockQuantity;

        public ReorderEntry(String itemName, String itemCode, int totalStockQuantity) {
            this.itemName = itemName;
            this.itemCode = itemCode;
            this.totalStockQuantity = totalStockQuantity;
        }

        public String getItemName() {
            return itemName;
        }

        public String getItemCode() {
            return itemCode;
        }

        public int getTotalStockQuantity() {
            return totalStockQuantity;
        }
    }

    public List<ReorderEntry> generateReorderLevelReport() {
        List<ReorderEntry> reportList = new ArrayList<>();

        String query = "SELECT " +
                "    i.itemName, " +
                "    b.itemCode, " +
                "    SUM(b.stockQuantity) AS totalStockQuantity " +
                "FROM batch b " +
                "JOIN item i ON b.itemCode = i.itemCode " +
                "GROUP BY b.itemCode, i.itemName " +
                "HAVING SUM(b.stockQuantity) < 50 " +
                "ORDER BY MIN(b.expiryDate) ASC;";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String itemName = rs.getString("itemName");
                String itemCode = rs.getString("itemCode");
                int totalStockQuantity = rs.getInt("totalStockQuantity");

                reportList.add(new ReorderEntry(itemName, itemCode, totalStockQuantity));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reportList;
    }
}
