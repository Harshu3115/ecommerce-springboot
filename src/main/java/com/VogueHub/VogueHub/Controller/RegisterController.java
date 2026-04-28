package com.VogueHub.VogueHub.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.VogueHub.VogueHub.Entity.User;
import com.VogueHub.VogueHub.Service.UserService;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    // 👉 Open Register Page
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "userRegister";
    }

    // 👉 Save User
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {

        // ✅ Check duplicate email
        if(userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email already exists");
            return "userRegister";
        }

        // ✅ Save user + send email
        userService.registerUser(user);

        return "redirect:/";
    }
}