package com.VogueHub.VogueHub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.VogueHub.VogueHub.Entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
}