

import model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.OrderProcessingService;
import service.StockMovementService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class OrderProcessingServiceTest {

    private StockMovementService stockMovementService;
    private OrderProcessingService orderProcessingService;

    @BeforeEach
    void setUp() {
        stockMovementService = mock(StockMovementService.class);
        orderProcessingService = new OrderProcessingService(stockMovementService);
    }

    @Test
    void testProcessOrders_CallsMoveStockForEachOrder() {
        // Arrange
        Order order1 = mock(Order.class);
        Order order2 = mock(Order.class);
        List<Order> orders = Arrays.asList(order1, order2);

        String billSerialNumber = "BILL123";
        String transactionType = "SALE";

        // Act
        orderProcessingService.processOrders(orders, billSerialNumber, transactionType);

        // Assert
        verify(stockMovementService, times(1)).moveStock(order1, transactionType);
        verify(stockMovementService, times(1)).moveStock(order2, transactionType);
        verifyNoMoreInteractions(stockMovementService);
    }
}
