package com.klef.fsd.agriconnect.controller;

import com.klef.fsd.agriconnect.model.Product;
import com.klef.fsd.agriconnect.model.Farmer;
import com.klef.fsd.agriconnect.repository.ProductRepository;
import com.klef.fsd.agriconnect.repository.FarmerRepository;
import com.klef.fsd.agriconnect.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private FarmerRepository farmerRepo;

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public String addProduct(@RequestBody Product product) {
        if (product.getFarmer() == null || product.getFarmer().getId() == 0) {
            throw new IllegalArgumentException("Farmer information is missing or invalid.");
        }

        product.setFarmer(farmerRepo.findById(product.getFarmer().getId())
            .orElseThrow(() -> new RuntimeException("Farmer not found")));

        productRepo.save(product);
        return "Product added successfully";
    }

    @PostMapping("/viewbyfarmer")
    public List<Product> getProductsByFarmer(@RequestBody Farmer farmer) {
        return productService.getAllProductsByFarmer(farmer);
    }

    @DeleteMapping("/delete")
    public String deleteProduct(@RequestParam int id) {
        Product product = productRepo.findById(id).orElse(null);
        if (product == null) {
            return "Product not found.";
        }
        
        // Optional: Add custom logic to prevent deletion if quantity > 0
        if (product.getQuantity() > 0) {
            return "Cannot delete: Quantity is greater than zero.";
        }

        productRepo.deleteById(id);
        return "Product deleted successfully.";
    }


    @GetMapping("/viewall")
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @PutMapping("/update")
    public String updateProduct(@RequestParam int id, @RequestBody Product updatedProduct) {
        Product existingProduct = productRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        existingProduct.setDescription(updatedProduct.getDescription());

        productRepo.save(existingProduct);
        return "Product updated successfully";
    }

    // ✅ Modified this to accept keyword as a PathVariable
    @GetMapping("/search/{keyword}")
    public List<Product> searchProducts(@PathVariable String keyword) {
        return productService.searchProducts(keyword);
    }
    
    @GetMapping("/count")
    public long getProductCount() {
        return productRepo.count();
    }


@PostMapping(value = "/addwithimage", consumes = "multipart/form-data")
public ResponseEntity<String> addProductWithImage(
    @RequestParam("name") String name,
    @RequestParam("category") String category,
    @RequestParam("quantity") int quantity,
    @RequestParam("price") double price,
    @RequestParam("description") String description,
    @RequestParam("farmerId") int farmerId,
    @RequestParam("image") MultipartFile imageFile
) {
    try {
        // Save image to "uploads" folder
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path imagePath = Paths.get("uploads/" + fileName);
        Files.createDirectories(imagePath.getParent());
        Files.write(imagePath, imageFile.getBytes());

        // Get farmer and create product
        Farmer farmer = farmerRepo.findById(farmerId)
            .orElseThrow(() -> new RuntimeException("Farmer not found"));

        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setDescription(description);
        product.setImage(fileName); // ⬅ You must add this field to Product model
        product.setFarmer(farmer);

        productRepo.save(product);
        return ResponseEntity.ok("Product with image added successfully");

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error adding product with image: " + e.getMessage());
    }
}
@RequestMapping(value = "/addwithimage", method = RequestMethod.OPTIONS)
public ResponseEntity<?> handlePreflightForImageUpload() {
    return ResponseEntity.ok().build();
}


}