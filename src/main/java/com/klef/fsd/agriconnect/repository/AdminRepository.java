package com.klef.fsd.agriconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.fsd.agriconnect.model.Admin;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    Admin findByUsernameAndPassword(String username, String password);

    Optional<Admin> findByUsername(String username);
}
