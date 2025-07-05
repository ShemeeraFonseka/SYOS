package model;

public class Order {
    private String orderID;
    private String itemCode;
    private int quantity;
    private float totalPrice;
    private String billSerialNumber;

    public Order(String orderID, String itemCode, int quantity, float totalPrice, String billSerialNumber) {
        this.orderID = orderID;
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.billSerialNumber = billSerialNumber;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBillSerialNumber() {
        return billSerialNumber;
    }

    public void setBillSerialNumber(String billSerialNumber) {
        this.billSerialNumber = billSerialNumber;
    }
}
