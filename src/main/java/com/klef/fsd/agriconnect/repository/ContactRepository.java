package com.klef.fsd.agriconnect.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.klef.fsd.agriconnect.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByRecipientRole(String recipientRole);
    
    // New methods for filtering by subject
    List<Contact> findByRecipientRoleAndSubjectIgnoreCase(String recipientRole, String subject);
    List<Contact> findByRecipientRoleAndSubjectNotIgnoreCase(String recipientRole, String subject);
}
