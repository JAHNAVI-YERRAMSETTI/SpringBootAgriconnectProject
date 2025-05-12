package com.klef.fsd.agriconnect.service;

import com.klef.fsd.agriconnect.model.Order;
import java.util.List;

public interface OrderService {
    String placeOrder(Order order);
    List<Order> getOrdersByBuyerId(int buyerId);
    String cancelOrder(int orderId);

    // ✅ New method for admin
    List<Order> getAllOrders();
    public long getOrderCount();
    String placeOrderFromPayment(Order order);
    

}
