package com.klef.fsd.agriconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsd.agriconnect.model.Farmer;
import com.klef.fsd.agriconnect.repository.FarmerRepository;

@Service
public class FarmerServiceImpl implements FarmerService
{
    @Autowired
    private FarmerRepository farmerRepository;
    
    @Override
    public Farmer checkFarmerLogin(String username, String password) 
    {
        return farmerRepository.findByUsernameAndPassword(username, password);
    }
    
    @Override
    public long getFarmerCount() {
        return farmerRepository.count();
    }
    @Override
    public Farmer getFarmerById(int id) {
        return farmerRepository.findById(id).orElse(null);
    }


}
