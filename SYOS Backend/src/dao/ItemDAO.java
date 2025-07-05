package dao;

import model.Item;

import java.util.List;

public interface ItemDAO {
    Float getItemPrice(String itemCode);

    List<Item> getAllItems();

    boolean isItemCodeValid(String itemCode);


}
