package com.VogueHub.VogueHub.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.VogueHub.VogueHub.Entity.User;
import com.VogueHub.VogueHub.Repository.UserRepository;

@Controller
public class UserForgotPasswordController {

    @Autowired
    private UserRepository userRepository;

    // Open forgot page
    @GetMapping("/user/forgot-password")
    public String forgotPage() {
        return "userForgotPassword";
    }

    // Verify email
    @PostMapping("/user/forgot-password")
    public String verifyEmail(@RequestParam String email, Model model) {

        User user = userRepository.findByEmail(email);

        if (user != null) {
            model.addAttribute("email", email);
            return "redirect:/user/reset-password?email=" + email; 
        } else {
            model.addAttribute("error", "Email not found");
            return "userForgotPassword";
        }
    }

    // Reset password
    @PostMapping("/user/reset-password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                Model model) {

        // 🔥 ADD HERE
        System.out.println("Email: " + email);
        System.out.println("New Password: " + newPassword);

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            model.addAttribute("email", email);
            return "user-reset-password";
        }

        User user = userRepository.findByEmail(email);

        if (user != null) {
            user.setPassword(newPassword);
            userRepository.save(user);
            System.out.println("Password updated in DB"); // 🔥 ADD THIS ALSO
        } else {
            System.out.println("User not found"); // 🔥 DEBUG
        }

        return "redirect:/login";
    }
    
    @GetMapping("/user/reset-password")
    public String showResetPage(@RequestParam(required = false) String email, Model model) {
        model.addAttribute("email", email);
        System.out.println("GET RESET PAGE CALLED");
        return "user-reset-password";
    }
}