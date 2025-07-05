package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SalesReport {

    public static class ReportEntry {
        public String itemName;
        public String itemCode;
        public int totalQuantity;
        public double totalRevenue;

        public ReportEntry(String itemName, String itemCode, int totalQuantity, double totalRevenue) {
            this.itemName = itemName;
            this.itemCode = itemCode;
            this.totalQuantity = totalQuantity;
            this.totalRevenue = totalRevenue;
        }
    }

    public List<ReportEntry> getSalesReport() {
        List<ReportEntry> reportList = new ArrayList<>();

        String query = "SELECT " +
                "i.itemName, " +
                "oi.itemCode, " +
                "SUM(oi.quantitySold) AS totalQuantity, " +
                "SUM(oi.quantitySold * i.pricePerUnit) AS totalRevenue " +
                "FROM onlineinventory oi " +
                "JOIN item i ON oi.itemCode = i.itemCode " +
                "WHERE DATE(oi.date) = CURRENT_DATE " +
                "GROUP BY i.itemName, oi.itemCode;";

        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                reportList.add(new ReportEntry(
                        rs.getString("itemName"),
                        rs.getString("itemCode"),
                        rs.getInt("totalQuantity"),
                        rs.getDouble("totalRevenue")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reportList;
    }
}
