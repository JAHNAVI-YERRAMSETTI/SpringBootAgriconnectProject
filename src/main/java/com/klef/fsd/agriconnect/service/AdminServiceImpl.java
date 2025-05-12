package com.klef.fsd.agriconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsd.agriconnect.model.Admin;
import com.klef.fsd.agriconnect.model.Buyer;
import com.klef.fsd.agriconnect.model.Farmer;
import com.klef.fsd.agriconnect.repository.AdminRepository;
import com.klef.fsd.agriconnect.repository.BuyerRepository;
import com.klef.fsd.agriconnect.repository.FarmerRepository;
import com.klef.fsd.agriconnect.repository.OrderRepository;
import com.klef.fsd.agriconnect.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private FarmerRepository farmerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Admin checkAdminLogin(String username, String password) {
        return adminRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public String addFarmer(Farmer farmer) {
        farmerRepository.save(farmer);
        return "Farmer added successfully";
    }

    @Override
    public List<Farmer> displayFarmers() {
        return farmerRepository.findAll();
    }

    @Override
    public List<Buyer> displayBuyers() {
        return buyerRepository.findAll();
    }

    @Override
    public String deleteBuyer(int buyerId) {
        buyerRepository.deleteById(buyerId);
        return "Buyer deleted successfully";
    }

    @Override
    public String deleteFarmer(int farmerId) {
        farmerRepository.deleteById(farmerId);
        return "Farmer deleted successfully";
    }

    @Override
    public long getTotalBuyers() {
        return buyerRepository.count();
    }

    @Override
    public long getTotalFarmers() {
        return farmerRepository.count();
    }

    @Override
    public long getTotalOrders() {
        return orderRepository.count();
    }

    @Override
    public long getTotalProducts() {
        return productRepository.count();
    }

    @Override
    public void updateAdminProfile(Admin admin) {
        if (admin.getUsername() == null || admin.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Invalid admin username");
        }

        Optional<Admin> existingAdminOpt = adminRepository.findByUsername(admin.getUsername());
        if (!existingAdminOpt.isPresent()) {
            throw new IllegalArgumentException("Admin not found with username: " + admin.getUsername());
        }

        Admin existingAdmin = existingAdminOpt.get();
        existingAdmin.setName(admin.getName());
        existingAdmin.setEmail(admin.getEmail());
        existingAdmin.setPhone(admin.getPhone());

        if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
            existingAdmin.setPassword(admin.getPassword());
        }

        adminRepository.save(existingAdmin);
    }

    @Override
    public Optional<Admin> getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}
