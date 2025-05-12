package com.klef.fsd.agriconnect.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.klef.fsd.agriconnect.model.Product;
import com.klef.fsd.agriconnect.model.Farmer;
import com.klef.fsd.agriconnect.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService
{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public String addProduct(Product product) 
    {
        productRepository.save(product);
        return "Product added successfully!";
    }

    @Override
    public List<Product> getAllProductsByFarmer(Farmer farmer) 
    {
        return productRepository.findByFarmer(farmer);
    }

    @Override
    public List<Product> getAllProducts() 
    {
        return productRepository.findAll();
    }

    @Override
    public String deleteProduct(int productId) 
    {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return "Product deleted successfully!";
        }
        return "Product ID not found!";
    }
    
    public List<Product> searchProducts(String keyword) {
        List<Product> byName = productRepository.findByNameContainingIgnoreCase(keyword);
        List<Product> byCategory = productRepository.findByCategoryContainingIgnoreCase(keyword);

        List<Product> result = new ArrayList<>(byName);
        for (Product p : byCategory) {
            if (!result.contains(p)) {
                result.add(p);
            }
        }

        return result;
    }

    
}
