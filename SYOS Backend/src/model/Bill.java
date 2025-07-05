package model;

import java.util.Date;
import java.util.List;

public class Bill {
    private int billID;
    private String billSerialNumber;
    private Date billDate;
    private String transactionType;
    private float totalAmount;
    private float discount;
    private float cashTendered;
    private float cashChange;
    private Integer customerID;
    private List<Order> orders;
    private float amountAfterDiscount;

    public Bill(){

    }

    public Bill(String billSerialNumber, Date billDate, String transactionType, float totalAmount, float discount,
                float cashTendered, float cashChange, Integer customerID, List<Order> orders, float amountAfterDiscount) {
        this.billSerialNumber = billSerialNumber;
        this.billDate = billDate;
        this.transactionType = transactionType;
        this.totalAmount = totalAmount;
        this.discount = discount;
        this.cashTendered = cashTendered;
        this.cashChange = cashChange;
        this.customerID = customerID;
        this.orders = orders;
        this.amountAfterDiscount = amountAfterDiscount;
    }

    public Integer getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public String getBillSerialNumber() {
        return billSerialNumber;
    }

    public void setBillSerialNumber(String billSerialNumber) {
        this.billSerialNumber = billSerialNumber;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getCashTendered() {
        return cashTendered;
    }

    public void setCashTendered(float cashTendered) {
        this.cashTendered = cashTendered;
    }

    public float getCashChange() {
        return cashChange;
    }

    public void setCashChange(float cashChange) {
        this.cashChange = cashChange;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public float getAmountAfterDiscount() {
        return amountAfterDiscount;
    }

    public void setAmountAfterDiscount(float amountAfterDiscount) {
        this.amountAfterDiscount = amountAfterDiscount;
    }
}
