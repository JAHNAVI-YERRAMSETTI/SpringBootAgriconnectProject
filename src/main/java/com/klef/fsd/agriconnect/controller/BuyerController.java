package com.klef.fsd.agriconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsd.agriconnect.model.Buyer;
import com.klef.fsd.agriconnect.service.BuyerService;

@RestController
@RequestMapping("/buyer")
@CrossOrigin("*")
public class BuyerController 
{
   @Autowired
   private BuyerService buyerService;

   @GetMapping("/")
   public String home() {
       return "AgriConnect Project";
   }

   @PostMapping("/registration")
   public ResponseEntity<String> buyerregistration(@RequestBody Buyer buyer) {
       try {
           String output = buyerService.buyerRegistration(buyer);
           return ResponseEntity.ok(output);
       } catch(Exception e) {
           return ResponseEntity.status(500).body("Buyer Registration Failed ...");
       }
   }

   @PostMapping("/checkbuyerlogin")
   public ResponseEntity<?> checkbuyerlogin(@RequestBody Buyer buyer) {
       try {
           Buyer b = buyerService.checkBuyerLogin(buyer.getUsername(), buyer.getPassword());
           if (b != null) {
               return ResponseEntity.ok(b);
           } else {
               return ResponseEntity.status(401).body("Invalid Username or Password");
           }
       } catch (Exception e) {
           return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
       }
   }

   @GetMapping("/count")
   public long getBuyerCount() {
       return buyerService.getBuyerCount();
   }

   @GetMapping("/viewbuyer/{id}")
   public ResponseEntity<Buyer> getBuyerById(@PathVariable int id) {
       Buyer buyer = buyerService.getBuyerById(id);
       if (buyer != null) {
           return ResponseEntity.ok(buyer);
       } else {
           return ResponseEntity.notFound().build();
       }
   }

   // ✅ Add this to enable profile updates
   @PutMapping("/updateprofile")
   public ResponseEntity<String> updateBuyerProfile(@RequestBody Buyer buyer) {
       try {
           String output = buyerService.updateBuyerProfile(buyer);
           return ResponseEntity.ok(output);
       } catch (Exception e) {
           return ResponseEntity.status(500).body("Failed to Update Buyer Profile");
       }
   }
}