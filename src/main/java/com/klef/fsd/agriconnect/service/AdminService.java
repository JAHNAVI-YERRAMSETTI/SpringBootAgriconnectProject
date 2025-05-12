package com.klef.fsd.agriconnect.service;

import java.util.List;
import java.util.Optional;

import com.klef.fsd.agriconnect.model.Admin;
import com.klef.fsd.agriconnect.model.Buyer;
import com.klef.fsd.agriconnect.model.Farmer;

public interface AdminService {
    public Admin checkAdminLogin(String username, String password);
    public String addFarmer(Farmer farmer);
    public List<Farmer> displayFarmers();
    public List<Buyer> displayBuyers();
    public String deleteBuyer(int buyerId);
    public String deleteFarmer(int farmerId);

    // Dashboard stats
    public long getTotalBuyers();
    public long getTotalFarmers();
    public long getTotalOrders();
    public long getTotalProducts();

    // Admin profile update
    public void updateAdminProfile(Admin updatedAdmin);

    // Admin by username
    public Optional<Admin> getAdminByUsername(String username);
}
