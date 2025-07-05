package model;

import java.util.Date;

public class OnlineInventory {
    private String  onlineInventoryID;
    private Date date;
    private int quantitySold;
    private String itemCode;
    private String batchID;
    private String orderID;

    public OnlineInventory(String onlineInventoryID, Date date, int quantitySold, String itemCode, String batchID, String orderID) {
        this.onlineInventoryID = onlineInventoryID;
        this.date = date;
        this.quantitySold = quantitySold;
        this.itemCode = itemCode;
        this.batchID = batchID;
        this.orderID = orderID;
    }

    public String getOnlineInventoryID() {
        return onlineInventoryID;
    }

    public void setOnlineInventoryID(String onlineInventoryID) {
        this.onlineInventoryID = onlineInventoryID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getBatchID() {
        return batchID;
    }

    public void setBatchID(String batchID) {
        this.batchID = batchID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
