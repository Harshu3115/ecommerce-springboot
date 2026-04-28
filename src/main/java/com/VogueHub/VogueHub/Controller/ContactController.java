package com.VogueHub.VogueHub.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.VogueHub.VogueHub.Entity.Contact;
import com.VogueHub.VogueHub.Service.ContactService;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    // 👉 Open page
    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    // 👉 Handle form
    @PostMapping("/submit-contact")
    public String submitContact(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String subject,
                               @RequestParam String message,
                               Model model) {

        Contact contact = new Contact();
        contact.setName(name);
        contact.setEmail(email);
        contact.setSubject(subject);
        contact.setMessage(message);

        contactService.saveContact(contact); // ✅ SAVE TO DB

        model.addAttribute("success", "Message sent successfully!");

        return "contact";
    }
}