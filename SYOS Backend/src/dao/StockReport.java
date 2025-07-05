package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StockReport {

    public static class StockEntry {
        private String itemCode;
        private String dateOfPurchase;
        private int quantityReceived;
        private String expiryDate;

        public StockEntry(String itemCode, String dateOfPurchase, int quantityReceived, String expiryDate) {
            this.itemCode = itemCode;
            this.dateOfPurchase = dateOfPurchase;
            this.quantityReceived = quantityReceived;
            this.expiryDate = expiryDate;
        }

        public String getItemCode() {
            return itemCode;
        }

        public String getDateOfPurchase() {
            return dateOfPurchase;
        }

        public int getQuantityReceived() {
            return quantityReceived;
        }

        public String getExpiryDate() {
            return expiryDate;
        }
    }

    public List<StockEntry> generateStockReport() {
        List<StockEntry> reportList = new ArrayList<>();

        String query = "SELECT itemCode, dateOfPurchase, quantityReceived, expiryDate FROM batch";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String itemCode = rs.getString("itemCode");
                String dateOfPurchase = rs.getString("dateOfPurchase");
                int quantityReceived = rs.getInt("quantityReceived");
                String expiryDate = rs.getString("expiryDate");

                reportList.add(new StockEntry(itemCode, dateOfPurchase, quantityReceived, expiryDate));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reportList;
    }
}
