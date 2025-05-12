package com.klef.fsd.agriconnect.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsd.agriconnect.model.Buyer;
import com.klef.fsd.agriconnect.repository.BuyerRepository;

@Service
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private BuyerRepository buyerRepository;

    @Override
    public String buyerRegistration(Buyer buyer) {
        // Optionally check if user/email already exists
        Optional<Buyer> existingBuyer = buyerRepository
            .findAll()
            .stream()
            .filter(b -> b.getUsername().equals(buyer.getUsername()) || b.getEmail().equals(buyer.getEmail()))
            .findFirst();

        if (existingBuyer.isPresent()) {
            return "Username or Email already exists!";
        }

        buyerRepository.save(buyer);
        return "Buyer Registered Successfully";
    }

    @Override
    public Buyer checkBuyerLogin(String uname, String pwd) {
        return buyerRepository.findByUsernameAndPassword(uname, pwd);  // returns null if not found, safe
    }
    
    @Override
    public long getBuyerCount() {
        return buyerRepository.count();
    }

    @Override
    public Buyer getBuyerById(int id) {
        return buyerRepository.findById(id).orElse(null);
    }
    
    @Override
    public String updateBuyerProfile(Buyer buyer) {
        Optional<Buyer> optional = buyerRepository.findById(buyer.getId());
        if (optional.isPresent()) {
            Buyer b = optional.get();
            b.setName(buyer.getName());
            b.setDob(buyer.getDob());
            b.setMobileno(buyer.getMobileno());
            b.setEmail(buyer.getEmail());
            b.setPassword(buyer.getPassword());
            b.setLocation(buyer.getLocation());
            buyerRepository.save(b);
            return "Buyer Profile Updated Successfully";
        } else {
            return "Buyer ID Not Found to Update";
        }
    }

}