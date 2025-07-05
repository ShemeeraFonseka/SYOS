package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BillReport {

    public static class BillEntry {
        private int billID;
        private String billDate;
        private String transactionType;
        private double totalAmount;
        private double discount;
        private double cashTendered;
        private double cashChange;
        private String customerID;

        public BillEntry(int billID, String billDate, String transactionType, double totalAmount,
                         double discount, double cashTendered, double cashChange, String customerID) {
            this.billID = billID;
            this.billDate = billDate;
            this.transactionType = transactionType;
            this.totalAmount = totalAmount;
            this.discount = discount;
            this.cashTendered = cashTendered;
            this.cashChange = cashChange;
            this.customerID = customerID;
        }

        public int getBillID() {
            return billID;
        }

        public String getBillDate() {
            return billDate;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public double getDiscount() {
            return discount;
        }

        public double getCashTendered() {
            return cashTendered;
        }

        public double getCashChange() {
            return cashChange;
        }

        public String getCustomerID() {
            return customerID;
        }
    }


    public List<BillEntry> generateBillReport() {
        List<BillEntry> reportList = new ArrayList<>();

        String query = "SELECT billID, billDate, transactionType, totalAmount, discount, cashTendered, cashChange, customerID FROM bill";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int billID = rs.getInt("billID");
                String billDate = rs.getString("billDate");
                String transactionType = rs.getString("transactionType");
                double totalAmount = rs.getDouble("totalAmount");
                double discount = rs.getDouble("discount");
                double cashTendered = rs.getDouble("cashTendered");
                double cashChange = rs.getDouble("cashChange");
                String customerID = rs.getString("customerID");

                reportList.add(new BillEntry(billID, billDate, transactionType, totalAmount, discount, cashTendered, cashChange, customerID));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reportList;
    }
}
