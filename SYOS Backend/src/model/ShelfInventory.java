package model;

public class ShelfInventory {
    private String  shelfInventoryID;
    private int shelfQuantity;
    private String itemCode;

    public ShelfInventory(String shelfInventoryID, int shelfQuantity, String itemCode) {
        this.shelfInventoryID = shelfInventoryID;
        this.shelfQuantity = shelfQuantity;
        this.itemCode = itemCode;
    }

    public String getShelfInventoryID() {
        return shelfInventoryID;
    }

    public void setShelfInventoryID(String shelfInventoryID) {
        this.shelfInventoryID = shelfInventoryID;
    }

    public int getShelfQuantity() {
        return shelfQuantity;
    }

    public void setShelfQuantity(int shelfQuantity) {
        this.shelfQuantity = shelfQuantity;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
}
