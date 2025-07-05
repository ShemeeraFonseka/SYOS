package dao;

import model.Bill;

public interface BillDAO {
    String generateBillSerialNumber();
    int saveBill(Bill bill, Integer customerID);
}
