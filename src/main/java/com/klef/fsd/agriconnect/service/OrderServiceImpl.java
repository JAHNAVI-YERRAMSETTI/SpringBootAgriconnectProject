package com.klef.fsd.agriconnect.service;

import com.klef.fsd.agriconnect.model.Buyer;
import com.klef.fsd.agriconnect.model.Order;
import com.klef.fsd.agriconnect.model.Product;
import com.klef.fsd.agriconnect.repository.BuyerRepository;
import com.klef.fsd.agriconnect.repository.OrderRepository;
import com.klef.fsd.agriconnect.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Override
    public String placeOrder(Order order) {
        try {
            Optional<Product> productOpt = productRepository.findById(order.getProduct().getId());
            Optional<Buyer> buyerOpt = buyerRepository.findById(order.getBuyer().getId());

            if (!productOpt.isPresent()) {
                return "Product not found";
            }

            if (!buyerOpt.isPresent()) {
                return "Buyer not found";
            }

            Product product = productOpt.get();
            Buyer buyer = buyerOpt.get();

            if (product.getQuantity() < order.getQuantity()) {
                return "Insufficient quantity available";
            }

            // Decrement product quantity
            product.setQuantity(product.getQuantity() - order.getQuantity());
            productRepository.save(product);

            // Setup order with all required fields
            order.setProduct(product);
            order.setBuyer(buyer);
            order.setBuyerName(buyer.getName());
            order.setTotalPrice(product.getPrice() * order.getQuantity());
            
            // Use current date as string
            order.setOrderDate(String.valueOf(LocalDate.now()));

            // Ensure status is set
            order.setStatus("Placed");
            
            // Save the order
            orderRepository.save(order);

            return "Order placed successfully";
        } catch (Exception e) {
            return "Error placing order: " + e.getMessage();
        }
    }

    @Override
    public List<Order> getOrdersByBuyerId(int buyerId) {
        return orderRepository.findByBuyerId(buyerId);
    }

    @Override
    public String cancelOrder(int orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            if (!order.getStatus().equals("Cancelled")) {
                order.setStatus("Cancelled");

                // Restore product quantity
                Product product = order.getProduct();
                product.setQuantity(product.getQuantity() + order.getQuantity());
                productRepository.save(product);

                orderRepository.save(order);
            }
            return "Order cancelled successfully";
        }
        return "Order not found";
    }
    
    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        // ✅ Remove nested farmer to avoid serialization issues (no DTOs used)
        for (Order order : orders) {
            if (order.getProduct() != null) {
                order.getProduct().setFarmer(null);
            }
        }

        return orders;
    }

    @Override
    public long getOrderCount() {
        return orderRepository.count();
    }

    @Override
    public String placeOrderFromPayment(Order order) {
        return placeOrder(order); // Reuse the placeOrder logic for consistency
    }
}