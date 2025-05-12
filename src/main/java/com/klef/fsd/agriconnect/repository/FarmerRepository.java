package com.klef.fsd.agriconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.fsd.agriconnect.model.Farmer;



@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Integer>
{
    public Farmer findByUsernameAndPassword(String username, String password);
}
