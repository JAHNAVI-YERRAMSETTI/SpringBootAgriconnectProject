package com.klef.fsd.agriconnect.service;

import java.util.List;
import com.klef.fsd.agriconnect.model.Product;
import com.klef.fsd.agriconnect.model.Farmer;

public interface ProductService {
    public String addProduct(Product product);
    public List<Product> getAllProductsByFarmer(Farmer farmer);
    public List<Product> getAllProducts();
    public String deleteProduct(int productId);
    List<Product> searchProducts(String keyword);

}
