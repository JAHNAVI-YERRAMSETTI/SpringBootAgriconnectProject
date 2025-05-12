package com.klef.fsd.agriconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.klef.fsd.agriconnect.model.Farmer;
import com.klef.fsd.agriconnect.service.FarmerService;

@RestController
@RequestMapping("/farmer")
@CrossOrigin("*")
public class FarmerController 
{
   @Autowired
   private FarmerService farmerService;
	   
   @PostMapping("/checkfarmerlogin")
   public ResponseEntity<?> checkfarmerlogin(@RequestBody Farmer farmer) 
   {
       try 
       {
           Farmer f = farmerService.checkFarmerLogin(farmer.getUsername(), farmer.getPassword());

           if (f != null) 
           {
               return ResponseEntity.ok(f);
           } 
           else 
           {
               return ResponseEntity.status(401).body("Invalid Username or Password");
           }
       } 
       catch (Exception e) 
       {
           return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
       }
   }
   
   @GetMapping("/count")
   public long getFarmerCount() {
       return farmerService.getFarmerCount();
   }

   @GetMapping("/viewfarmer/{id}")
   public ResponseEntity<Farmer> getFarmerById(@PathVariable int id) {
       Farmer farmer = farmerService.getFarmerById(id);
       if (farmer != null) {
           return ResponseEntity.ok(farmer);
       } else {
           return ResponseEntity.notFound().build();
       }
   }
   @GetMapping("/weather")
   public ResponseEntity<?> getWeather(String city) {
       try {
           String apiKey = "5b7e979f947d7fab5f3efd5d560364ba";
           String url = UriComponentsBuilder.fromHttpUrl("https://api.openweathermap.org/data/2.5/weather")
               .queryParam("q", city)
               .queryParam("units", "metric")
               .queryParam("appid", apiKey)
               .toUriString();

           RestTemplate restTemplate = new RestTemplate();
           String response = restTemplate.getForObject(url, String.class);

           return ResponseEntity.ok(response);
       } catch (Exception e) {
           return ResponseEntity.status(500).body("Weather fetch failed: " + e.getMessage());
       }
   }
   
   
}
