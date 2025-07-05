package service;

import dao.ItemDAO;

public class ItemValidator {
    private final ItemDAO itemDAO;

    public ItemValidator(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public boolean isValidItemCode(String itemCode) {
        return itemDAO.isItemCodeValid(itemCode);
    }
}
