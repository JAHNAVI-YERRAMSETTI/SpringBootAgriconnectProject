package com.klef.fsd.agriconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.fsd.agriconnect.model.Product;
import com.klef.fsd.agriconnect.model.Farmer;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>
{
    
    List<Product> findByFarmer(Farmer farmer);
    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findByCategoryContainingIgnoreCase(String keyword);

    
}
