package com.VogueHub.VogueHub.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.VogueHub.VogueHub.Entity.Contact;
import com.VogueHub.VogueHub.Repository.ContactRepository;

@Service
public class ContactService {

    @Autowired
    private ContactRepository repo;

    public void saveContact(Contact contact) {
        repo.save(contact);
    }
}