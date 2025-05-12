package com.klef.fsd.agriconnect.service;

import com.klef.fsd.agriconnect.model.Buyer;

public interface BuyerService {
    public String buyerRegistration(Buyer buyer);
    public Buyer checkBuyerLogin(String uname, String pwd);
    public long getBuyerCount();
    public Buyer getBuyerById(int id);
    public String updateBuyerProfile(Buyer buyer);

}
