package com.klef.fsd.agriconnect.controller;
import com.klef.fsd.agriconnect.model.Order;
import com.klef.fsd.agriconnect.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/count")
    public long getOrderCount() {
        return orderService.getOrderCount();
    }

    @PostMapping("/place")
    public ResponseEntity<Map<String, String>> placeOrder(@RequestBody Order order) {
        Map<String, String> response = new HashMap<>();
        try {
            String result = orderService.placeOrder(order);
            response.put("message", result);
            
            if (result.contains("successfully")) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.put("message", "Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buyer/{buyerId}")
    public List<Order> getOrdersByBuyerId(@PathVariable int buyerId) {
        return orderService.getOrdersByBuyerId(buyerId);
    }

    @DeleteMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable int orderId) {
        return orderService.cancelOrder(orderId);
    }

    // ✅ New method for admin to view all buyer orders
    @GetMapping("/admin/viewall")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
    
    @PostMapping("/place/payment")
    public ResponseEntity<Map<String, String>> placeOrderFromPayment(@RequestBody Order order) {
        Map<String, String> response = new HashMap<>();
        try {
            String result = orderService.placeOrderFromPayment(order);
            response.put("message", result);
            
            if (result.contains("successfully")) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.put("message", "Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}