package com.klef.fsd.agriconnect.service;

import com.klef.fsd.agriconnect.model.Farmer;

public interface FarmerService 
{
  public Farmer checkFarmerLogin(String username, String password);
  public long getFarmerCount();
  Farmer getFarmerById(int id);


}
