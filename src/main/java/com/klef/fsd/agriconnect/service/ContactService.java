package com.klef.fsd.agriconnect.service;

import java.util.List;
import com.klef.fsd.agriconnect.model.Contact;

public interface ContactService {
    String saveContact(Contact contact);
    List<Contact> getMessagesByRecipient(String role);
}
