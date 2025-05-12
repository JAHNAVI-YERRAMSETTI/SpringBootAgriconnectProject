package com.klef.fsd.agriconnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.klef.fsd.agriconnect.model.Contact;
import com.klef.fsd.agriconnect.service.ContactService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/submit")
    public String submitContact(@RequestBody Contact contact) {
        return contactService.saveContact(contact);
    }

    @GetMapping("/messages/{role}")
    public List<Contact> getMessagesByRole(@PathVariable String role) {
        return contactService.getMessagesByRecipient(role);
    }
}
