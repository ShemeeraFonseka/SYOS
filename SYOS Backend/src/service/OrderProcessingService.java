package service;

import dao.OrderDAO;
import model.Order;

import java.util.List;

public class OrderProcessingService {
    private final StockMovementService stockMovementService;

    public OrderProcessingService(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    public void processOrders(List<Order> orders, String billSerialNumber,
                              String transactionType) {
        for (Order order : orders) {
            stockMovementService.moveStock(order, transactionType);
        }
    }
}
