package com.klef.fsd.agriconnect.repository;

import com.klef.fsd.agriconnect.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByBuyerId(int buyerId);
}
