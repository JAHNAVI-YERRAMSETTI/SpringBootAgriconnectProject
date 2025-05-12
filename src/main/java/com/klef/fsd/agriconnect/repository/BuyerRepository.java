package com.klef.fsd.agriconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.fsd.agriconnect.model.Buyer;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Integer>
{
    public Buyer findByUsernameAndPassword(String username, String password);
}