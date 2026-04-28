package com.VogueHub.VogueHub.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.VogueHub.VogueHub.Entity.Seller;
import com.VogueHub.VogueHub.Entity.User;
import com.VogueHub.VogueHub.Repository.SellerRepository;
import com.VogueHub.VogueHub.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private EmailService emailService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // ✅ REGISTER USER + EMAIL
    public void registerUser(User user) {

        // 🔐 Encrypt password
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCreatedDate(LocalDate.now());

        // 💾 Save user
        repo.save(user);

        // =========================
        // ✅ EMAIL TO USER (TEMPLATE)
        // =========================
        try {
            emailService.sendTemplateEmail(
                user.getEmail(),
                "Welcome to VogueHub 🎉",
                user.getName(),
                user.getEmail()
            );
        } catch (Exception e) {
            System.out.println("User email error: " + e.getMessage());
        }

        // =========================
        // ✅ EMAIL TO ADMIN (SELLERS)
        // =========================
        List<Seller> sellers = sellerRepository.findAll();

        for (Seller seller : sellers) {

            if (seller.getEmail() != null && !seller.getEmail().trim().isEmpty()) {

                emailService.sendAdminTemplateEmail(
                    seller.getEmail(),
                    user.getName(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getCreatedDate().toString()
                );
            }
        }
    }

    // ✅ LOGIN
    public User loginUser(String email, String password) {
        User user = repo.findByEmail(email);

        if (user != null && encoder.matches(password, user.getPassword())) {
            return user;
        }

        return null;
    }

    // ✅ FIND USER
    public User findByEmail(String email) {
        return repo.findByEmail(email);
    }
}