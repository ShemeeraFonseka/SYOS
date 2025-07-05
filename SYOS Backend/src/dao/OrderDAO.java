package dao;

import model.Order;

public interface OrderDAO {
    void saveOrder(Order order, String billSerialNumber);
}
