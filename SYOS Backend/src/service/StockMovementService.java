package service;

import model.Order;

public interface StockMovementService {
    void moveStock(Order order, String transactionType);
}
