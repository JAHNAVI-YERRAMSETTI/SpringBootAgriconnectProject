package com.klef.fsd.agriconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsd.agriconnect.model.Admin;
import com.klef.fsd.agriconnect.model.Buyer;
import com.klef.fsd.agriconnect.model.Farmer;
import com.klef.fsd.agriconnect.model.Order;
import com.klef.fsd.agriconnect.repository.OrderRepository;
import com.klef.fsd.agriconnect.service.AdminService;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin("*") // Allowing frontend to access from any origin
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private OrderRepository orderRepository;

    @CrossOrigin("*")
    // Admin Login
    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        Admin admin = adminService.checkAdminLogin(username, password);

        if (admin != null) {
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid credentials");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    // Add a new farmer
    @PostMapping("/addfarmer")
    public ResponseEntity<String> addFarmer(@RequestBody Farmer farmer) {
        String result = adminService.addFarmer(farmer);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // Get all farmers
    @GetMapping("/farmers")
    public ResponseEntity<List<Farmer>> getAllFarmers() {
        List<Farmer> farmers = adminService.displayFarmers();
        return new ResponseEntity<>(farmers, HttpStatus.OK);
    }

    // Get all buyers
    @GetMapping("/buyers")
    public ResponseEntity<List<Buyer>> getAllBuyers() {
        List<Buyer> buyers = adminService.displayBuyers();
        return new ResponseEntity<>(buyers, HttpStatus.OK);
    }

    // Delete a buyer
    @DeleteMapping("/buyer/{id}")
    public ResponseEntity<String> deleteBuyer(@PathVariable("id") int buyerId) {
        String result = adminService.deleteBuyer(buyerId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Delete a farmer
    @DeleteMapping("/farmer/{id}")
    public ResponseEntity<String> deleteFarmer(@PathVariable("id") int farmerId) {
        String result = adminService.deleteFarmer(farmerId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Dashboard summary (total counts)
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Long>> getDashboardStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalBuyers", adminService.getTotalBuyers());
        stats.put("totalFarmers", adminService.getTotalFarmers());
        stats.put("totalOrders", adminService.getTotalOrders());
        stats.put("totalProducts", adminService.getTotalProducts());

        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

    // Update admin profile
    @PutMapping("/updateprofile")
    public ResponseEntity<?> updateAdminProfile(@RequestBody Admin admin) {
        try {
            adminService.updateAdminProfile(admin);
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Failed to update profile: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Order analytics by day
    @GetMapping("/order-analytics/day")
    public Map<String, Long> getOrdersByDay() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getOrderDate, Collectors.counting()));
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getAdminByUsername(@PathVariable("username") String username) {
        Optional<Admin> adminOpt = adminService.getAdminByUsername(username);
        return adminOpt
                .<ResponseEntity<?>>map(admin -> new ResponseEntity<>(admin, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Admin not found", HttpStatus.NOT_FOUND));
    }
}
