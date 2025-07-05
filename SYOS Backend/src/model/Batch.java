package model;

import java.util.Date;

public class Batch {
    private int batchID;
    private Date dateOfPurchase;
    private int quantityRecieved;
    private Date expiryDate;
    private String itemCOde;
    private int stockQuantity;

    public Batch(Date dateOfPurchase, int quantityRecieved, Date expiryDate, String itemCOde, int stockQuantity) {

        this.dateOfPurchase = dateOfPurchase;
        this.quantityRecieved = quantityRecieved;
        this.expiryDate = expiryDate;
        this.itemCOde = itemCOde;
        this.stockQuantity = stockQuantity;
    }



    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public int getQuantityRecieved() {
        return quantityRecieved;
    }

    public void setQuantityRecieved(int quantityRecieved) {
        this.quantityRecieved = quantityRecieved;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getItemCOde() {
        return itemCOde;
    }

    public void setItemCOde(String itemCOde) {
        this.itemCOde = itemCOde;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
