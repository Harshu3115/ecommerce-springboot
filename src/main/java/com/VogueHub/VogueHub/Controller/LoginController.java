package com.VogueHub.VogueHub.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.VogueHub.VogueHub.Entity.User;
import com.VogueHub.VogueHub.Service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    private UserService service;

    // 👉 Open Login Page FIRST
    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    // 👉 Handle Login
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {

        User user = service.loginUser(email, password);

        if(user != null) {
            session.setAttribute("loggedUser", user); // ✅ SAVE USER
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
}