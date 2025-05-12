package com.klef.fsd.agriconnect.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.klef.fsd.agriconnect.model.Contact;
import com.klef.fsd.agriconnect.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public String saveContact(Contact contact) {
        contactRepository.save(contact);
        return "Contact form submitted successfully!";
    }

    @Override
    public List<Contact> getMessagesByRecipient(String role) {
        if (role.equalsIgnoreCase("farmer")) {
            return contactRepository.findByRecipientRoleAndSubjectIgnoreCase(role, "Feedback");
        } else {
            return contactRepository.findByRecipientRoleAndSubjectNotIgnoreCase(role, "Feedback");
        }
    }

}
