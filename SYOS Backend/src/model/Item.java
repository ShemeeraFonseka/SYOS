package model;

public class Item {
    private String itemCode;
    private String itemName;
    private float pricePerUnit;
    private String path;
    private String category;

    public Item(String itemCode, String itemName, float pricePerUnit, String path,String category) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.pricePerUnit = pricePerUnit;
        this.path=path;
        this.category=category;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(float pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
