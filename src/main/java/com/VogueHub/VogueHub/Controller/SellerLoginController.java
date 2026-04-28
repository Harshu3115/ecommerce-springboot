package com.VogueHub.VogueHub.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.VogueHub.VogueHub.Entity.Seller;
import com.VogueHub.VogueHub.Repository.SellerRepository;

@Controller
public class SellerLoginController {

    @Autowired
    private SellerRepository sellerRepository;

    @GetMapping("/seller/login")
    public String sellerLoginPage() {
        return "sellerLogin";
    }

    @PostMapping("/seller/login")
    public String sellerLogin(@RequestParam String email,
                              @RequestParam String password,
                              Model model) {

        Seller seller = sellerRepository.findByEmail(email);

        if (seller != null && seller.getPassword().equals(password)) {
        	return "redirect:/admin/adminDashboard";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "sellerLogin";
        }
    }
    
    
    @GetMapping("/admin/adminDashboard")
    public String adminDashboard() {
        return "adminDashboard"; // ✅ MUST match file name
    }

}
